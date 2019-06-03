package com.main.s_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.Toast;

import java.util.HashMap;

public class ManageRssFeed extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    CheckedTextView mSoleCollector, mNiceKicks, mFinishLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rss_feed);

        mSoleCollector = findViewById(R.id.check_sole_collector);
        mNiceKicks = findViewById(R.id.check_nice_kicks);
        mFinishLine = findViewById(R.id.check_finish_line);

        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_KEY, Context.MODE_PRIVATE);

        updateUi();
    }

    /*
    Press Event for any of the CheckedTextViews
     */
    public void onCheckboxChange(View view) {
        CheckedTextView textView = (CheckedTextView) view;
        textView.toggle();
        if(textView.isChecked()) {
            textView.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
            addToSharedPreferences(textView.getText().toString());
        } else {
            textView.setCheckMarkDrawable(null);
            deleteFromSharedPreferences(textView.getText().toString());
        }
    }

    private void updateUi() {
        for(String s : Constants.getSharedPreferencesKeys()) {
            if(sharedPreferences.getBoolean(s, false)) {
                switch (s) {
                    case Constants.SOLE_COLLECTOR:
                        mSoleCollector.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
                        break;
                    case Constants.FINISH_LINE:
                        mFinishLine.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
                        break;
                    case Constants.NICE_KICKS:
                        mNiceKicks.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
                        break;
                }
            }
        }
    }

    private void deleteFromSharedPreferences(String subscription) {
        editor = sharedPreferences.edit();
        editor.putBoolean(subscription, false);
        editor.apply();
    }

    private void addToSharedPreferences(String subscription) {
        editor = sharedPreferences.edit();
        editor.putBoolean(subscription, true);
        editor.apply();
    }
}
