package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScanResultActivity extends AppCompatActivity {
    String posttext="Press the button",scanResult;
    TextView respdisp;
    String url = "http://192.168.42.186:8000/";
    String serverResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        respdisp = findViewById(R.id.textView2);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scanResult = extras.getString("ScanResult"); /* Retrieving text of QR Code */
            url = extras.getString("url");
            respdisp.setText(scanResult);
        }
        try {
            getRequest(scanResult);
        }catch (NullPointerException | IOException e){
            e.printStackTrace();
        }finally {
            respdisp.setText(posttext);
        }
    }
    public void getRequest(String scanResult) throws IOException{

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
            respdisp.setText(posttext);
        }

    }
}
