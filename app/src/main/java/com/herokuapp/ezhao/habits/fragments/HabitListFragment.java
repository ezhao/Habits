package com.herokuapp.ezhao.habits.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.herokuapp.ezhao.habits.R;
import com.herokuapp.ezhao.habits.adapters.HabitAdapter;
import com.herokuapp.ezhao.habits.models.Habit;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.todddavies.components.progressbar.ProgressWheel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HabitListFragment extends Fragment {
    private static final String TAG = "HabitListFragment";
    @InjectView(R.id.pwHabitProgress) ProgressWheel pwHabitProgress;
    @InjectView(R.id.lvHabits) ListView lvHabits;
    HabitAdapter habitAdapter;
    OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        public void onItemSelected(Habit habit);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnItemSelectedListener) activity; // TODO(emily) class cast exception
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_list, container, false);
        ButterKnife.inject(this, view);

        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d");
        Date date = new Date();
        pwHabitProgress.setText(dateFormat.format(date));
        Log.d(TAG, dateFormat.format(date));

        // Set up listview and adapter
        habitAdapter = new HabitAdapter((Activity) listener, new ArrayList<Habit>());
        lvHabits.setAdapter(habitAdapter);
        lvHabits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Habit habit = habitAdapter.getItem(position);
                listener.onItemSelected(habit);
            }
        });

        // Query Parse
        ParseQuery<Habit> queryHabits = ParseQuery.getQuery(Habit.class);
        queryHabits.findInBackground(new FindCallback<Habit>() {
            public void done(List<Habit> itemList, ParseException e) {
                if (e == null) {
                    habitAdapter.addAll(itemList);

                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });

        // TODO(emily) set progress bar to correct #
        pwHabitProgress.setProgress(240);

        return view;
    }

    @OnClick(R.id.btnAddHabit)
    public void addHabitTapped(View view) {
        listener.onItemSelected(null);
    }

    public void saveHabit(Habit habit, String newDescription) {
        if(habit == null) {
            // Create
            if (newDescription != null && newDescription.length() > 0) {
                habit = new Habit(newDescription);
                habit.saveEventually();
                habitAdapter.add(habit);
                // TODO(emily) some concurrency thing here with getting the list to update properly...
            }
        } else {
            // Update
            habit.setDescription(newDescription);
            habit.saveEventually();
        }
    }

    public void deleteHabit(Habit habit) {
        habit.deleteEventually();
        habitAdapter.remove(habit);
        // TODO(emily) some concurrency thing here with getting the list to update properly...
    }
}
