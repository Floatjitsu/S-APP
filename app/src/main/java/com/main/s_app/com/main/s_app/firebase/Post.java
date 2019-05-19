package com.main.s_app.com.main.s_app.firebase;

public class Post {

    private String postId;
    private String postTitle;
    private long timeStamp;
    private String postKind;
    private User user;

    public Post() {}

    public Post(String postId, String postTitle, long timeStamp, String postKind, User user) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.timeStamp = timeStamp;
        this.postKind = postKind;
        this.user = user;
    }

    public Post(String postId, String postTitle, long timeStamp, User user) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.timeStamp = timeStamp;
        this.user = user;
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
