package com.example.ray.myapplication.BannerView;

public class BannerInfo {
    private String url;
    private String title;

    public BannerInfo(String url,String title){
        this.url = url;
        this.title = title;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

}
