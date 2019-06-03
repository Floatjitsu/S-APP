package com.main.s_app;

import android.graphics.Bitmap;

public class NewsModel {

    private String newsTitle, newsUrl, newsDate, newsDesc, newsProvider;
    private Bitmap newsImage;

    public NewsModel() {}

    NewsModel(String newsProvider) {
        this.newsProvider = newsProvider;
    }

    NewsModel(String newsProvider, String newsTitle, String newsUrl, String newsDate, String newsDesc) {
        this.newsProvider = newsProvider;
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.newsDate = newsDate;
        this.newsDesc = newsDesc;
    }

    public Bitmap getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(Bitmap newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsProvider() {
        return newsProvider;
    }

    String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}
