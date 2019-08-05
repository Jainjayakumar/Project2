package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText loginInputEmail, loginInputPassword;
    private TextInputLayout loginInputLayoutEmail, loginInputLayoutPassword;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginInputLayoutEmail = findViewById(R.id.login_input_layout_email);
        loginInputLayoutPassword = findViewById(R.id.login_input_layout_password);
        progressBar = findViewById(R.id.progressBar);

        loginInputEmail = findViewById(R.id.login_input_email);
        loginInputPassword = findViewById(R.id.login_input_password);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnLinkToSignUp = findViewById(R.id.btn_link_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        btnLinkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUPActivity.class);
                startActivity(intent);
            }
        });
    }
    private void submitForm() {
        String email = loginInputEmail.getText().toString().trim();
        String password = loginInputPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        loginInputLayoutEmail.setErrorEnabled(false);
        loginInputLayoutPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        //authenticate user
        if(email.equals("admin@gmail.com") && password.equals("admin123")) {
            Toast.makeText(getApplicationContext(),
                    "Redirecting...",Toast.LENGTH_SHORT).show();
            //User has successfully logged in, save this information
            // We need an Editor object to make preference changes.
            SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0); // 0 - for private mode
            SharedPreferences.Editor editor = settings.edit();
            //Set "hasLoggedIn" to true
            editor.putBoolean("hasLoggedIn", true);
            // Commit the edits!
            editor.apply();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else{
            Toast.makeText(getApplicationContext(),
                    "Wrong Credentials",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkEmail() {
        String email = loginInputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            loginInputLayoutEmail.setErrorEnabled(true);
            loginInputLayoutEmail.setError(getString(R.string.err_msg_email));
            loginInputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(loginInputEmail);
            return false;
        }
        loginInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {

        String password = loginInputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            loginInputLayoutPassword.setError(getString(R.string.err_msg_password));
            loginInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(loginInputPassword);
            return false;
        }
        loginInputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
