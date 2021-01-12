package com.vtu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
@Controller
public class TestController {

    @RequestMapping("/test")
    private String test(HttpServletRequest request){
        request.setAttribute("test","object");
        return "/test/userList";
    }

}
