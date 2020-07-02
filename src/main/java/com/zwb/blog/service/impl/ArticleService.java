package com.zwb.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwb.blog.common.ArticleCountCache;
import com.zwb.blog.common.Result;
import com.zwb.blog.entity.Article;
import com.zwb.blog.mapper.ArticleMapper;
import com.zwb.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleService implements IArticleService {

    private final static Integer DEFAULT_PAGE_SIZE = 5;

    private Integer pages = Integer.MAX_VALUE;

    private final static String ARTICLE_REDIS_PREFIX = "article:id:";

    private final static String ARTICLES_PREFIX_KEY = "article:all";


    @Autowired
    ArticleMapper mapper;


    @Autowired
    StringRedisTemplate redisTemplate;

    public List<Article> getArticleCells(Integer page){
        if (page > pages)
            return null;
        PageHelper.startPage(page,DEFAULT_PAGE_SIZE);
        List<Article> list = mapper.selectArticleCells();
        list.stream().forEach( article ->
                {
                    Integer c;
                    if (( c = ArticleCountCache.get(article.getId())) == 0){
                        ArticleCountCache.put(article.getId(),article.getCount());
                    } else {
                        article.setCount(c);
                    }
                }
        );
        PageInfo<Article> info = new PageInfo<>(list);
        this.pages = info.getPages();
        return list;
    }

    public Article getArticleById(Integer id){
        ArticleCountCache.incr(id);
        String as = redisTemplate.opsForValue().get(ARTICLE_REDIS_PREFIX + id);
        if (!StringUtils.isEmpty(as)){
            System.out.println("use cache get article by id "+ id);
            Article article = JSON.parseObject(as, Article.class);
            return article;
        }
        Article article = mapper.getArticleById(id);
        as = JSON.toJSONString(article);
        redisTemplate.opsForValue().set(ARTICLE_REDIS_PREFIX+id,as,3600*24, TimeUnit.SECONDS);
        System.out.println("set article cache");
        return article;
    }


    @Override
    public List<Article> getAllArticle() {
        Set<String> as = redisTemplate.opsForZSet().reverseRange(ARTICLES_PREFIX_KEY, 0, -1);
        if (as != null && as.size() > 0){
            System.out.println("use cache get all articles");
            List<Article> res = new ArrayList<>();
            as.forEach(article ->
                res.add(JSON.parseObject(article,Article.class))
            );
            return res;
        }

        List<Article> articles = mapper.geAllArticle();
        articles.stream().forEach(article -> {
            String s = JSON.toJSONString(article);
            redisTemplate.opsForZSet().add(ARTICLES_PREFIX_KEY,s,article.getCreateTime().getTime());
        });
        System.out.println("set articles cache");
        return articles;
    }
}
