package com.olegmisko.newsapplication.main.Models;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/* This class represents a news headline
 * like BBC, The Guardian, etc. */
public class NewsGroup implements ParentListItem {

    private String title;
    private Integer icon;

    private List mNews;

    public NewsGroup(String title, Integer icon, List mNews) {
        this.title = title;
        this.icon = icon;

        this.mNews = mNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    @Override
    public List<?> getChildItemList() {
        return mNews;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
