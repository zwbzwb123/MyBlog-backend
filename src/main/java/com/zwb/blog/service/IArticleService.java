package com.zwb.blog.service;

import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Article;

import java.util.List;

public interface IArticleService {

    List<Article> getArticleCells(Integer page);

    Article getArticleById(Integer id);

    List<Article> getAllArticle();
}
