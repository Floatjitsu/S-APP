package com.main.s_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.s_app.com.main.s_app.firebase.ImagePost;
import com.main.s_app.com.main.s_app.firebase.Post;
import com.main.s_app.com.main.s_app.firebase.TextPost;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ForumAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_TEXT_POST = 1;
    private static final int VIEW_TYPE_IMAGE_POST = 2;
    //TODO: Add VIEW_TYPE constants for link post

    private Context mActivityContext;
    private ArrayList<Post> mPosts;

    public ForumAdapter(ArrayList<Post> mPosts, Context mActivityContext) {
        //Sort Array List by Timestamp
        Collections.sort(mPosts, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return (int) (o2.getTimeStamp() - o1.getTimeStamp());
            }
        });
        this.mPosts = mPosts;
        this.mActivityContext = mActivityContext;
    }

    @Override
    public int getItemViewType(int position) {
        if(mPosts.get(position) instanceof TextPost) {
            return VIEW_TYPE_TEXT_POST;
        } else {
            return VIEW_TYPE_IMAGE_POST;
        }
        //TODO: Return different view types for different instanceof results
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(i == VIEW_TYPE_TEXT_POST) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_post_item, viewGroup, false);
            return new TextPostHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_post_item, viewGroup, false);
            return new ImagePostHolder(view);
        }
        //TODO: Create other view types and return them
        /*view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_post_item, viewGroup, false);
        return new TextPostHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Post post = mPosts.get(i);
        if(post instanceof TextPost) {
            ((TextPostHolder) viewHolder).bind((TextPost) post);
        } else if(post instanceof ImagePost) {
            ((ImagePostHolder) viewHolder).bind((ImagePost) post);
        }
        //TODO: Create bind methods for other view types (image, link)
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    private String getPrettyDate(long timeStamp) {
        Date date = new Date(timeStamp);
        return DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
    }

    class TextPostHolder extends RecyclerView.ViewHolder {

        TextView textPostTitle, textPostContent, textPostPostedBy, textPostDate, textPostId;

        TextPostHolder(@NonNull View itemView) {
            super(itemView);
            textPostTitle = itemView.findViewById(R.id.text_post_title);
            textPostContent = itemView.findViewById(R.id.text_post_content);
            textPostPostedBy = itemView.findViewById(R.id.text_post_posted_by);
            textPostDate = itemView.findViewById(R.id.text_post_date);
            textPostId = itemView.findViewById(R.id.text_post_id);
        }

        void bind(TextPost textPost) {
            textPostTitle.setText(textPost.getPostTitle());
            textPostContent.setText(textPost.getTextContent());
            textPostPostedBy.append(" " + textPost.getUser().getUsername());
            textPostDate.setText(getPrettyDate(textPost.getTimeStamp()));
            textPostId.setText(textPost.getPostId());
        }
    }

    class ImagePostHolder extends RecyclerView.ViewHolder {

        TextView imagePostTitle, imagePostDesc, imagePostPostedBy, imagePostDate, imagePostId;
        ImageView imagePostImage;

        ImagePostHolder(@NonNull View itemView) {
            super(itemView);
            imagePostTitle = itemView.findViewById(R.id.image_post_title);
            imagePostDesc = itemView.findViewById(R.id.image_post_description);
            imagePostPostedBy = itemView.findViewById(R.id.image_post_posted_by);
            imagePostDate = itemView.findViewById(R.id.image_post_date);
            imagePostId = itemView.findViewById(R.id.image_post_id);
            imagePostImage = itemView.findViewById(R.id.image_post_image);
        }

        void bind(ImagePost imagePost) {
            imagePostTitle.setText(imagePost.getPostTitle());
            imagePostDesc.setText(imagePost.getImageDescription());
            imagePostPostedBy.append(" " + imagePost.getUser().getUsername());
            imagePostDate.setText(getPrettyDate(imagePost.getTimeStamp()));
            imagePostId.setText(imagePost.getPostId());
            StorageReference reference = FirebaseStorage.getInstance().getReference().child("/images/" + imagePost.getImageUri());
            GlideApp.with(mActivityContext).load(reference).into(imagePostImage);
        }
    }

    //TODO: Add LinkPostHolder
}
