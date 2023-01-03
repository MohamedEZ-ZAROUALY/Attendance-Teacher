package com.example.log_dash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.modal.Teacher;
import com.example.retrofit.EmployeeAPI;
import com.example.retrofit.RetrofitService;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        EditText old_password = (EditText) findViewById(R.id.old_password);
        EditText new_password = (EditText) findViewById(R.id.new_password);
        EditText re_new_password = (EditText) findViewById(R.id.re_new_password);
        Button register_btn = (Button) findViewById(R.id.register_btn);

        RetrofitService retrofitService = new RetrofitService();
        EmployeeAPI employeeApi = retrofitService.getRetrofit().create(EmployeeAPI.class);

        employeeApi.login("t1",old_password.getText().toString(),new_password.getText().toString())
                .enqueue(new Callback<Teacher>() {
                    @Override
                    public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                        Toast.makeText(ForgotPassActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ForgotPassActivity.this, DashboardActivity.class);
                        startActivity(i);
                        Log.d("tag", String.valueOf(response.body()));
                                i.putExtra("TEACHER", response.body().getId());
                                startActivity(i);
                            }
                            @Override
                            public void onFailure(Call<Teacher> call, Throwable t) {
                                Toast.makeText(ForgotPassActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                            }
                        });

                    }
    }









