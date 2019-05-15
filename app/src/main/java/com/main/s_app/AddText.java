package com.main.s_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddText extends Fragment {

    EditText mTitle;
    EditText mPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_add_text, container, false);
        mTitle = myView.findViewById(R.id.editText_title_text);
        mPost = myView.findViewById(R.id.editText_post);

        //Enable onOptionsItemSelected Event
        setHasOptionsMenu(true);

        return myView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.post_article) {
            if(TextUtils.isEmpty(mTitle.getText())) {
                mTitle.setError("Please enter a title");
            } else if(TextUtils.isEmpty(mPost.getText())) {
                mPost.setError("Please enter some text");
            }
            //TODO: Create Text Article/Post
        };
        return super.onOptionsItemSelected(item);
    }
}
