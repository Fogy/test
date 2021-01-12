package com.vtu.service;

import com.vtu.model.BaseResult;
import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;

import java.util.List;

public interface CommentsService {
    BaseResult addComments(Comments comments);

    BaseResult delComments(Comments comments);

    List<CommentWithChild> selectBYWorksId(Comments comments);

}
