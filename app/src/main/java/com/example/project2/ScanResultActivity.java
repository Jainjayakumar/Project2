package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Patterns;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScanResultActivity extends AppCompatActivity {
    String posttext="Press the button",scanResult;
    TextView respdisp;
    String url = "http://192.168.42.204:8000/";
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
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        //check Internet connection
        if(internet||wifi)
        {
            if (Patterns.WEB_URL.matcher(scanResult).matches())
            {
                try {
                    URL url = new URL(scanResult);
                    String host = url.getHost();
                    if (host.contains("192.168.42.186")) {
                        try {
                            getRequest(scanResult);
                        }catch (NullPointerException | IOException e){
                            e.printStackTrace();
                        }finally {
                            respdisp.setMovementMethod(new ScrollingMovementMethod());
                            respdisp.setText(posttext);
                        }

                    }else {
                        respdisp.setText(host);
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }

            }
        }else{
            new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.title)
                    .setTitle("No internet connection")
                    .setMessage("Please turn on mobile data")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //code for exit
                            //Intent intent = new Intent(Intent.ACTION_MAIN);
                            //intent.addCategory(Intent.CATEGORY_HOME);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //startActivity(intent);
                            //Intent intent = new Intent(ScanResultActivity.this, MainActivity.class);
                            //startActivity(intent);
                        }

                    })
                    .show();
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
