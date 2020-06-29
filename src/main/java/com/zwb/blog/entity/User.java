package com.zwb.blog.entity;

public class User {

    private String username;
    private String password;
    private String email;
    private String captcha;

    public User() { }

    public User(String username, String password, String email,String captcha) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.captcha = captcha;
    }

    public String getUsername() {
        return username;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
