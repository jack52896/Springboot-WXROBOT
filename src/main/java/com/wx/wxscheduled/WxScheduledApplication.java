package com.wx.wxscheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WxScheduledApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxScheduledApplication.class, args);
    }

}
