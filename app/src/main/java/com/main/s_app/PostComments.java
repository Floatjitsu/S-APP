package com.main.s_app;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.s_app.com.main.s_app.firebase.FirebaseForum;
import com.main.s_app.com.main.s_app.firebase.ImagePost;
import com.main.s_app.com.main.s_app.firebase.LinkPost;
import com.main.s_app.com.main.s_app.firebase.TextPost;

import java.util.Objects;

public class PostComments extends AppCompatActivity {

    Bundle mBundle;

    Toolbar mToolbar;
    LinearLayout mLinearLayout;
    TextView mPostTitle, mPostPostedBy, mPostDate, mPostId;
    EditText mPostComment; //The new comment
    ImageButton mPostCommentButton;

    RecyclerView mComments; //Post comments
    RecyclerView.Adapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        mToolbar = findViewById(R.id.toolbar_post_comments);
        setUpToolbar();

        mPostDate = findViewById(R.id.post_comments_date);
        mPostTitle = findViewById(R.id.post_comments_title);
        mPostPostedBy = findViewById(R.id.post_comments_posted_by);
        mPostId = findViewById(R.id.post_comments_id);
        mPostCommentButton = findViewById(R.id.new_comment_button);
        mPostComment = findViewById(R.id.post_new_comment);

        mPostComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() == 0) {
                    mPostCommentButton.setEnabled(false);
                } else {
                    mPostCommentButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mComments = findViewById(R.id.rv_post_comments);
        mComments.hasFixedSize();
        mComments.setLayoutManager(new LinearLayoutManager(this));

        mBundle = getIntent().getExtras();

        mLinearLayout = findViewById(R.id.post_comments_placeholder);

        initPost();
        new FirebaseForum().getPostCommentsToRecyclerView(mPostId.getText().toString(), mComments);

    }

    private void initPost() {
        //Post title, date, id and posted by are always part of a post
        mPostDate.setText(mBundle.getString(Constants.KEY_POST_DATE));
        mPostPostedBy.setText(Objects.requireNonNull(mBundle.getString(Constants.KEY_POST_POSTED_BY)).replace("Posted by", ""));
        mPostTitle.setText(mBundle.getString(Constants.KEY_POST_TITLE));
        mPostId.setText(mBundle.getString(Constants.KEY_POST_ID));
        //Check which kind of posts got pressed in the forum and initialize it
        switch (Objects.requireNonNull(mBundle.getString(Constants.KEY_POST_KIND))) {
            case TextPost.POST_KIND:
                initTextPost();
                break;
            case ImagePost.POST_KIND:
                initImagePost();
                break;
            case LinkPost.POST_KIND:
                initLinkPost();
                break;
        }
    }

    /*
    Initialize a Preview of a Text Post
     */
    private void initTextPost() {
        TextView textPostContent = new TextView(this);
        textPostContent.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        textPostContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textPostContent.setText(mBundle.getString(Constants.KEY_TEXT_POST_CONTENT));
        mLinearLayout.addView(textPostContent);
    }

    /*
    Initialize a Preview of an Image Post
     */
    private void initImagePost() {
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 400);
        image.setLayoutParams(layoutParams);
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child(Objects.requireNonNull(mBundle.getString(Constants.KEY_IMAGE_POST_IMAGE_PATH)));
        GlideApp.with(this).load(reference).into(image);
        mLinearLayout.addView(image);
        TextView imageDesc = new TextView(this);
        imageDesc.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        imageDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        imageDesc.setText(mBundle.getString(Constants.KEY_IMAGE_POST_DESC));
        mLinearLayout.addView(imageDesc);
    }

    /*
    Initialize a Preview of a Link Post
     */
    private void initLinkPost() {
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
        layoutParams.setMargins(0, 0, 35, 0);
        image.setLayoutParams(layoutParams);
        GlideApp.with(this).load(mBundle.getString(Constants.KEY_LINK_POST_IMAGE_PATH)).into(image);
        mLinearLayout.addView(image);
        TextView linkDesc = new TextView(this);
        linkDesc.setTextColor(ContextCompat.getColor(this, R.color.textColor));
        linkDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        linkDesc.setText(mBundle.getString(Constants.KEY_LINK_POST_DESC));
        mLinearLayout.addView(linkDesc);
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

    /*
    Add a new comment on the post
     */
    public void onCommentPost(View view) {
        String comment = mPostComment.getText().toString();
        String postId = mPostId.getText().toString();

        new FirebaseForum().addCommentToPost(comment, postId);
        mPostComment.setText("");
        hideSoftKeyboard(this, view);
        mPostComment.clearFocus();
    }

    void hideSoftKeyboard(Activity activity, View v) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }
}
