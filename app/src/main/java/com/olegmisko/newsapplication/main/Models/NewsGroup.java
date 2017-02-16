package com.olegmisko.newsapplication.main.Models;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/* This class represents a news headline
 * like BBC, The Guardian, etc. */
public class NewsGroup extends RealmObject implements ParentListItem {

    private String title;
    private Integer icon;
    @Ignore
    private List<News> mNews;

    public NewsGroup() {
    }

    public NewsGroup(String title, Integer icon, List<News> mNews) {
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
