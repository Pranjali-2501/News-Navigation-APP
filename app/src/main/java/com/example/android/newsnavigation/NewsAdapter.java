package com.example.android.newsnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItem = convertView;
        if(ListItem == null)
        {
            ListItem = LayoutInflater.from(getContext()).inflate(R.layout.news_item,parent,false);
        }
        News currentNews = getItem(position);
        // set image in list item
        ImageView imageView = ListItem.findViewById(R.id.Image_view);


        Glide.with(getContext()).load(currentNews.getmUrlToImage()).into(imageView);
        // set title in list item
        TextView textView = ListItem.findViewById(R.id.text_view);
        textView.setText(currentNews.getmTitle());

        return ListItem;
    }
}
