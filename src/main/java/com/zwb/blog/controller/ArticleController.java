package com.zwb.blog.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Article;
import com.zwb.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    IArticleService service;

    @GetMapping("/{id}")
    @SentinelResource(value = "getArticle",blockHandler = "getArticleBlockHandle")
    public Result getArticle(@PathVariable("id") Integer id){
        Article article = service.getArticleById(id);
        if (article == null)
            return Result.failed();
        return Result.ok().put("article",article);
    }


    @GetMapping("/cells/{page}")
    public Result getAll(@PathVariable("page") Integer page){
        List<Article> articles =  service.getArticleCells(page);
        if (articles == null || articles.size() == 0)
            return Result.failed();
        return Result.ok().put("articles",articles);
    }

    @GetMapping("/timeline")
    public Result getAll(){
        List<Article> articles = service.getAllArticle();
        return Result.ok().put("articles",articles);
    }

    public Result getArticleBlockHandle(Integer id){
        Article article = new Article();
        article.setContent("服务器忙，请稍后重试~");
        article.setName("请稍后重试~");
        return Result.failed().put("article",article);
    }
}
