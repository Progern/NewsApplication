package com.olegmisko.newsapplication.main.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsList {

    /*This class is used to hold the
     * news list, that comes from HTTP-response */

    @Expose
    @SerializedName("articles")
    private List<News> newsList;

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
