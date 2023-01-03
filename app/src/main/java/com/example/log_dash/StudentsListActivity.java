package com.example.log_dash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.log_dash.adapter.EmployeeAdapter;
import com.example.log_dash.adapter.SelectListener;
import com.example.log_dash.adapter.TeacherListAdapter;
import com.example.modal.Student;
import com.example.modal.Subject;
import com.example.retrofit.EmployeeAPI;
import com.example.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsListActivity extends AppCompatActivity implements SelectListener {

  private RecyclerView recyclerView;
  private String teacher ;
  private List<Subject> employeeList ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_students_list);

    recyclerView = findViewById(R.id.employeeList_recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    teacher = (String) getIntent().getSerializableExtra("TEACHER");

    FloatingActionButton floatingActionButton = findViewById(R.id.employeeList_fab);
    floatingActionButton.setOnClickListener(view -> {
      Intent intent = new Intent(this, RegisterActivity.class);
      startActivity(intent);
    });
  }

  private void loadEmployees() {
    RetrofitService retrofitService = new RetrofitService();
    EmployeeAPI employeeApi = retrofitService.getRetrofit().create(EmployeeAPI.class);
    teacher = (String) getIntent().getSerializableExtra("TEACHER");
    employeeApi.getSubjects(teacher)
          .enqueue(new Callback<List<Subject>>() {
          @Override
          public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
            populateListView(response.body());
          }

          @Override
          public void onFailure(Call<List<Subject>> call, Throwable t) {
            Toast.makeText(StudentsListActivity.this, "Failed to load employees", Toast.LENGTH_SHORT).show();
          }
        });
  }

  private void populateListView(List<Subject> employeeList) {
    TeacherListAdapter employeeAdapter = new TeacherListAdapter(employeeList);
    recyclerView.setAdapter(employeeAdapter);
  }

  @Override
  protected void onResume() {
    super.onResume();
    loadEmployees();
  }


  @Override
  public void onItemClicked(Student student) {

  }
}