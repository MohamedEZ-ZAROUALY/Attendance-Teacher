package com.example.log_dash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.log_dash.adapter.SubjectAdapter;
import com.example.modal.Employee;
import com.example.modal.Subject;
import com.example.retrofit.EmployeeAPI;
import com.example.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity {
    String teacherId;
    String teacherName;
    List<Subject> histories ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent i = getIntent();
        teacherId = i.getStringExtra("TEACHER");
        teacherName = i.getStringExtra("TEACHER_NAME");
        TextView teacher_name = (TextView) findViewById(R.id.text_dashboard);
        teacher_name.setText(teacherName);

        loadHistoryList();

    }

    private void loadHistoryList(){


        RetrofitService retrofitService = new RetrofitService();
        EmployeeAPI employeeApi = retrofitService.getRetrofit().create(EmployeeAPI.class);

        Call<List<Subject>> call = employeeApi.getSubjects(teacherId);

        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(DashboardActivity.this, "Code: " + response.code() ,Toast.LENGTH_LONG).show();
                    return;
                }
                histories = response.body();
                Log.d("H","HHHHHHHHHHHHHHHHHHHHHH");
                for ( Subject x : histories ) {
                    Log.d("H","s : "+ x.getName());
                }

                Log.d("H","HHHHHHHHHHHHHHHHHHHHHH");

                populateListView((ArrayList<Subject>) histories);
            }
            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Failed to load histories" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateListView(ArrayList<Subject> histories){
        // Create the adapter to convert the array to views
        SubjectAdapter adapter = new SubjectAdapter(this, histories);
        ListView listView = (ListView) findViewById(R.id.listview);
        // Attach the adapter to a ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text


                String selectedSubject = histories.get(position).getId();
                String selectedClass = histories.get(position).getClasses().getId();
                // Create an Intent to start the second activity
                Intent intent = new Intent(DashboardActivity.this, StudentActivity.class);

                // Add the selected item text as an extra to the Intent
                intent.putExtra("SELECTED_SUBJECT", selectedSubject);
                intent.putExtra("SELECTED_CLASS", selectedClass);
                // Start the new activity
                startActivity(intent);
            }
        });
    }

}
