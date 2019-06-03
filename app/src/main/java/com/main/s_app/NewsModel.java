package com.main.s_app;

public class NewsModel {

    private String newsTitle, newsUrl, newsDate, newsDesc, newsProvider;

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

    String getNewsProvider() {
        return newsProvider;
    }

    String getNewsDesc() {
        return newsDesc;
    }

    String getNewsTitle() {
        return newsTitle;
    }

    String getNewsUrl() {
        return newsUrl;
    }

    String getNewsDate() {
        return newsDate;
    }
}
