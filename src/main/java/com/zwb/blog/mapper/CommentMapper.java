package com.zwb.blog.mapper;

import com.zwb.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getCommentsById(String id);

    void addComment(Comment comment);
}
