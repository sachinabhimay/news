package com.app.news.utils;

import java.io.Serializable;


public class News implements Serializable {

    private String title;
    private String description;
    private String date;
    private String imageUrl;
    private String webUrl;
    private String sectionName;

    public News(String title, String description, String date, String imageUrl, String webUrl, String sectionName) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
        this.sectionName = sectionName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}