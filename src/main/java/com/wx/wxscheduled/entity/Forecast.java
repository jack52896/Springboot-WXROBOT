package com.wx.wxscheduled.entity;

public class Forecast {
    private String wdnight;//晚上风向
    private String date;
    private String high;//温度
    private String textnight;//晚上的天气
    private String wdday;//白天风向
    private String low;//最低温度
    private String wcnight;//晚上风力
    private String textday;//白天的天气
    private String wcday;//白天的风力
    private String week;//时间
    @Override
    public String toString() {
        return "明天的预计天气 "+textday+", 最高温度"+high+"度"+", 最低温度"+low+"度"+" 白天的风力 "+wcday+
                ", 风向"+wdday+ ", 晚上的风力"+wcnight+ ", 晚上的风向"+wdnight+ ", 晚上天气"+textnight +
                week+", 数据接口由百度地图提供";
    }
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWdnight() {
        return wdnight;
    }

    public void setWdnight(String wdnight) {
        this.wdnight = wdnight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getTextnight() {
        return textnight;
    }

    public void setTextnight(String textnight) {
        this.textnight = textnight;
    }

    public String getWdday() {
        return wdday;
    }

    public void setWdday(String wdday) {
        this.wdday = wdday;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getWcnight() {
        return wcnight;
    }

    public void setWcnight(String wcnight) {
        this.wcnight = wcnight;
    }

    public String getTextday() {
        return textday;
    }

    public void setTextday(String textday) {
        this.textday = textday;
    }

    public String getWcday() {
        return wcday;
    }

    public void setWcday(String wcday) {
        this.wcday = wcday;
    }

}
