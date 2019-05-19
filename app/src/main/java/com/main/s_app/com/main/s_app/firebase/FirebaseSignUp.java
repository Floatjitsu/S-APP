package com.main.s_app.com.main.s_app.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSignUp {

    private static final String USER_REFERENCE = "users";

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    public FirebaseSignUp() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Create a new User in the Firebase Auth DB
     * @param user new user
     * @param password new users password
     */
    public void singUpWithEmailAndPassword(final User user, String password) {
        auth.createUserWithEmailAndPassword(user.getEmailAdress(), password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Set Username as DisplayName for the new user
                        FirebaseUser currentUser = auth.getCurrentUser();
                        if(currentUser != null) {
                            String uId = currentUser.getUid();
                            UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getUsername()).build();
                            currentUser.updateProfile(changeRequest);
                            writeUserToFirebaseDb(user, uId);
                        }
                    }
                });
    }

    /**
     * Create a new user in the Firebase Realtime DB
     * @param user new user
     * @param uId uId of new user
     */
    private void writeUserToFirebaseDb(User user, String uId) {
        user.setuId(uId);
        DatabaseReference userRef = database.getReference(USER_REFERENCE);
        userRef.push().setValue(user);
    }
}
