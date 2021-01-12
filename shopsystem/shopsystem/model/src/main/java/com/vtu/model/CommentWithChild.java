package com.vtu.model;

import java.util.List;

public class CommentWithChild extends Comments {
    List<Comments> commentsList;


    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }


}
