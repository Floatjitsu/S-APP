package com.main.s_app.com.main.s_app.firebase;

import java.util.ArrayList;

public class Post {

    private String postId;
    private String postTitle;
    private long timeStamp;
    private String postKind;
    private User user;
    private int commentsCount;

    public Post() {}

    public Post(String postId, String postTitle, long timeStamp, String postKind, User user) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.timeStamp = timeStamp;
        this.postKind = postKind;
        this.user = user;
        this.commentsCount = 0;
    }

    public Post(String postId, String postTitle, long timeStamp, User user) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.timeStamp = timeStamp;
        this.user = user;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getPostKind() {
        return postKind;
    }

    public User getUser() {
        return user;
    }

    public String getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
