package com.main.s_app.com.main.s_app.firebase;

public class ImagePost extends Post {

    public static final String POST_KIND = "image";
    private String imageUri;
    private String imageDescription;

    public ImagePost() {}

    ImagePost(String id, String title, long timeStamp, String postKind, User user, String imageUri, String imageDescription) {
        super(id, title, timeStamp, postKind, user);
        this.imageUri = imageUri;
        this.imageDescription = imageDescription;
    }

    public String getPostKind() {
        return POST_KIND;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getImageDescription() {
        return imageDescription;
    }
}
