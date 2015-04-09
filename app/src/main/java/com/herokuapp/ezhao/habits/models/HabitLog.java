package com.herokuapp.ezhao.habits.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

@ParseClassName("HabitLog")
public class HabitLog extends ParseObject {
    public HabitLog() {
    }

    public HabitLog(Habit habit, Date date, Boolean completion) {
        setHabit(habit);
        setDate(date);
        setCompletion(completion);
    }

    public void setHabit(Habit habit) {
        put("habit", habit);
    }

    public void setDate(Date date) {
        put("date", date);
    }

    public void setCompletion(Boolean completion) {
        put("completed", completion);
    }
}
