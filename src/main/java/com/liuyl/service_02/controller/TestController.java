package com.liuyl.service_02.controller;


import com.liuyl.service_02.service.TestService;
import com.liuyl.service_02.service.WeChatApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyl01
 */
@RestController
@RequestMapping("/api/user")
public class TestController {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public  Map<String,Object> tset(){
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        result.put("msg","success");
        String key = "zszxz";
        String value = "知识追寻者";
        redisTemplate.opsForValue().set(key, value);
        return result;
    }

    //测试redis
    @GetMapping(value = "/getRedis")
    public Map<String,Object> testredis(){
        System.out.println(WeChatApiService.getCurrentUserOpenid());
        String aa = (String)redisTemplate.opsForValue().get("access_token");
        Map<String,Object> result = new HashMap<>();
        result.put("access_token",aa);
        return result;
    }

    @GetMapping("/test-mybatis")
    public Map<String,Object>  testMybatis(){
        Map<String,Object> result = new HashMap<>();
        result = testService.testMybatis();
        return result;
    }
}
