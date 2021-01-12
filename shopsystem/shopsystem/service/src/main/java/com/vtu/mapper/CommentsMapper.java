package com.vtu.mapper;

import com.vtu.model.CommentWithChild;
import com.vtu.model.Comments;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentsMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comments record);

    int insertSelective(Comments record);

    Comments selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);

    List<CommentWithChild> selectByWorkId(@Param("comments") Comments comments);
}