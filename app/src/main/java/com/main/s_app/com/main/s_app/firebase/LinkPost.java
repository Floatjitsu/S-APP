package com.main.s_app.com.main.s_app.firebase;

public class LinkPost extends Post {

    public static final String POST_KIND = "link";
    private String url;

    LinkPost() {}

    LinkPost(String postId, String postTitle, long timeStamp, String postKind, User user, String url) {
        super(postId, postTitle, timeStamp, postKind, user);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getPostKind() {
        return POST_KIND;
    }
}
