package com.main.s_app;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.main.s_app.com.main.s_app.firebase.TextPost;

import java.util.Objects;

public class PostComments extends AppCompatActivity {

    Bundle mBundle;

    Toolbar mToolbar;
    LinearLayout mLinearLayout;
    TextView mPostTitle, mPostPostedBy, mPostDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        mToolbar = findViewById(R.id.toolbar_post_comments);
        setUpToolbar();

        mPostDate = findViewById(R.id.post_comments_date);
        mPostTitle = findViewById(R.id.post_comments_title);
        mPostPostedBy = findViewById(R.id.post_comments_posted_by);

        mBundle = getIntent().getExtras();

        mLinearLayout = findViewById(R.id.post_comments_placeholder);

        initPost();
    }

    private void initPost() {
        //Post title, date and posted by are always part of a post
        mPostDate.setText(mBundle.getString(Constants.KEY_POST_DATE));
        mPostPostedBy.setText(Objects.requireNonNull(mBundle.getString(Constants.KEY_POST_POSTED_BY)).replace("Posted by", ""));
        mPostTitle.setText(mBundle.getString(Constants.KEY_POST_DATE));
        //Check which kind of posts got pressed in the forum
        switch (Objects.requireNonNull(mBundle.getString(Constants.KEY_POST_KIND))) {
            case TextPost.POST_KIND:
                initTextPost();
                break;
        }
        //TODO: Add other cases for image post and link post
    }

    private void initTextPost() {
        TextView textPostContent = new TextView(this);
        textPostContent.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        textPostContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textPostContent.setText(mBundle.getString(Constants.KEY_TEXT_POST_CONTENT));
        mLinearLayout.addView(textPostContent);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }

}
