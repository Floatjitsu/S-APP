package com.main.s_app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class AddArticleActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        mToolbar = findViewById(R.id.toolbar_add_article);
        setUpToolbar();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch(bundle.getInt("articleKind")) {
                case R.string.add_text:
                    mToolbar.setTitle(R.string.text_post);
                    transaction.add(R.id.add_article_placeholder, new AddText()).commit();
                    break;
                case R.string.add_image:
                    mToolbar.setTitle(R.string.image_post);
                    transaction.add(R.id.add_article_placeholder, new AddImage()).commit();
                    break;
                case R.string.add_link:
                    mToolbar.setTitle(R.string.link_post);
                    transaction.add(R.id.add_article_placeholder, new AddLink()).commit();
                    break;
            }
        }
    }

    /*
    Create the Options Menu for the Activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_article_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
