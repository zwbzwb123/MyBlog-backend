package com.zwb.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Article;
import com.zwb.blog.mapper.ArticleMapper;
import com.zwb.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService implements IArticleService {

    private final static Integer DEFAULT_PAGE_SIZE = 5;

    private Integer pages = Integer.MAX_VALUE;
    @Autowired
    ArticleMapper mapper;

    public List<Article> getArticleCells(Integer page){
        if (page > pages)
            return null;
        PageHelper.startPage(page,DEFAULT_PAGE_SIZE);
        List<Article> list = mapper.selectArticleCells();
        PageInfo<Article> info = new PageInfo<>(list);
        this.pages = info.getPages();
        return list;
    }

    public Article getArticleById(Integer id){
        Article article = mapper.getArticleById(id);
        return article;
    }

    @Override
    public List<Article> getAllArticle() {
        List<Article> articles = mapper.geAllArticle();
        return articles;
    }
}
