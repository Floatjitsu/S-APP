package com.main.s_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.main.s_app.com.main.s_app.firebase.FirebaseSneakerRelease;
import com.main.s_app.com.main.s_app.firebase.Sneaker;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Calendar extends Fragment {

    final FirebaseSneakerRelease firebaseSneakerRef = new FirebaseSneakerRelease();

    RecyclerView mSneakerRelease;
    //RecyclerView.Adapter mSneakerReleaseAdapter;
    CalendarView mCalendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mCalendar = myView.findViewById(R.id.calendarView);
        mSneakerRelease = myView.findViewById(R.id.rv_sneaker_releases);
        mSneakerRelease.hasFixedSize();
        mSneakerRelease.setLayoutManager(new LinearLayoutManager(getActivity()));

        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("dd.M.YYYY");
        firebaseSneakerRef.getSneakerReleasesToRecyclerView(format.format(mCalendar.getDate()), mSneakerRelease);

        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //mTestText.setText(convertDate(year, month, dayOfMonth));
                String date = dayOfMonth + "." + (month + 1) + "." + year;
                firebaseSneakerRef.getSneakerReleasesToRecyclerView(date, mSneakerRelease);
            }
        });

        /*

        ArrayList<Sneaker> sneakers = new ArrayList<>();
        sneakers.add(new Sneaker("Nike", "AirMax 270"));
        sneakers.add(new Sneaker("Nike", "Shox R4"));
        sneakers.add(new Sneaker("New Balance", "274"));
        sneakers.add(new Sneaker("Reebok", "Classics"));
        sneakers.add(new Sneaker("Nike", "React 55 SE"));

        mSneakerReleaseAdapter = new SneakerReleaseAdapter(sneakers);
        mSneakerRelease.setAdapter(mSneakerReleaseAdapter);
        */

        return myView;
    }

    private String convertDate(int year, int month, int dayOfMonth) {
        String m = String.valueOf(month + 1);
        if(m.length() < 2) {
            m = "0" + m;
        }
        return dayOfMonth + "." + m + "." + year;
    }
}
