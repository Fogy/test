package com.vtu.service.mapper;

import com.vtu.mapper.VipMapper;
import com.vtu.model.Vip;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVipMapper {
    @Autowired
    VipMapper mapper;

    @Test
    public void testSelectByVipType(){
        List<Vip> vips = mapper.selectByVipType(1L);
        System.out.println(JSONArray.toJSONString(vips));

    }
}
