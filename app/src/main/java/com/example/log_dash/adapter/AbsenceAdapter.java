package com.example.log_dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.log_dash.R;
import com.example.modal.Absence;
import com.example.modal.Subject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AbsenceAdapter extends ArrayAdapter<Absence> {

    public AbsenceAdapter(@NonNull Context context, ArrayList<Absence> histories) {
        super(context, 0, histories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Absence subject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_students_item, parent, false);
        }
        // Lookup view for data population
        TextView courseName = (TextView) convertView.findViewById(R.id.employeeListItem_name);
        TextView date = (TextView) convertView.findViewById(R.id.employeeListItem_location);
        TextView presence = (TextView) convertView.findViewById(R.id.employeeListItem_branch);

        // Populate the data into the template view using the data object
        //courseName.setText(String.valueOf(subject.getDate().getDay())+" - "+String.valueOf(subject.getDate().getMonth()+1)+" - "+String.valueOf(subject.getDate().getYear()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE ,  dd - MM - yyyy");
        String formatted = dateFormat.format(subject.getDate());

        courseName.setText(formatted);
        date.setText(subject.getSession().getSubject().getName());
        presence.setText("");

        // Return the completed view to render on screen
        return convertView;
    }
}
