package com.example.log_dash.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.log_dash.R;

public class TeacherListHolder extends RecyclerView.ViewHolder {
    TextView name, branch,location;

    public TeacherListHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.employeeListItem_name);
        location = itemView.findViewById(R.id.employeeListItem_location);
        branch = itemView.findViewById(R.id.employeeListItem_branch);

    }
}