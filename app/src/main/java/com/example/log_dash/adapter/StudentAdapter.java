package com.example.log_dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.log_dash.R;
import com.example.modal.Student;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(@NonNull Context context, ArrayList<Student> histories) {
        super(context, 0, histories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Student subject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_students_item, parent, false);
        }
        // Lookup view for data population
        TextView courseName = (TextView) convertView.findViewById(R.id.employeeListItem_name);
        TextView date = (TextView) convertView.findViewById(R.id.employeeListItem_location);
        TextView presence = (TextView) convertView.findViewById(R.id.employeeListItem_branch);

        // Populate the data into the template view using the data object
        courseName.setText(subject.getFirstName() +" "+ subject.getLastName());
        date.setText(subject.getUserName());
        presence.setText("");

        // Return the completed view to render on screen
        return convertView;
    }
}
