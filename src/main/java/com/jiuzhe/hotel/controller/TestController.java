package com.jiuzhe.hotel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author:郑鹏宇
 * @Date: Created in 10:29 2019/11/4
 */
@RestController
public class TestController {
    @GetMapping("/a")
    public String s() {
        return "ss";
    }
}
