package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUPActivity extends AppCompatActivity {
    Button btnSignUp,btnLinkToLogIn;
    EditText signupInputEmail,signupInputPassword;
    TextInputLayout signupInputLayoutEmail,signupInputLayoutPassword;
    ProgressBar progressBar;
    String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupInputLayoutEmail = findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = findViewById(R.id.signup_input_layout_password);
        progressBar = findViewById(R.id.progressBar);

        signupInputEmail =  findViewById(R.id.signup_input_email);
        signupInputPassword = findViewById(R.id.signup_input_password);

        btnSignUp = findViewById(R.id.btn_signup);
        btnLinkToLogIn = findViewById(R.id.btn_link_login);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });

        btnLinkToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUPActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void submitForm() {

        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);

        progressBar.setVisibility(View.VISIBLE);
        //create user

        Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
    }

    private boolean checkEmail() {
        String email = signupInputEmail.getText().toString().trim();
        if (email.isEmpty() || !isEmailValid(email)) {

            signupInputLayoutEmail.setErrorEnabled(true);
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            signupInputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputEmail);
            return false;
        }
        signupInputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean checkPassword() {

        String password = signupInputPassword.getText().toString().trim();
        if (password.isEmpty() || !isPasswordValid(password)) {

            signupInputLayoutPassword.setError(getString(R.string.err_msg_password));
            signupInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputPassword);
            return false;
        }
        signupInputLayoutPassword.setErrorEnabled(false);
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
