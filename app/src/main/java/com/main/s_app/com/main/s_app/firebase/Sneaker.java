package com.main.s_app.com.main.s_app.firebase;

public class Sneaker {

    private String brandName;
    private String modelName;
    private String releaseDate;

    public Sneaker() {};

    public Sneaker(String brandName, String modelName, String releaseDate) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModelName() {
        return modelName;
    }
}
