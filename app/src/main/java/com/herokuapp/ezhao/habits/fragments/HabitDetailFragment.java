package com.herokuapp.ezhao.habits.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.herokuapp.ezhao.habits.R;
import com.herokuapp.ezhao.habits.models.Habit;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HabitDetailFragment extends Fragment {
    private static final String HABIT_TAG = "serializedHabit";
    OnItemSavedListener listener;
    Habit habit;

    @InjectView(R.id.etHabit) EditText etHabit;
    @InjectView(R.id.btnDeleteHabit) Button btnDeleteHabit;

    public static HabitDetailFragment newInstance(Habit habit) {
        HabitDetailFragment habitDetailFragment = new HabitDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(HABIT_TAG, habit);
        habitDetailFragment.setArguments(args);
        return habitDetailFragment;
    }

    public interface OnItemSavedListener {
        public void onItemSaved(Habit habit, String newItem);
        public void onItemDeleted(Habit habit);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnItemSavedListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            habit = (Habit) getArguments().getSerializable(HABIT_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_detail, container, false);
        ButterKnife.inject(this, view);

        if(habit != null) {
            etHabit.setText(habit.getDescription());
        } else {
            btnDeleteHabit.setVisibility(View.GONE);
        }

        return view;
    }

    @OnClick(R.id.btnSaveHabit)
    public void saveHabit(View view) {
        listener.onItemSaved(habit, etHabit.getText().toString());
    }

    @OnClick(R.id.btnDeleteHabit)
    public void deleteHabit(View view) {
        listener.onItemDeleted(habit);
    }
}
