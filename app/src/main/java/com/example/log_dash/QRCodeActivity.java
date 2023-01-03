package com.example.log_dash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.modal.Session;
import com.example.retrofit.EmployeeAPI;
import com.example.retrofit.RetrofitService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class QRCodeActivity extends AppCompatActivity {
    String subject_id ;
    Session session_started;
    private static final int MAX_LENGTH = 16;

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    String data = random();

    int width = 500;
    int height = 500;

    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    BitMatrix bitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

    public QRCodeActivity() throws WriterException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Intent i = getIntent();
        subject_id = i.getStringExtra("subject_id");

        TextView countdown = findViewById(R.id.qr_title);

        int pixelColor;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelColor = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
                bitmap.setPixel(x, y, pixelColor);
            }
        }

        ImageView imageView = findViewById(R.id.qrcode);


        imageView.setImageBitmap(bitmap);

        RetrofitService retrofitService = new RetrofitService();
        EmployeeAPI employeeApi = retrofitService.getRetrofit().create(EmployeeAPI.class);

        employeeApi.getStudents(subject_id, data)
                .enqueue(new Callback<Session>() {
                    @Override
                    public void onResponse(Call<Session> call, Response<Session> response) {
                        Toast.makeText(QRCodeActivity.this, "QR SUCCESSFUL", Toast.LENGTH_SHORT).show();
                        session_started = response.body();
                    }

                    @Override
                    public void onFailure(Call<Session> call, Throwable t) {
                        Toast.makeText(QRCodeActivity.this, "QR failed!!!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(QRCodeActivity.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdown.setText("Done !");
                employeeApi.stopSession(session_started.getId())
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(QRCodeActivity.this, "QR SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                            }
                        });
            };

        }.start();


    }
}
