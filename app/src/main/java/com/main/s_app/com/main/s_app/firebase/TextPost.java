package com.main.s_app.com.main.s_app.firebase;

public class TextPost extends Post {

    public static final String POST_KIND = "text";
    private String textContent;

    public TextPost() {}

    TextPost(String id, String title, long timeStamp, String postKind, User user, String textContent) {
        super(id, title, timeStamp, postKind, user);
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getPostKind() {
        return POST_KIND;
    }


}
