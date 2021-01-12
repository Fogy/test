package com.vtu.web.service.impl;

import com.vtu.model.BaseResult;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
import com.vtu.web.service.CommentsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentsServiceImpl implements CommentsService {
    @Override
    public BaseResult addComments(Comments comments) {
        return null;
    }

    @Override
    public List<CommentWithChild> selectBYWorksId(Comments comments) {
        return null;
    }

    @Override
    public BaseResult delComments(Comments comments) {
        return null;
    }
}
