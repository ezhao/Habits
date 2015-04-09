package com.herokuapp.ezhao.habits.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.herokuapp.ezhao.habits.R;
import com.herokuapp.ezhao.habits.models.Habit;

import java.util.Date;
import java.util.List;

public class HabitAdapter extends ArrayAdapter<Habit> {
    private static class ViewHolder {
        TextView tvHabitDescription;
        CheckBox cbCompletion;
    }

    public HabitAdapter(Context context, List<Habit> habits) {
        super(context, 0, habits);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Habit habit = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_habit, parent, false);
            viewHolder.tvHabitDescription = (TextView) convertView.findViewById(R.id.tvHabitDescription);
            viewHolder.cbCompletion = (CheckBox) convertView.findViewById(R.id.cbCompletion);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvHabitDescription.setText(habit.getDescription());

        // TODO(emily) reflect completion
        //Date date = new Date();

        viewHolder.cbCompletion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO(emily) set completion
            }
        });

        return convertView;
    }
}
