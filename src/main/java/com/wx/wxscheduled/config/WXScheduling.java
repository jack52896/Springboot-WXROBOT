package com.wx.wxscheduled.config;

import ch.qos.logback.classic.net.SyslogAppender;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.wxscheduled.entity.Forecast;
import com.wx.wxscheduled.entity.Weather;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
@Component
public class WXScheduling {
    @Autowired
    private SimpleDateFormat time;
    private static final String WXID = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=f0474735-939d-4c44-9a78-8234b7abebe6";
    private static final Logger logger = LoggerFactory.getLogger(WXScheduling.class);
    @Scheduled(fixedDelay = 10000) //每10秒执行一次
    public void scheduledTaskByFixedDelay() {
        logger.info("定时任务开始 ByFixedDelay：" + time.format(new Date()));
        scheduledTask();
        logger.info("定时任务结束 ByFixedDelay：" + time.format(new Date()));
    }
    /*
    定时拿取接口数据
     */
    @Test
    public void scheduledTask() {
        try {
            String baiduUrl = "https://api.map.baidu.com/weather/v1/?district_id=360111&data_type=all&ak=QLVCURfTor6cr3IekRiK7ebaqLjnqvYN";
            StringBuffer stringBuffer = new StringBuffer();
            URL url = new URL(baiduUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
            String line = null;
            while((line = bufferedReader.readLine() )!= null){
                stringBuffer.append(line);
            }
            bufferedReader.close();
            String seccussData = stringBuffer.toString();
            String msg = map(seccussData);
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WXID);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            JSONObject contentObject = new JSONObject();
            contentObject.put("content", msg);
            jsonObject.put("msgtype", "text");
            jsonObject.put("text", contentObject);
            StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            httppost.setEntity(stringEntity);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (MalformedURLException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String  map (String text){
        JSONObject jsonObject = JSONObject.parseObject(text);
        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
        JSONObject jsonObject2 = jsonObject1.getJSONObject("location");//地区
        JSONObject jsonObject3 = jsonObject1.getJSONObject("now");//今天天气
        JSONArray jsonArray = jsonObject1.getJSONArray("forecasts");
        JSONObject jsonObject4 = jsonArray.getJSONObject(0);//明天的天气

        Forecast forecast = new Forecast();

        forecast.setDate(jsonObject4.get("date").toString());
        forecast.setHigh(jsonObject4.get("high").toString());
        forecast.setLow(jsonObject4.get("low").toString());
        forecast.setTextday(jsonObject4.get("text_day").toString());
        forecast.setTextnight(jsonObject4.get("text_night").toString());
        forecast.setWcday(jsonObject4.get("wc_day").toString());
        forecast.setWeek(jsonObject4.get("week").toString());
        forecast.setWdday(jsonObject4.get("wd_day").toString());
        forecast.setWcnight(jsonObject4.get("wc_night").toString());
        forecast.setWdnight(jsonObject4.get("wd_night").toString());

        Weather weather = new Weather();

        weather.setCountry(jsonObject2.get("country").toString());
        weather.setProvince(jsonObject2.get("province").toString());
        weather.setCity(jsonObject2.get("city").toString());
        weather.setName(jsonObject2.get("name").toString());
        weather.setText(jsonObject3.get("text").toString());
        weather.setFeellike(jsonObject3.get("feels_like").toString());
        weather.setRh(jsonObject3.get("rh").toString());
        weather.setUptime(jsonObject3.get("uptime").toString());
        weather.setWindclass(jsonObject3.get("wind_class").toString());
        weather.setWinddir(jsonObject3.get("wind_dir").toString());
        weather.setForecasts(forecast);

        return weather.toString();
    }
}
