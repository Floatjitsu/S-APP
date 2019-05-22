package com.main.s_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.main.s_app.com.main.s_app.dialogs.DiscardPostDialog;
import com.main.s_app.com.main.s_app.firebase.FirebaseForum;

public class CreateLinkPost extends Fragment {

    EditText mLinkTitle, mLink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_create_link_post, container, false);

        mLinkTitle = myView.findViewById(R.id.editText_title_link);
        mLink = myView.findViewById(R.id.editText_link);

        //Enable onOptionsItemSelected Event
        setHasOptionsMenu(true);

        return myView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.post_article) {
            if(mLinkTitle.getText().toString().equals("")) {
                mLinkTitle.setError("Please enter a title");
            } else if(mLink.getText().toString().equals("")) {
                mLink.setError("Please enter a link");
            } else if(!URLUtil.isValidUrl(mLink.getText().toString())) {
                mLink.setError("Please provide a valid URL");
            } else {
                new FirebaseForum().addLinkPostToFirebase(mLinkTitle.getText().toString(), mLink.getText().toString());
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        } else {
            //Back button
            if(getFragmentManager() != null) {
                new DiscardPostDialog().show(getFragmentManager(), "DiscardPostDialog");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
