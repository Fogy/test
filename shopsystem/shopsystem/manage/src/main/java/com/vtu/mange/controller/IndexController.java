package com.vtu.mange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页访问
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String goIndex(){
        return "index/index";
    }
}
