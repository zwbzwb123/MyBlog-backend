package com.zwb.blog.service;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getCommentsById(String id);

    Result addComment(Comment comment);
}
