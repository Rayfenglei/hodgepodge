package com.example.ray.myapplication.Function.News;

public class AgNewsBean {
    private String title;
    private String author;
    private String time;
    private String ImageUrl;
    private String contentUrl;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AgNewsBean{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                '}';
    }
}