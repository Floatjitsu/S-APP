package com.main.s_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        tryLogin(mAuth.getCurrentUser());

        mEmail = findViewById(R.id.emailEditTextLogin);
        mPassword = findViewById(R.id.passwordEditTextLogin);
    }

    //Start SignUp Activity
    public void onSignUp(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    //Login User with email and password
    public void onLogin(View view) {
        String email = mEmail.getText().toString(),
                password = mPassword.getText().toString();
        final Context context = this;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Login was successful
                        if(task.isSuccessful() && mAuth.getCurrentUser() != null) {
                            startActivity(new Intent(context, MainActivity.class));
                            Toast.makeText(context, "Welcome " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_LONG).show();
                        } else { //Login not successful
                            Toast.makeText(context, "Wrong User Credentials!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //If the user is still logged in, start MainActivity
    private void tryLogin(FirebaseUser currentUser) {
        if(currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
