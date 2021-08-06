package com.wx.wxscheduled.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wx.wxscheduled.entity.ZhihuPassage;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class zhihuScheduling {
    @Autowired
    private SimpleDateFormat time;
    private static final String WXID = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=f0474735-939d-4c44-9a78-8234b7abebe6";
    private static final Logger logger = LoggerFactory.getLogger(zhihuScheduling.class);
    @Scheduled(fixedDelay = 60000*60*12) //每12小时执行一次
    public void scheduledTaskByFixedDelay() {
        logger.info("定时任务开始 ByFixedDelay：" + time.format(new Date()));
        zhihuTask();
        logger.info("定时任务结束 ByFixedDelay：" + time.format(new Date()));
    }
    private void zhihuTask() {
        try {
            String zhihuUrl = "https://news-at.zhihu.com/api/4/news/latest";
            StringBuffer stringBuffer = new StringBuffer();
            URL url = new URL(zhihuUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf8"));
            String line = null;
            while((line = bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            bufferedReader.close();
            List<ZhihuPassage> passageList = JsonTask(stringBuffer.toString());
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WXID);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            JSONArray jsonArray = new JSONArray();
            JSONObject jb = new JSONObject();
            for(ZhihuPassage zhihuPassage : passageList){
                JSONObject jb2 = new JSONObject();
                jb2.put("url", zhihuPassage.getUrl());
                jb2.put("picurl", zhihuPassage.getImages());
                jb2.put("title", zhihuPassage.getTitle());
                jsonArray.add(jb2);
            }
            jb.put("msgtype", "news");
            JSONObject Article = new JSONObject();
            Article.put("articles", jsonArray);
            jb.put("news",Article);
            StringEntity stringEntity = new StringEntity(jb.toString(), "utf-8");
            httppost.setEntity(stringEntity);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<ZhihuPassage> JsonTask(String text){
        List<ZhihuPassage> passageList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(text);
        JSONArray jsonArray = jsonObject.getJSONArray("stories");
        for(int i =0; i<jsonArray.size(); i++){
            ZhihuPassage passage = new ZhihuPassage();
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            passage.setImages(jsonObject1.getJSONArray("images").get(0).toString());
            passage.setUrl(jsonObject1.get("url").toString());
            passage.setTitle(jsonObject1.get("title").toString());
            passageList.add(passage);
        }
        return passageList;
    }
}
