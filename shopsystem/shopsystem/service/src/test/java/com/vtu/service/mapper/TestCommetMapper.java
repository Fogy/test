package com.vtu.service.mapper;

import com.alibaba.fastjson.JSON;
import com.vtu.mapper.CommentsMapper;
import com.vtu.mapper.VipMapper;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
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
public class TestCommetMapper {
    @Autowired
    CommentsMapper commentsMapper;

    @Test
    public void testSelectByWorkId(){
        Comments comments = new Comments();
        comments.setWorksId((long) 1);
        List<CommentWithChild> commentWithChildren = commentsMapper.selectByWorkId(comments);
        System.out.println(JSONArray.toJSONString(commentWithChildren));

    }

    @Test
    public void testSelectById(){
        Comments comments = commentsMapper.selectByPrimaryKey((long) 1);
        System.out.println(JSON.toJSONString(comments));

    }
}
