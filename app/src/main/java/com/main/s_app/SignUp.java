package com.main.s_app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    EditText mEmail, mFirstName, mSurname, mUsername, mPassword, mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        mEmail = findViewById(R.id.emailEditText);
        mFirstName = findViewById(R.id.firstNameEditText);
        mSurname = findViewById(R.id.surnameEditText);
        mUsername = findViewById(R.id.usernameEditText);
        mPassword = findViewById(R.id.passwordEditText);
        mPasswordConfirm = findViewById(R.id.confirmPasswordEditText);
    }

    public void onSignUp(View v) {
        String email = mEmail.getText().toString(),
                password = mPassword.getText().toString();
        final Context context = this;
        //Create a user in the Firebase Authentication DB
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Set Username as DisplayName for the new user
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user != null) {
                            UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mUsername.getText().toString()).build();
                            user.updateProfile(changeRequest);
                        }
                        createFirebaseUser();
                        startActivity(new Intent(context, MainActivity.class));

                    }
                });
    }

    //Put the user in the Firebase Realtime DB
    private void createFirebaseUser() {
        String firstName = mFirstName.getText().toString(),
                surname = mSurname.getText().toString(),
                username = mUsername.getText().toString(),
                email = mEmail.getText().toString();

        DatabaseReference userRef = mDatabase.getReference("users");
        User user = new User(email, firstName, surname, username);
        userRef.push().setValue(user);
    }
}
