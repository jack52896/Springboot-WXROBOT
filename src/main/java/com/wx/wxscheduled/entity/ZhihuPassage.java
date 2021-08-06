package com.wx.wxscheduled.entity;

public class ZhihuPassage {
    private String title;
    private String url;
    private String images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ZhihuPassage{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
