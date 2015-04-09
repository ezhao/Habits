package com.herokuapp.ezhao.habits;

import android.app.Application;

import com.herokuapp.ezhao.habits.models.Habit;
import com.herokuapp.ezhao.habits.models.HabitLog;
import com.parse.Parse;
import com.parse.ParseObject;

public class HabitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        ParseObject.registerSubclass(Habit.class);
        ParseObject.registerSubclass(HabitLog.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "oWimWFsPLNhWO5vxYKLVeJkVxYAty0zQKigVfTBM", "UsbxDGxkmL4NJV9Xq4XuprdaaTdQ2iBeTxlYYUru");
    }
}
