package com.vtu.service.mapper;

import com.google.gson.Gson;
import com.vtu.mapper.TestMapper;
import com.vtu.model.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDaoMapper {
    @Autowired
    TestMapper testMapper;

    @org.junit.Test
    public void  getUser(){
        Test test = testMapper.selectByPrimaryKey(1);
        Gson gson= new Gson();
        System.out.println(gson.toJson(test));
    }

}
