package com.vtu.controller;

import com.vtu.model.BaseResult;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
import com.vtu.service.impl.CommentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentsController {

    @Autowired
    CommentsServiceImpl commentsService;

    @RequestMapping(value = "/addComments")
    public BaseResult addComments(@RequestBody Comments comments) {
        BaseResult baseResult = commentsService.addComments(comments);
        return baseResult;
    }

    @RequestMapping(value = "/selectBYWorksId")
    public List<CommentWithChild> selectBYWorksId(@RequestBody Comments comments) {
        List<CommentWithChild> commentWithChildList = commentsService.selectBYWorksId(comments);
        return commentWithChildList;
    }

    @RequestMapping(value = "/delComments")
    public BaseResult delComments(@RequestBody Comments comments) {
        BaseResult baseResult = commentsService.delComments(comments);
        return baseResult;
    }

}
