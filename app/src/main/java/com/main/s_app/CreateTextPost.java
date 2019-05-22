package com.main.s_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.main.s_app.com.main.s_app.dialogs.DiscardPostDialog;
import com.main.s_app.com.main.s_app.firebase.FirebaseForum;

public class CreateTextPost extends Fragment {

    EditText mTitle;
    EditText mPost;
    FirebaseForum mFirebaseForum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_create_text_post, container, false);

        mTitle = myView.findViewById(R.id.editText_title_text);
        mPost = myView.findViewById(R.id.editText_post);

        //Enable onOptionsItemSelected Event
        setHasOptionsMenu(true);

        mFirebaseForum = new FirebaseForum();

        return myView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.post_article) {
            if(TextUtils.isEmpty(mTitle.getText())) {
                mTitle.setError("Please enter a title");
            } else if(TextUtils.isEmpty(mPost.getText())) {
                mPost.setError("Please enter some text");
            } else {
                mFirebaseForum.addTextPostToFirebase(mTitle.getText().toString(), mPost.getText().toString());
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        } else {
            //Back Button
            if(getFragmentManager() != null) {
                new DiscardPostDialog().show(getFragmentManager(), "DiscardPostDialog");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
