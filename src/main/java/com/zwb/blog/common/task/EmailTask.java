package com.zwb.blog.common.task;

import com.zwb.blog.common.CaptchaCache;
import com.zwb.blog.entity.Email;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Date;
import java.util.UUID;

public class EmailTask implements Runnable {

    private static final String DEFAULT_SENDER = "2667130414@qq.com";
    private static final String PASSWORD = "oywmtjkchfbydjbh";

    private String email;

    public EmailTask(String email){
        this.email = email;
    }

    @Override
    public void run() {
        try {
            String captcha = createCaptcha();
            Email e = new Email(captcha,new Date());
            CaptchaCache.put(this.email,e);
            HtmlEmail email = new HtmlEmail();//创建电子邮件对象
            email.setSSL(true);
            email.setHostName("SMTP.qq.com");//设置发送电子邮件使用的服务器主机名
            email.setSmtpPort(587);//设置发送电子邮件使用的邮件服务器的TCP端口地址
            email.setAuthenticator(new DefaultAuthenticator(DEFAULT_SENDER, PASSWORD));//邮件服务器身份验证
            email.setFrom(DEFAULT_SENDER);//设置发信人邮箱
            email.setSubject("博客的验证码");
            email.setMsg("your captcha : "+captcha);//设置邮件文本内容
            email.addTo(this.email);//设置收件人
            email.send();//发送邮件
        }catch(EmailException e){
            e.printStackTrace();
            CaptchaCache.remove(this.email);
        }
    }

    private String createCaptcha(){
        return UUID.randomUUID().toString().substring(0,4);
    }
}
