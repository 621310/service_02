package com.liuyl.service_02.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class TestController {

    @GetMapping("/getAllUser")
    public void tset(){
        System.out.println(123);
    }
}
