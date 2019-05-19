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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.main.s_app.com.main.s_app.firebase.User;

public class SignUp extends AppCompatActivity {

    private static final String USER_REFERENCE = "users";
    private final Context CONTEXT = this;

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
                password = mPassword.getText().toString(),
                passwordConfirm = mPasswordConfirm.getText().toString(),
                username = mUsername.getText().toString(),
                firstName = mFirstName.getText().toString(),
                surname = mSurname.getText().toString();

        if(isValid(email, password, passwordConfirm, username, firstName, surname)) {
            User user = new User(email, firstName, surname, username);
            createUserWithEmailAndPassword(user, password);
        }
    }

    /*
    Create a new User in the Firebase Auth DB
     */
    private void createUserWithEmailAndPassword(final User user, String password) {
        mAuth.createUserWithEmailAndPassword(user.getEmailAdress(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if(currentUser != null) {
                            String uId = currentUser.getUid();
                            UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getUsername()).build();
                            currentUser.updateProfile(changeRequest);
                            writeUserToFirebaseDb(user, uId);
                            startActivity(new Intent(CONTEXT, Login.class));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CONTEXT, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /*
    Create a new user in the Firebase Realtime DB
     */
    private void writeUserToFirebaseDb(User user, String uId) {
        user.setuId(uId);
        DatabaseReference userRef = mDatabase.getReference(USER_REFERENCE);
        userRef.push().setValue(user);
    }

    /*
    Check if the user input for the sign up is valid
     */
    private boolean isValid(String email, String password, String passwordConfirm, String username, String firstName, String surname) {
        if(email.equals("")) {
            mEmail.setError("Please enter your email address");
            return false;
        } else if(password.equals("")) {
            mPassword.setError("Please enter a password");
            return false;
        } else if(passwordConfirm.equals("")) {
            mPasswordConfirm.setError("Please confirm your password");
            return false;
        } else if(username.equals("")) {
            mUsername.setError("Please enter an username");
            return false;
        } else if(firstName.equals("")) {
            mFirstName.setError("Please enter your first name");
            return false;
        } else if(surname.equals("")) {
            mSurname.setError("Please enter your surname");
            return false;
        } else if(!password.equals(passwordConfirm)) {
            mPasswordConfirm.setError("Passwords do not match!");
            return false;
        }
        return true;
    }


}
