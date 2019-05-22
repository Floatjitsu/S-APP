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
import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.main.s_app.com.main.s_app.firebase.ImagePost;
import com.main.s_app.com.main.s_app.firebase.LinkPost;
import com.main.s_app.com.main.s_app.firebase.Post;
import com.main.s_app.com.main.s_app.firebase.TextPost;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ForumAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private static final int VIEW_TYPE_TEXT_POST = 1;
    private static final int VIEW_TYPE_IMAGE_POST = 2;
    private static final int VIEW_TYPE_LINK_POST = 3;

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
        } else if(mPosts.get(position) instanceof LinkPost) {
            return VIEW_TYPE_LINK_POST;
        } else {
            return VIEW_TYPE_IMAGE_POST;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(i == VIEW_TYPE_TEXT_POST) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_post_item, viewGroup, false);
            view.setOnClickListener(this);
            return new TextPostHolder(view);
        } else if(i == VIEW_TYPE_LINK_POST) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.link_post_item, viewGroup, false);
            return new LinkPostHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_post_item, viewGroup, false);
            return new ImagePostHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Post post = mPosts.get(i);
        if(post instanceof TextPost) {
            ((TextPostHolder) viewHolder).bind((TextPost) post);
        } else if(post instanceof ImagePost) {
            ((ImagePostHolder) viewHolder).bind((ImagePost) post);
        } else {
            ((LinkPostHolder) viewHolder).bind((LinkPost) post);
        }
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    private String getPrettyDate(long timeStamp) {
        Date date = new Date(timeStamp);
        return DateUtils.getRelativeTimeSpanString(date.getTime(), Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
    }

    @Override
    public void onClick(View v) {
        TextView postId = v.findViewById(R.id.post_id);
        //TODO: Create switch for post id
        //TODO: Create a bundle with the post id
        //TODO: Start post comment activity
    }

    class TextPostHolder extends RecyclerView.ViewHolder {

        TextView textPostTitle, textPostContent, textPostPostedBy, textPostDate, textPostId;

        TextPostHolder(@NonNull View itemView) {
            super(itemView);
            textPostTitle = itemView.findViewById(R.id.text_post_title);
            textPostContent = itemView.findViewById(R.id.text_post_content);
            textPostPostedBy = itemView.findViewById(R.id.text_post_posted_by);
            textPostDate = itemView.findViewById(R.id.text_post_date);
            textPostId = itemView.findViewById(R.id.post_id);
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
            imagePostId = itemView.findViewById(R.id.post_id);
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

    class LinkPostHolder extends RecyclerView.ViewHolder {

        TextView linkPostTitle, linkPostDescription, linkPostPostedBy, linkPostDate, linkPostId, linkPostUrl;
        ImageView linkPostImage;

        LinkPostHolder(@NonNull View itemView) {
            super(itemView);
            linkPostTitle = itemView.findViewById(R.id.link_post_title);
            linkPostDescription = itemView.findViewById(R.id.link_post_description);
            linkPostId = itemView.findViewById(R.id.post_id);
            linkPostDate = itemView.findViewById(R.id.link_post_date);
            linkPostPostedBy = itemView.findViewById(R.id.link_post_posted_by);
            linkPostImage = itemView.findViewById(R.id.link_post_image);
            linkPostUrl = itemView.findViewById(R.id.link_post_url);
        }

        void bind(LinkPost linkPost) {
            linkPostTitle.setText(linkPost.getPostTitle());
            linkPostPostedBy.append(" " + linkPost.getUser().getUsername());
            linkPostDate.setText(getPrettyDate(linkPost.getTimeStamp()));
            linkPostId.setText(linkPost.getPostId());
            linkPostUrl.setText(linkPost.getUrl());
            setUrlImageDesc(linkPost.getUrl(), linkPostImage, linkPostDescription);
        }

        void setUrlImageDesc(String url, final ImageView urlImage, final TextView urlDescription) {
            TextCrawler textCrawler = new TextCrawler();
            LinkPreviewCallback callback = new LinkPreviewCallback() {
                @Override
                public void onPre() {

                }

                @Override
                public void onPos(SourceContent sourceContent, boolean b) {
                    urlDescription.setText(sourceContent.getDescription());
                    GlideApp.with(mActivityContext).load(sourceContent.getImages().get(0)).into(urlImage);
                }
            };
            textCrawler.makePreview(callback, url);
        }
    }
}
