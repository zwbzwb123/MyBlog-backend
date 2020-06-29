package com.zwb.blog.entity;

import java.awt.*;
import java.util.Date;

public class Comment {

    private Integer id;
    private Integer articleId;
    private Date createTime;
    private String content;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticle_id(Integer articleId) {
        this.articleId = articleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Comment(){}

    public Comment(Integer id, Integer articleId, Date createTime, String content, String username) {
        this.id = id;
        this.articleId = articleId;
        this.createTime = createTime;
        this.content = content;
        this.username = username;
    }
}
