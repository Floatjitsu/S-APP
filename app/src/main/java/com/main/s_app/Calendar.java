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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Calendar extends Fragment {

    final FirebaseSneakerRelease firebaseSneakerRef = new FirebaseSneakerRelease();
    @SuppressLint("SimpleDateFormat")
    final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    RecyclerView mSneakerRelease;
    CalendarView mCalendar;
    TextView mNothingFound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mCalendar = myView.findViewById(R.id.calendarView);
        mNothingFound = myView.findViewById(R.id.text_nothing_found);
        mSneakerRelease = myView.findViewById(R.id.rv_sneaker_releases);
        mSneakerRelease.hasFixedSize();
        mSneakerRelease.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseSneakerRef.getSneakerReleasesToRecyclerView(format.format(mCalendar.getDate()), mSneakerRelease, getActivity(), mNothingFound);

        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                firebaseSneakerRef.getSneakerReleasesToRecyclerView(getDate(year, month, dayOfMonth), mSneakerRelease, getActivity(), mNothingFound);
            }
        });

        return myView;
    }

    private String getDate(int year, int month, int dayOfMonth) {
        int nMonth = month + 1;
        String m = String.valueOf(nMonth);
        if(m.length() < 2) {
            m = "0" + nMonth;
        }
        if(String.valueOf(dayOfMonth).length() < 2) {
            return "0" + dayOfMonth + "/" + m + "/" + year;
        }
        return dayOfMonth + "/" + m + "/" + year;
    }
}
