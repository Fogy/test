package com.vtu.web.controller;

import com.vtu.web.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    HiService hiService;

    @RequestMapping(value = "/getHi",method = RequestMethod.GET)
    public String getHi(String name) {
        return hiService.getHi(name);
    }
}
