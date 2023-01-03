package com.example.log_dash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.log_dash.adapter.StudentAdapter;
import com.example.log_dash.adapter.SubjectAdapter;
import com.example.modal.Student;
import com.example.modal.Student;
import com.example.retrofit.EmployeeAPI;
import com.example.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentActivity extends AppCompatActivity {
    String classeId ;
    String subjectId ;
    List<Student> histories ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        classeId = i.getStringExtra("SELECTED_CLASS");
        subjectId = i.getStringExtra("SELECTED_SUBJECT");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        loadHistoryList();
        ImageView btn_record = findViewById(R.id.image_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(new Intent(StudentActivity.this, QRCodeActivity.class));
                i.putExtra("subject_id", subjectId);
                startActivity(i);
            }
        });

    }

    private void loadHistoryList(){


        RetrofitService retrofitService = new RetrofitService();
        EmployeeAPI employeeApi = retrofitService.getRetrofit().create(EmployeeAPI.class);

        Call<List<Student>> call = employeeApi.getStudents(classeId);

        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(StudentActivity.this, "Code: " + response.code() ,Toast.LENGTH_LONG).show();
                    return;
                }
                histories = response.body();
                Log.d("H","HHHHHHHHHHHHHHHHHHHHHH");
                for ( Student x : histories ) {
                    Log.d("H","s : "+ x.getFirstName());
                }

                Log.d("H","HHHHHHHHHHHHHHHHHHHHHH");

                populateListView((ArrayList<Student>) histories);
            }
            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(StudentActivity.this, "Failed to load histories" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView(ArrayList<Student> histories){
        // Create the adapter to convert the array to views
        StudentAdapter adapter = new StudentAdapter(this, histories);
        ListView listView = (ListView) findViewById(R.id.listview);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text


                Student selectedStudent = histories.get(position);
                // Create an Intent to start the second activity
                Intent intent = new Intent(StudentActivity.this, StudentDetailActivity.class);

                // Add the selected item text as an extra to the Intent
                intent.putExtra("SELECTED_STUDENT", selectedStudent.getId());
                intent.putExtra("SELECTED_SUBJECT", subjectId);
                intent.putExtra("STUDENT_NAME", selectedStudent.getFirstName() +
                                                        " "+ selectedStudent.getLastName());
                // Start the new activity
                startActivity(intent);
            }
        });
    }
}
