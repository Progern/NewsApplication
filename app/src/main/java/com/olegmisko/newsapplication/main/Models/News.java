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
    private String description;

    public News(String title, String description) {
        this.title = title;
        this.description = description;
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
}
