package com.zwb.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.zwb.blog.common.Result;
import com.zwb.blog.common.TaskExecuter;
import com.zwb.blog.entity.Comment;
import com.zwb.blog.mapper.CommentMapper;
import com.zwb.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentMapper mapper;

    @Autowired
    StringRedisTemplate template;

    private final static String COMMENT_REDIS_PREFIX = "comment:";

    @Override
    public List<Comment> getCommentsById(String id) {
        List<String> range = template.opsForList().range(COMMENT_REDIS_PREFIX + id, 0, -1);
        if (range.size() > 0){
            List<Comment> res = new ArrayList<>();
            range.stream().forEach(cs ->
                res.add(JSON.parseObject(cs,Comment.class))
            );
            return res;
        }
        List<Comment> comments = mapper.getCommentsById(id);
        comments.stream().forEach(comment -> {
            template.opsForList().rightPush(COMMENT_REDIS_PREFIX+id,JSON.toJSONString(comment));
        });
        template.expire(COMMENT_REDIS_PREFIX+id,3600, TimeUnit.SECONDS);
        return comments;
    }

    @Override
    public Result addComment(Comment comment) {

        comment.setCreateTime(new Date());
        try {
            template.opsForList().rightPush(COMMENT_REDIS_PREFIX+comment.getArticleId(),JSON.toJSONString(comment));
            TaskExecuter.get().execute(new CommentAddTask(comment));
            return Result.ok().put("msg","评论成功,刷新页面即可看到")
                    .put("comment",comment);
        }catch (Exception e){
            return Result.error().put("msg","评论失败，请稍后重试");
        }
    }

    private class CommentAddTask implements  Runnable{

        private Comment comment;

        public CommentAddTask(Comment comment){
            this.comment = comment;
        }

        @Override
        public void run() {
            mapper.addComment(comment);
        }
    }
}
