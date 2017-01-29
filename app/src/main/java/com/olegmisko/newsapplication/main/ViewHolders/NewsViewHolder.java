package com.olegmisko.newsapplication.main.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Models.News;

public class NewsViewHolder extends ChildViewHolder {

    private TextView title;
    private TextView shortDescription;

    public NewsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.single_news_title);
        shortDescription = (TextView) itemView.findViewById(R.id.single_news_short_desc);
    }

    public void bindView(News news) {
        title.setText(news.getTitle());
        shortDescription.setText(news.getShortDescription());
    }
}
