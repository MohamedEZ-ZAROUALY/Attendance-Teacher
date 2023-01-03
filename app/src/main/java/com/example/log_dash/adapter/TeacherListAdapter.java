package com.example.log_dash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.log_dash.R;
import com.example.log_dash.adapter.TeacherListHolder;
import com.example.modal.Subject;
import com.example.modal.Teacher;


import java.util.ArrayList;
import java.util.List;

public class TeacherListAdapter extends  RecyclerView.Adapter<TeacherListHolder> {
    private List<Subject> teachersList;

    public TeacherListAdapter(List<Subject> teachersList) {
        this.teachersList = teachersList;
    }

    @NonNull
    @Override
    public TeacherListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_students_list, parent, false);
        return new TeacherListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListHolder holder, int position) {
        Subject subject = teachersList.get(position);
        holder.name.setText(subject.getName());
        holder.location.setText(subject.getName());
        holder.branch.setText(subject.getName());
    }

    @Override
    public int getItemCount() {
        return teachersList.size();
    }
}
;