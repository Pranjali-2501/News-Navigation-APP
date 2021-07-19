package com.example.android.newsnavigation;

public class News {
    String mTitle , mUrl , mUrlToImage;
    public News(String title , String url , String urlToImage)
    {
        mTitle = title;
        mUrl = url;
        mUrlToImage = urlToImage;
    }
    public String getmTitle()
    {
        return mTitle;
    }
    public String getmUrl()
    {
        return mUrl;
    }
    public String getmUrlToImage()
    {
        return mUrlToImage;
    }
}
