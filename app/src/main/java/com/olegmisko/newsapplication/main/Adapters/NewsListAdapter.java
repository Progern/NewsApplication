package com.olegmisko.newsapplication.main.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.olegmisko.newsapplication.R;
import com.olegmisko.newsapplication.main.Models.News;
import com.olegmisko.newsapplication.main.Models.NewsGroup;
import com.olegmisko.newsapplication.main.ViewHolders.NewsGroupViewHolder;
import com.olegmisko.newsapplication.main.ViewHolders.NewsViewHolder;

import java.util.List;

public class NewsListAdapter extends ExpandableRecyclerAdapter<NewsGroupViewHolder, NewsViewHolder> {

    private LayoutInflater inflater;
    private List<? extends ParentListItem> parentItemList;
    private Context context;


    public NewsListAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.parentItemList = parentItemList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public NewsGroupViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View newsGroupView = inflater.inflate(R.layout.news_group_layout, parentViewGroup, false);
        return new NewsGroupViewHolder(newsGroupView);
    }

    @Override
    public NewsViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View singleNewsView = inflater.inflate(R.layout.single_news_layout, childViewGroup, false);
        return new NewsViewHolder(singleNewsView);
    }

    @Override
    public void onBindParentViewHolder(NewsGroupViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        NewsGroup newsGroup = (NewsGroup) parentListItem;
        parentViewHolder.bindView(newsGroup);
    }

    @Override
    public void onBindChildViewHolder(NewsViewHolder childViewHolder, int position, Object childListItem) {
        News news = (News) childListItem;
        childViewHolder.bindView(news, context);
    }


}
