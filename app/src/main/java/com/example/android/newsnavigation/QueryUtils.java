package com.example.android.newsnavigation;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.newsnavigation.MainActivity.LOG_TAG;

public class QueryUtils {
    private QueryUtils()
    {}
    public static List<News> extractNews(String url)
    {
        System.out.println("extractnews");
        URL mUrl = createUrl(url);
        String sampleJsonResponse = null;
        try
        {
            sampleJsonResponse = makeHttpURLConnection(mUrl);
        }catch(IOException e)
        {
            Log.e(LOG_TAG,"Exception caught " +e.getMessage());
        }
        List<News> newsList = extractFeatureFromJson(sampleJsonResponse);
        return newsList;
    }
    public static URL createUrl(String stringUrl)
    {
        URL hp = null;
        try
        {
            hp = new URL(stringUrl);
        }catch(MalformedURLException e)
        {
            System.out.println("EXception Caught : "+e.getMessage());
        }
        return hp;
    }
    public static String makeHttpURLConnection(URL url) throws IOException
    {
        String jsonResponse = null;
        if(url == null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromResponse(inputStream);
            }
            else
            {
                Log.e(LOG_TAG,"Error Response Code" + urlConnection.getResponseCode());
            }
        }catch(IOException e)
        {
            System.out.println("Exception caught : " + e.getMessage());
        }finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(inputStream != null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    public static String readFromResponse(InputStream inputStream) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String line = buffer.readLine();
            while (line != null)
            {
                builder.append(line);
                line = buffer.readLine();
            }
        }
        return builder.toString();
    }
    public static List<News> extractFeatureFromJson(String sampleJsonResponse)
    {
        if(TextUtils.isEmpty(sampleJsonResponse))
        {
            return null;
        }

        List<News> newsList = new ArrayList<News>();
        try
        {
            JSONObject jsonObject = new JSONObject(sampleJsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");
                String url = obj.getString("url");
                String urlImage = obj.getString("urlToImage");

                News n = new News(title,url,urlImage);
                newsList.add(n);
            }

        }catch (JSONException e)
        {
            System.out.println("Exception caught :" + e.getMessage());
        }
        return newsList;
    }

}
