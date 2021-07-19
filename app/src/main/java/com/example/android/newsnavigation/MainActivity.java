package com.example.android.newsnavigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    public static final String NEWS_URL= "https://saurav.tech/NewsAPI/top-headlines";
    private String category="health";
    private static final int NEWS_LOADER_ID = 1;
    public static final String LOG_TAG =MainActivity.class.getName();
    private NewsAdapter mAdapter;
    private TextView emptyState;
    private LoaderManager loaderManager;
    private  SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*this code is for drawer layout*/
        loaderManager = getSupportLoaderManager();
        MaterialToolbar m1= findViewById(R.id.toolbar);
        DrawerLayout d1 = findViewById(R.id.drawer);
        NavigationView n1 = findViewById(R.id.navigation_view);

        SharedPreferences shrd = getSharedPreferences("navigate",MODE_PRIVATE);
       // SharedPreferences.Editor editor;
        m1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d1.openDrawer(GravityCompat.START);
            }
        });
        n1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                d1.closeDrawer(GravityCompat.START);

                switch (id)
                {
                    case R.id.health:
                        category="health";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"Health",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.enter:
                        category="entertainment";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"Entertainment",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.general:
                        category="general";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"General News",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.business:
                        category="business";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"Business",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.science:
                        category="science";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"Science",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.sport:
                        category="sports";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"Sports",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tech:
                        category="technology";
                        editor= shrd.edit();
                        editor.putString("Category",category);
                        editor.apply();
                        loaderManager.restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        Toast.makeText(MainActivity.this,"Technology",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        m1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.menu){
                    //Toast.makeText(this,"Menu called",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(MainActivity.this,SettingsActivity.class);
                    startActivity(in);
                    return true;
                }
                return false;
            }
        });
    /*this code is for setting data in list view*/
        ListView NewsItemView = findViewById(R.id.list_view);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        NewsItemView.setAdapter(mAdapter);

        emptyState = findViewById(R.id.empty_view);
        NewsItemView.setEmptyView(emptyState);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnectedOrConnecting();

        if(isConnected)
        {
            loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        }
        else
        {
            ProgressBar loadingIndicator =(ProgressBar) findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyState.setText("No Internet Connection");
        }
        NewsItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getmUrl());

                // Create a new intent to view the earthquake URI
               Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                websiteIntent.setPackage("com.android.chrome");
                // Send the intent to launch a new activity
               startActivity(websiteIntent);

//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                int colorInt = Color.parseColor("#FF0000"); //red
//                builder.setToolbarColor(colorInt);
//                builder.setPackage("com.android.chrome");
//                CustomTabsIntent customTabsIntent = builder.build();
//                customTabsIntent.launchUrl(MainActivity.this, newsUri);
            }
        });
        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        System.out.println( "onRefresh called from SwipeRefreshLayout");
                        //loaderManager = getSupportLoaderManager();
                       getSupportLoaderManager().restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        System.out.println("OnCreateLoader Method");
        SharedPreferences srd = PreferenceManager.getDefaultSharedPreferences(this);
        String country = srd.getString("country","in");

        SharedPreferences shrd = getSharedPreferences("navigate",MODE_PRIVATE);
        String cat = shrd.getString("Category","health");

        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendPath("category");
        builder.appendPath(cat);
        String path = country + ".json";
        builder.appendPath(path);
        String url = builder.toString();
        System.out.println(url);
        return new NewsLoader(this,url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        System.out.println("OnLoadFinished method");
        ProgressBar progressBar = findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);

        emptyState.setText("No News Found");
        mAdapter.clear();
        if(data!=null && !data.isEmpty())
        {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        System.out.println("OnLoaderReset method");
        mAdapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println(":onCreateOptionMenu called");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("onOptionItemSelected");
        int id = item.getItemId();
        if(id == R.id.menu){
            //Toast.makeText(this,"Menu called",Toast.LENGTH_SHORT).show();
            Intent in = new Intent(this,SettingsActivity.class);
            startActivity(in);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
