package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BatchActivity extends AppCompatActivity {
    Button search_btn;
    String serverResponse;
    String posttext, scanResult;
    TextView Result;
    EditText batchid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);

        search_btn = findViewById(R.id.btn_batch);
        Result = findViewById(R.id.Batchid);
        batchid = findViewById(R.id.batch);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getRequest();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }
    public void getRequest() throws IOException {

        scanResult = "http://192.168.42.186:8000/batch?BATCHID=" + batchid.getText().toString();

        final OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url(scanResult)
                    .get()
                    //.addHeader("Content-Type","text/html")
                    .addHeader("Content-Type", "application/json")
                    //.addHeader("Authorization", "Your Token")
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();
            serverResponse = response.body().string();
            posttext = serverResponse;
        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }finally {
            Result.setMovementMethod(new ScrollingMovementMethod());
            Result.setText(posttext);
        }
    }
}
