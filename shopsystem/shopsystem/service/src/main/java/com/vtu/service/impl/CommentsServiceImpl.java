package com.vtu.service.impl;

import com.vtu.mapper.CommentsMapper;
import com.vtu.model.BaseResult;
import com.vtu.model.BaseResultCode;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
import com.vtu.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsMapper commentsMapper;

    @Override
    public BaseResult addComments(Comments comments) {
        BaseResult baseResult = new BaseResult();
        int num = commentsMapper.insert(comments);
        if(num>1){
            baseResult.setCode(200);
        }
        return baseResult;
    }

    @Override
    public BaseResult delComments(Comments comments) {
        BaseResult baseResult = new BaseResult();
        int num = commentsMapper.deleteByPrimaryKey(comments.getCommentId());
        if(num>1){
            baseResult.setCode(200);
        }
        return baseResult;
    }

    @Override
    public List<CommentWithChild> selectBYWorksId(Comments comments) {
        List<CommentWithChild> commentWithChildList = commentsMapper.selectByWorkId(comments);
        return commentWithChildList;
    }

}
