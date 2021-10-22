package com.liuyl.service_02.controller;


import com.liuyl.service_02.config.JwtTokenUtils;
import com.liuyl.service_02.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liuyl01
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {


    @Autowired
    RabbitTemplate rabbitTemplate;


    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final JwtTokenUtils jwtTokenUtils;
    private final LoginService loginService;

    public LoginController(JwtTokenUtils jwtTokenUtils, LoginService loginService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public String login(String username,String password){
        Map map = new HashMap();
        map.put("user",username);
        map.put("password",password);
        map.put("sessionKey","4545");
        map.put("openid","23232");
        return jwtTokenUtils.createToken(map);
    }

    @GetMapping("/wx-login")
    public Map<String,String> wxLogin(String code){
        Map<String,String> result = new HashMap<>();
        log.info("微信小程序登录");
        try{
            result = loginService.jscode2session(code);
        }catch (Exception e){
            e.printStackTrace();
            log.info("微信小程序登录异常");
        }
        return result;
    }


    //测试rabitmq消息推送
    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }



}
