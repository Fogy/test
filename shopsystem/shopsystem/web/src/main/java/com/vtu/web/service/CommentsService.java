package com.vtu.web.service;

import com.vtu.model.BaseResult;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
import com.vtu.web.service.impl.CommentsServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "service",fallback = CommentsServiceImpl.class)
public interface CommentsService {

    @RequestMapping(value = "/addComments",method = RequestMethod.POST)
    BaseResult addComments(Comments comments);

    @RequestMapping(value = "/selectBYWorksId",method = RequestMethod.POST)
    List<CommentWithChild> selectBYWorksId(Comments comments);

    @RequestMapping(value = "/delComments",method = RequestMethod.POST)
    BaseResult delComments(Comments comments);
}
