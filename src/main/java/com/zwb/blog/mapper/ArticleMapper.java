package com.zwb.blog.mapper;

import com.zwb.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    List<Article> selectArticleCells();

    Article getArticleById(Integer id);

    List<Article> geAllArticle();

    void updateCount(Integer id, Integer count);
}
