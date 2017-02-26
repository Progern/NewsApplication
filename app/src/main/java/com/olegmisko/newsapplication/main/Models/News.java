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
    @Expose
    @SerializedName("urlToImage")
    private String imageUrl;
    @Expose
    @SerializedName("publishedAt")
    private String publishedAt;

    public News() {

    }

    public News(String title, String description, String url, String imageUrl, String publishedAt) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }


}
