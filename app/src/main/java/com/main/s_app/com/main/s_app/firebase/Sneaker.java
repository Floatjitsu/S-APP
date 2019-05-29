package com.main.s_app.com.main.s_app.firebase;

public class Sneaker {

    private String brandName;
    private String modelName;
    private String releaseDate;
    private String imageUri;

    public Sneaker() {};

    public Sneaker(String brandName, String modelName, String releaseDate, String imageUri) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.releaseDate = releaseDate;
        this.imageUri = imageUri;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModelName() {
        return modelName;
    }
}
