package com.main.s_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.main.s_app.com.main.s_app.firebase.FirebaseSignUp;
import com.main.s_app.com.main.s_app.firebase.User;

public class SignUp extends AppCompatActivity {

    EditText mEmail, mFirstName, mSurname, mUsername, mPassword, mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.emailEditText);
        mFirstName = findViewById(R.id.firstNameEditText);
        mSurname = findViewById(R.id.surnameEditText);
        mUsername = findViewById(R.id.usernameEditText);
        mPassword = findViewById(R.id.passwordEditText);
        mPasswordConfirm = findViewById(R.id.confirmPasswordEditText);
    }

    public void onSignUp(View v) {
        String email = mEmail.getText().toString(),
                password = mPassword.getText().toString(),
                username = mUsername.getText().toString(),
                firstName = mFirstName.getText().toString(),
                surname = mSurname.getText().toString();

        User user = new User(email, firstName, surname, username);
        FirebaseSignUp signUp = new FirebaseSignUp();
        signUp.singUpWithEmailAndPassword(user, password);

        startActivity(new Intent(this, Login.class));
    }
}
