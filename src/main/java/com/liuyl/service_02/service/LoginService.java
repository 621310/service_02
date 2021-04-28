package com.liuyl.service_02.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuyl.service_02.WxInfo;
import com.liuyl.service_02.config.JwtTokenUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Value("${wechat.AppId}")
    private String AppId;
    @Value("${wechat.AppSecret}")
    private String AppSecret;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public Map<String,String> jscode2session(String code){
        Map<String,String> result = new HashMap<>();

        CloseableHttpClient httpClient =  HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/sns/jscode2session?appid="+ AppId +"&secret="+ AppSecret +"&js_code=" +code+ "&grant_type=authorization_code");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
           String data = EntityUtils.toString(httpEntity);
            WxInfo wxInfo = objectMapper.readValue(data,WxInfo.class);
            Map map = new HashMap();
            map.put("user","admin");
            map.put("password","admin");
            map.put("sessionKey",wxInfo.getSession_key());
            map.put("openid",wxInfo.getOpenid());
            String token = jwtTokenUtils.createToken(map);
            result.put("token", token);
            System.out.println(token);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
