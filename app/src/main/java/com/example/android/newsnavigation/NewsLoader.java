package com.example.android.newsnavigation;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;

    public NewsLoader(Context context , String url)
    {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
        //super.onStartLoading();
    }


    @Override
    public List<News> loadInBackground() {
        if(mUrl == null) {
            return null;
        }
        List<News> newsList = QueryUtils.extractNews(mUrl);
        return newsList;
    }
}
