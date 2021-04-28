package com.liuyl.service_02.controller;


import com.liuyl.service_02.config.JwtTokenUtils;
import com.liuyl.service_02.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyl01
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {


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

}
