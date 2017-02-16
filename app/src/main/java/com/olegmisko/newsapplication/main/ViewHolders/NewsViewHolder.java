package com.olegmisko.newsapplication.main.ViewHolders;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Models.News;

public class NewsViewHolder extends ChildViewHolder implements View.OnClickListener {

    private TextView title;
    private TextView shortDescription;
    private String url;

    public NewsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.single_news_title);
        shortDescription = (TextView) itemView.findViewById(R.id.single_news_short_desc);
        title.setOnClickListener(this);
        shortDescription.setOnClickListener(this);
    }

    public void bindView(News news) {
        title.setText(news.getTitle());
        shortDescription.setText(news.getShortDescription());
        url = news.getUrl();
    }

    /* This function checks if we have any URL attached
     * to the clicked news and loads a WebView if we have one. */
    public void loadURLRequest() {
        if (this.url != null) {
            Intent loadNews = new Intent(Intent.ACTION_VIEW);
            loadNews.setData(Uri.parse(url));
            loadNews.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(loadNews);
        }
    }

    @Override
    public void onClick(View v) {
        loadURLRequest();
    }
}
