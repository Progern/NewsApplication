package com.olegmisko.newsapplication.main.Views;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Models.NewsGroup;

public class NewsGroupViewHolder extends ParentViewHolder {

    private TextView title;
    private ImageView icon;


    public NewsGroupViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.news_group_title);
        icon = (ImageView) itemView.findViewById(R.id.news_group_icon);
    }

    public void bindView(NewsGroup newsGroup) {
        title.setText(newsGroup.getTitle());
        icon.setImageResource(newsGroup.getIcon());
    }
}
