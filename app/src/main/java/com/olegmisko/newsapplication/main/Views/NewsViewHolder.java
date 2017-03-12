package com.olegmisko.newsapplication.main.Views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Activities.WebViewActivity;
import com.olegmisko.newsapplication.main.Models.News;
import com.squareup.picasso.Picasso;

public class NewsViewHolder extends ChildViewHolder implements View.OnClickListener {

    private TextView title;
    private TextView shortDescription;
    private ImageView newsImage;
    private TextView publishedAt;
    private ImageView shareNews;
    private News news;


    public NewsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.single_news_title);
        shortDescription = (TextView) itemView.findViewById(R.id.single_news_short_desc);
        newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
        publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
        shareNews = (ImageView) itemView.findViewById(R.id.shareImage);
        newsImage.setOnClickListener(this);
        shareNews.setOnClickListener(this);
    }

    public void bindView(News news, Context context) {
        this.news =  news;
        title.setText(news.getTitle());
        shortDescription.setText(news.getShortDescription());
        Picasso.with(context)
                .load(news.getImageUrl())
                .resize(128, 128)
                .centerCrop()
                .into(newsImage);
        publishedAt.setText(convertStringToDate(news.getPublishedAt()));

    }

    /* This function checks if we have any URL attached
     * to the clicked news and loads a WebView if we have one. */
    private void loadURLRequest() {
        if (news.getUrl() != null) {
            Intent loadNews = new Intent(itemView.getContext(), WebViewActivity.class);
            loadNews.putExtra("URL", news.getUrl());
            loadNews.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(loadNews);
        }
    }

    private String convertStringToDate(String date) {
        if (date != null) {
            int endIndex = 0;
            for (int i = 0; i < date.length(); i++) {
                if (date.charAt(i) == 'T') {
                    endIndex = i;
                    break;
                }
            }
            return date.substring(0, endIndex);
        } else {
            return "";
        }

    }

    private void shareNews() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle() + "\n\n" + news.getShortDescription() + "\nSee more.. " + news.getUrl());
        sharingIntent.setType("text/plain");
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        itemView.getContext().startActivity(sharingIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newsImage:
                loadURLRequest();
                break;
            case R.id.shareImage:
                shareNews();
                break;
        }
    }
}
