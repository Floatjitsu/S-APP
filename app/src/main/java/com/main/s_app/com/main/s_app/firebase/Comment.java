package com.main.s_app.com.main.s_app.firebase;

public class Comment {

    private String comment;
    private User user;

    public Comment() {}

    Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
