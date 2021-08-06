package com.wx.wxscheduled.entity;

import java.util.List;

public class Weather {
    private String country;
    private String province;
    private String city;
    private String name;
    private String rh;//湿度
    private String text ;//天气
    private String windclass;//风级
    private String winddir;//风向
    private String feellike;//体表温度
    private String uptime;//数据更新时间
    private Forecast forecasts;//近日天气
    @Override
    public String toString() {
        return country+province+city+name+"的今天的天气:"+" 湿度为"+rh+", 天气"+text+", 风力"+windclass
                +", 风向"+winddir+", 体表温度"+feellike+"度"+", 数据更新时间"+uptime+"\n"+forecasts;
    }
    public Forecast getForecasts() {
        return forecasts;
    }

    public void setForecasts(Forecast forecasts) {
        this.forecasts = forecasts;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWindclass() {
        return windclass;
    }

    public void setWindclass(String windclass) {
        this.windclass = windclass;
    }

    public String getWinddir() {
        return winddir;
    }

    public void setWinddir(String winddir) {
        this.winddir = winddir;
    }

    public String getFeellike() {
        return feellike;
    }

    public void setFeellike(String feellike) {
        this.feellike = feellike;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

}
