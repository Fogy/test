package com.vtu.controller;

import com.vtu.mapper.TestMapper;
import com.vtu.model.Test;
import com.vtu.service.impl.TestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    TestMapper testMapper;

    @Autowired
    TestServiceImpl testService;

    @Value("${server.port}")
    private String port;

    @RequestMapping("/getHi")
    public String getHi(String name) {
        return "端口："+port+"姓名:"+name;
    }

    @RequestMapping("/getTest")
    public Test getUser(){
        //return testMapper.selectByPrimaryKey(1);
        return testService.getTest(1);
    }
}
