package com.wx.wxscheduled.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class TimeConfig {
    @Bean
    public SimpleDateFormat time(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
