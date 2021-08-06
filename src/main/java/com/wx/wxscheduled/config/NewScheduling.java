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
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NewScheduling {
    @Autowired
    private SimpleDateFormat time;
    private static final String WXID = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=f0474735-939d-4c44-9a78-8234b7abebe6";
    private static final Logger logger = LoggerFactory.getLogger(ZhihuScheduling.class);
    @Scheduled(fixedDelay = 60000*60*12) //每12小时执行一次
    public void scheduledTaskByFixedDelay() {
        logger.info("定时任务开始 ByFixedDelay：" + time.format(new Date()));
        newsTask();
        logger.info("定时任务结束 ByFixedDelay：" + time.format(new Date()));
    }
    @Test
    public  void newsTask(){
        try {
            String newsUrl = "http://c.3g.163.com/nc/article/list/T1467284926140/0-20.html";
            StringBuffer stringBuffer = new StringBuffer();
            URL url = new URL(newsUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf8"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            List<ZhihuPassage> passageList = news(stringBuffer.toString());
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WXID);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            JSONArray array = new JSONArray();
            for(ZhihuPassage passage : passageList){
                JSONObject jb2 = new JSONObject();
                jb2.put("url", passage.getUrl());
                jb2.put("picurl", passage.getImages());
                jb2.put("title", passage.getTitle());
                array.add(jb2);
            }
            JSONObject jsonObject = new JSONObject();
            JSONObject Article = new JSONObject();
            jsonObject.put("msgtype", "news");
            Article.put("articles", array);
            jsonObject.put("news",Article);
            StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");
            httppost.setEntity(stringEntity);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public List<ZhihuPassage> news(String text){
        List<ZhihuPassage> passageList = new ArrayList<>();
        JSONObject newsObject = JSONObject.parseObject(text);
        JSONArray newsObjectList = newsObject.getJSONArray("T1467284926140");
        for(int i=0; i<8; i++){
            ZhihuPassage zhihuPassage = new ZhihuPassage();
            JSONObject ob = newsObjectList.getJSONObject(i);
            zhihuPassage.setImages(ob.get("imgsrc").toString());
            zhihuPassage.setTitle(ob.get("title").toString());
            zhihuPassage.setUrl(ob.get("url").toString());
            passageList.add(zhihuPassage);
        }
        return passageList;
    }
}
