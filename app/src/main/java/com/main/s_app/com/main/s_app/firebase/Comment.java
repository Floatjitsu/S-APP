package com.main.s_app.com.main.s_app.firebase;

public class Comment {

    private String comment;
    private User user;
    private long timeStamp;

    public Comment() {}

    Comment(String comment, User user, long timeStamp) {
        this.comment = comment;
        this.user = user;
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
