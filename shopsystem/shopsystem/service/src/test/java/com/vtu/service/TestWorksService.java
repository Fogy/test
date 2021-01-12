package com.vtu.service;

import com.alibaba.fastjson.JSON;
import com.vtu.model.Works;
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

    @Test
    public void testIndex(){
        String string="https://vtuweb.oss-cn-beijing.aliyuncs.com/video/videoImport/1576865335896/695f4609a52444a3ae73f488c6885f0b.mp4";
        String point ="https://vtuweb.oss-cn-beijing.aliyuncs.com/";
        System.out.println(string.replace(point,""));
    }
}
