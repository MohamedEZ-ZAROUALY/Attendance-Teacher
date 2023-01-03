package com.example.retrofit;

import com.example.modal.Absence;
import com.example.modal.Employee;
import com.example.modal.Session;
import com.example.modal.Student;
import com.example.modal.Subject;
import com.example.modal.Teacher;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmployeeAPI {
    @GET("api/employees")
    Call<List<Employee>> getAllEmployees();

    @GET("api/subjects")
    Call<List<Subject>> getSubjects(@Query("teacher_id") String teacher_id);

    @GET("api/students")
    Call<List<Student>> getStudents(@Query("classes_id") String classe_id);

    @POST("api/startSession")
    Call<Session> getStudents(@Query("subject_id") String subject_id, @Query("qrCode") String qrCode);

    @POST("api/stopSession")
    Call<String> stopSession(@Query("session_id") String session_id);

    @GET("api/login")
    Call<Teacher> login(@Query("username") String username,@Query("password") String password/*@Body String username, String password*/);

    @GET("api/studentAttendance")
    Call<List<Absence>> getStudentAttendance(@Query("student_id") String student_id,@Query("subject_id") String subject_id);

    @GET("api/updatePassword")
    Call<Teacher> login(@Body String username, String oldPassword, String newPassword);
}
