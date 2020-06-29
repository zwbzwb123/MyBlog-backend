package com.zwb.blog.entity;


import java.util.Date;

public class Article {

    private Integer id;
    private String name;
    private Integer count;
    private String summary;
    private Date createTime;
    private String content;
    private String label;

    public Article(){}

    public Article(Integer id, String name, Integer count, String summary, Date createTime, String content,String label) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.summary = summary;
        this.createTime = createTime;
        this.content = content;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
