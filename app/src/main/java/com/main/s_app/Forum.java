package com.main.s_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.s_app.com.main.s_app.firebase.FirebaseForum;

public class Forum extends Fragment {

    Toolbar mToolbar;
    RecyclerView mForum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_forum, container, false);

        mToolbar = myView.findViewById(R.id.toolbar_forum);
        if(getActivity() != null) { //Parent Activity is available
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar); //Set Toolbar
        }

        mForum = myView.findViewById(R.id.rv_forum);
        mForum.hasFixedSize();
        mForum.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Fill Fragment Content with Data from Firebase DB
        new FirebaseForum().getForumToRecyclerView(mForum, getActivity());

        return myView;
    }
}
