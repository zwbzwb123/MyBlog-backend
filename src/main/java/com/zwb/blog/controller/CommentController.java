package com.zwb.blog.controller;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Comment;
import com.zwb.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ICommentService service;

    @GetMapping("/{id}")
    public Result getComments(@PathVariable String id){
        List<Comment> list = service.getCommentsById(id);
        return Result.ok().put("comments",list);
    }


    @PutMapping
    public Result addComment(@RequestBody Comment comment){
        return service.addComment(comment);
    }
}
