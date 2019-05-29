package com.main.s_app.com.main.s_app.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.s_app.SneakerReleaseAdapter;

import java.util.ArrayList;

public class FirebaseSneakerRelease {

    private static final String SNEAKER_PATH = "sneakers";
    private FirebaseDatabase mDatabase;

    public FirebaseSneakerRelease() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void getSneakerReleasesToRecyclerView(String date, final RecyclerView sneakerReleases, final Context activityContext) {
        DatabaseReference sneakersRef = mDatabase.getReference(SNEAKER_PATH);
        sneakersRef.orderByChild("releaseDate").equalTo(date)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Sneaker> sneakers = new ArrayList<>();
                        for(DataSnapshot sneaker : dataSnapshot.getChildren()) {
                            sneakers.add(sneaker.getValue(Sneaker.class));
                        }
                        sneakerReleases.setAdapter(new SneakerReleaseAdapter(sneakers, activityContext));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void addReleaseToFirebase(Sneaker sneaker) {
        mDatabase.getReference(SNEAKER_PATH).push().setValue(sneaker);
    }
}
