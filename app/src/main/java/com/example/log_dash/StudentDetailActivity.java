package com.example.log_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.log_dash.adapter.AbsenceAdapter;
import com.example.log_dash.adapter.SubjectAdapter;
import com.example.modal.Absence;
import com.example.modal.Student;
import com.example.modal.Subject;
import com.example.retrofit.EmployeeAPI;
import com.example.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailActivity extends AppCompatActivity {

    String studentId ;
    String subjectId ;
    String studentName ;
    List<Absence> absences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        studentId = i.getStringExtra("SELECTED_STUDENT");
        subjectId = i.getStringExtra("SELECTED_SUBJECT");
        studentName = i.getStringExtra("STUDENT_NAME");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        RetrofitService retrofitService = new RetrofitService();
        EmployeeAPI employeeApi = retrofitService.getRetrofit().create(EmployeeAPI.class);

        Call<List<Absence>> call = employeeApi.getStudentAttendance(studentId,subjectId);

        call.enqueue(new Callback<List<Absence>>() {
            @Override
            public void onResponse(Call<List<Absence>> call, Response<List<Absence>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(StudentDetailActivity.this, "Code: " + response.code() ,Toast.LENGTH_LONG).show();
                    return;
                }
                absences = response.body();
                Log.d("H","HHHHHHHHHHHHHHHHHHHHHH");
                for ( Absence x : absences ) {
                    Log.d("H","s : "+ x.getId() );
                }

                Log.d("H","HHHHHHHHHHHHHHHHHHHHHH");

                TextView absences_count = (TextView) findViewById(R.id.subject_absences);
                TextView student_name = (TextView) findViewById(R.id.student_name);

                absences_count.setText(String.valueOf(absences.size()));
                student_name.setText(studentName);

                populateListView((ArrayList<Absence>) absences);

            }
            @Override
            public void onFailure(Call<List<Absence>> call, Throwable t) {
                Toast.makeText(StudentDetailActivity.this, "Failed to load histories" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView(ArrayList<Absence> histories){
        // Create the adapter to convert the array to views
        AbsenceAdapter adapter = new AbsenceAdapter(this, histories);
        ListView listView = (ListView) findViewById(R.id.listview);
        // Attach the adapter to a ListView
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);


    }

}