package com.olegmisko.newsapplication.main.Models;

/*This class represents a single-news
* with title and short description*/

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("description")
    private String shortDescription;

    public News(String title, String shortDescription) {
        this.title = title;
        this.shortDescription = shortDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
