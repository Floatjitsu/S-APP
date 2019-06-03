package com.main.s_app;

class Constants {

    /*
    This class is for holding keys for a bundle  which gets created to start
    PostCommentsActivity and read the data inside there
     */

    //Post
    static final String KEY_POST_ID = "postId";
    static final String KEY_POST_TITLE = "postTitle";
    static final String KEY_POST_POSTED_BY = "postPostedBy";
    static final String KEY_POST_KIND = "postKind";
    static final String KEY_POST_DATE = "postDate";

    //Text Post
    static final String KEY_TEXT_POST_CONTENT = "textPostContent";

    //Image Post
    static final String KEY_IMAGE_POST_DESC = "imagePostDescription";
    static final String KEY_IMAGE_POST_IMAGE_PATH = "imagePostImagePath";

    //Link Post
    static final String KEY_LINK_POST_IMAGE_PATH = "linkPostImagePath";
    static final String KEY_LINK_POST_DESC = "linkPostDescription";

    /*
    This class is for holding keys for the rss feed subscription preferences
    as well as urls for every key
     */

    //Shared Preferences
    static final String PREFERENCE_KEY = "user_pref_key";
    static final String SOLE_COLLECTOR = "Sole Collector";
    static final String NICE_KICKS = "Nice Kicks";
    static final String FINISH_LINE = "Finish Line";
    static final String SNEAKER_FREAKER = "Sneaker Freaker";
    static final String SNEAKER_FILES = "Sneaker Files";
    static final String MODERN_NOTORIETY = "Modern Notoriety";

    private static final String URL_SOLE_COLLECTOR = "https://solecollector.com/feeds/generator/e/r/1.rss";
    private static final String URL_NICE_KICKS = "https://www.nicekicks.com/feed/";
    private static final String URL_FINISH_LINE = "https://blog.finishline.com/feed/";
    private static final String URL_SNEAKER_FREAKER = "https://www.sneakerfreaker.com/feed";
    private static final String URL_SNEAKER_FILES = "https://www.sneakerfiles.com/feed/";
    private static final String URL_MODERN_NOTORIETY = "https://www.modern-notoriety.com/feed/";

    static String [] getSharedPreferencesKeys() {
        return new String [] {
                SOLE_COLLECTOR, NICE_KICKS,
                FINISH_LINE, SNEAKER_FREAKER,
                SNEAKER_FILES, MODERN_NOTORIETY
        };
    }

    static String getUrlByPreferenceKey(String prefKey) {
        switch (prefKey) {
            case SOLE_COLLECTOR:
                return URL_SOLE_COLLECTOR;
            case NICE_KICKS:
                return URL_NICE_KICKS;
            case FINISH_LINE:
                return URL_FINISH_LINE;
            case SNEAKER_FREAKER:
                return URL_SNEAKER_FREAKER;
            case SNEAKER_FILES:
                return URL_SNEAKER_FILES;
            case MODERN_NOTORIETY:
                return URL_MODERN_NOTORIETY;
            default:
                return "";
        }
    }

}
