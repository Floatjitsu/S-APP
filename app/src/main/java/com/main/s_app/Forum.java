package com.main.s_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class Forum extends Fragment {

    Toolbar mToolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_forum, container, false);
        mToolbar = myView.findViewById(R.id.toolbar_forum);
        if(getActivity() != null) { //Parent Activity is available
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar); //Set Toolbar
            setHasOptionsMenu(true); //Needed for the Options
        }
        return myView;
    }

    /*
    Create the Options Menu for the Toolbar
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forum_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*
    Option Menu Press Event in the Toolbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_addArticle) {
            //TODO: Add Article Activity or Fragment
        }
        return true;
    }
}
