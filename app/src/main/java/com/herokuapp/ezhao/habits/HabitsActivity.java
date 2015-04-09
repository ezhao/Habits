package com.herokuapp.ezhao.habits;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.herokuapp.ezhao.habits.fragments.HabitDetailFragment;
import com.herokuapp.ezhao.habits.fragments.HabitListFragment;
import com.herokuapp.ezhao.habits.models.Habit;
import com.parse.ParseObject;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HabitsActivity extends ActionBarActivity implements HabitListFragment.OnItemSelectedListener, HabitDetailFragment.OnItemSavedListener {
    FragmentTransaction ft;
    FragmentManager fm;
    HabitListFragment habitListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        // Create the HabitListFragment
        habitListFragment = new HabitListFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flFragment, habitListFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habits, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Habit habit) {
        ft = fm.beginTransaction();

        HabitDetailFragment habitDetailFragment;
        if (habit == null) {
            habitDetailFragment = new HabitDetailFragment();
        } else {
            habitDetailFragment = HabitDetailFragment.newInstance(habit);
        }

        ft.replace(R.id.flFragment, habitDetailFragment);
        ft.addToBackStack("detail");
        ft.commit();
    }

    @Override
    public void onItemSaved(Habit habit, String newDescription) {
        habitListFragment.saveHabit(habit, newDescription);

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    @Override
    public void onItemDeleted(Habit habit) {
        habitListFragment.deleteHabit(habit);

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }
}
