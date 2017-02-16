package com.olegmisko.newsapplication.main.Models;

/*This class represents a single-news
* with title and short description*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class News extends RealmObject {
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("url")
    private String url;

    public News() {

    }

    public News(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return description;
    }

    public void setShortDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
