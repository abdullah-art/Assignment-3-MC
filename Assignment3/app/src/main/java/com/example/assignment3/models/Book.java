package com.example.assignment3.models;

public class Book {
    private String title;
    private String author;
    private String authorUrl;
    private String level;
    private String info;
    private String url;
    private String cover;

    public Book(String title, String author, String authorUrl, String level, String info, String url, String cover) {
        this.title = title;
        this.author = author;
        this.authorUrl = authorUrl;
        this.level = level;
        this.info = info;
        this.url = url;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getLevel() {
        return level;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }

    public String getCover() {
        return cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
