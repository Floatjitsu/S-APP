package com.main.s_app.com.main.s_app.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

    /**
     * Fills the sneaker release recycler view with data from the Firebase DB
     * by a given date
     * @param date the date chosen by the user
     * @param sneakerReleases the recycler view
     * @param activityContext the activity context where the recycler view is located
     * @param nothingFound the text view shown if no data got found
     */
    public void getSneakerReleasesToRecyclerView(String date, final RecyclerView sneakerReleases, final Context activityContext, final TextView nothingFound) {
        DatabaseReference sneakersRef = mDatabase.getReference(SNEAKER_PATH);
        sneakersRef.orderByChild("releaseDate").equalTo(date)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Sneaker> sneakers = new ArrayList<>();
                        for(DataSnapshot sneaker : dataSnapshot.getChildren()) {
                            sneakers.add(sneaker.getValue(Sneaker.class));
                        }
                        if(sneakers.size() > 0) {
                            nothingFound.setVisibility(View.GONE);
                        } else {
                            nothingFound.setVisibility(View.VISIBLE);
                        }
                        sneakerReleases.setAdapter(new SneakerReleaseAdapter(sneakers, activityContext));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
