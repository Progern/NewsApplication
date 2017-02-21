package com.olegmisko.newsapplication.main.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Models.News;
import com.squareup.picasso.Picasso;

public class NewsViewHolder extends ChildViewHolder implements View.OnClickListener {

    private TextView title;
    private TextView shortDescription;
    private ImageView newsImage;
    private TextView publishedAt;
    private String url;


    public NewsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.single_news_title);
        shortDescription = (TextView) itemView.findViewById(R.id.single_news_short_desc);
        newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
        publishedAt = (TextView) itemView.findViewById(R.id.publishedAt);
        newsImage.setOnClickListener(this);
    }

    public void bindView(News news, Context context) {
        title.setText(news.getTitle());
        shortDescription.setText(news.getShortDescription());
        url = news.getUrl();
        Picasso.with(context)
                .load(news.getImageUrl())
                .resize(128, 128)
                .centerCrop()
                .into(newsImage);
        publishedAt.setText(convertStringToDate(news.getPublishedAt()));

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

    public String convertStringToDate(String date) {
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

    @Override
    public void onClick(View v) {
        loadURLRequest();
    }
}
