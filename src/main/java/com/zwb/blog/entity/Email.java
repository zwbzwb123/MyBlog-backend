package com.zwb.blog.entity;

import java.util.Date;

public class Email {

    private String captcha;

    private Date createTime;

    public Email(String captcha, Date createTime) {
        this.captcha = captcha;
        this.createTime = createTime;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
