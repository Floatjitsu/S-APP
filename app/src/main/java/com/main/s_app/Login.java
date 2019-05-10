package com.main.s_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emailEditTextLogin);
        mPassword = findViewById(R.id.passwordEditTextLogin);
    }

    public void onSignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    public void onLogin(View view) {

    }
}
