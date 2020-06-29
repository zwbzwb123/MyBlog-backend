package com.zwb.blog.service.impl;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Comment;
import com.zwb.blog.mapper.CommentMapper;
import com.zwb.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentMapper mapper;

    @Override
    public List<Comment> getCommentsById(String id) {
        return mapper.getCommentsById(id);
    }

    @Override
    public Result addComment(Comment comment) {
        comment.setCreateTime(new Date());
        try {
            mapper.addComment(comment);
            return Result.ok().put("msg","评论成功,刷新页面即可看到")
                    .put("comment",comment);
        }catch (Exception e){
            return Result.error().put("msg","评论失败，请稍后重试");
        }
    }
}
