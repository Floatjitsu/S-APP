package com.main.s_app;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.main.s_app.com.main.s_app.dialogs.LogoutDialog;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    SpeedDialView mAddArticle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_forum:
                    transaction.replace(R.id.fragment_placeholder, new Forum());
                    transaction.commit();
                    mAddArticle.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_news:
                    transaction.replace(R.id.fragment_placeholder, new News());
                    transaction.commit();
                    mAddArticle.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_calendar:
                    transaction.replace(R.id.fragment_placeholder, new Calendar());
                    transaction.commit();
                    mAddArticle.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();

        //Set up the Forum Fragment as the first Fragment the user sees when starting
        //the Main Activity
        getSupportFragmentManager().beginTransaction().add(
                R.id.fragment_placeholder, new Forum()
        ).commit();

        mAddArticle = findViewById(R.id.speedDial);
        mAddArticle.inflate(R.menu.add_article_menu);
        setOnClickListenerForAddArticle();
    }

    /*
    Read which item got pressed and start the Add Article Activity
     */
    private void setOnClickListenerForAddArticle() {
        final Intent intent = new Intent(this, AddArticleActivity.class);
        mAddArticle.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.add_text:
                        intent.putExtra("articleKind", R.string.add_text);
                        startActivity(intent);
                        return true;
                    case R.id.add_image:
                        intent.putExtra("articleKind", R.string.add_image);
                        startActivity(intent);
                        return true;
                    case R.id.add_link:
                        intent.putExtra("articleKind", R.string.add_link);
                        startActivity(intent);
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new LogoutDialog().show(getSupportFragmentManager(), "LogoutDialog");
    }
}
