package com.vtu.web.controller.works;

import com.vtu.model.BaseResult;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
import com.vtu.model.Customer;
import com.vtu.web.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentsService commentsService;

    /**
     * 添加评论
     * @param request
     * @param comments
     * @return
     */
    @RequestMapping(value = "/addComments")
    public BaseResult addComments(HttpServletRequest request,Comments comments){
        Customer cust = (Customer) request.getSession().getAttribute("customer");
        comments.setCustomerId(cust.getAccountId());
        comments.setCommentTime(new Date());
        BaseResult baseResult = commentsService.addComments(comments);
        return baseResult;
    }

    /**
     * 查询评论
     * @param comments
     * @return
     */
    @RequestMapping(value = "/selectBYWorksId")
    public List<CommentWithChild> selectBYWorksId(Comments comments){
        List<CommentWithChild> commentWithChildList = commentsService.selectBYWorksId(comments);
        return commentWithChildList;
    }

    @RequestMapping(value = "/delComment")
    public BaseResult delComments(Comments comments){
        BaseResult baseResult = commentsService.delComments(comments);
        return baseResult;
    }
}
