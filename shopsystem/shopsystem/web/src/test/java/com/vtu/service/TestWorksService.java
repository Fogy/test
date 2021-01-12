package com.vtu.service;

import com.alibaba.fastjson.JSON;
import com.vtu.model.Works;
import com.vtu.web.service.WorksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWorksService {
    @Autowired
    WorksService worksService;

    @Test
    public void test(){
        Works worksById = worksService.getWorksById("13");
        System.out.println(JSON.toJSONString(worksById,true));
    }
}
