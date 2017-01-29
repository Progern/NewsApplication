package com.olegmisko.newsapplication.main.Models;

/*This class represents a single-news
* with title and short description*/

public class News {

    private String title;
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
