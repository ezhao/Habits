package com.herokuapp.ezhao.habits.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import java.io.Serializable;

@ParseClassName("Habit")
public class Habit extends ParseObject implements Serializable {
    public Habit() {
    }

    public Habit(String description) {
        super();
        setDescription(description);
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public String getDescription() {
        return getString("description");
    }
}
