package com.liuyl.service_02.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuyl.service_02.entity.WxInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyl01
 * 微信Api调用类
 */
@Service
@EnableScheduling
public class WeChatApiService {

    @Value("${wechat.AppId}")
    private String AppId;
    @Value("${wechat.AppSecret}")
    private String AppSecret;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    //获取小程序全局唯一后台接口调用凭据,每两小时执行一次
//    @Scheduled(cron = "0 */2 * * * ?")
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void getAccessToken(){
        CloseableHttpClient httpClient =  HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+AppId+"&secret="+AppSecret);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String data = EntityUtils.toString(httpEntity);
            Map<String, String> result = objectMapper.readValue(data, Map.class);
            redisTemplate.opsForValue().set("access_token", result.get("access_token"));
            System.out.println(data);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
