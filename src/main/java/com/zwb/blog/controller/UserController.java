package com.zwb.blog.controller;

import com.zwb.blog.common.CaptchaCache;
import com.zwb.blog.common.Result;
import com.zwb.blog.common.TaskExecuter;
import com.zwb.blog.common.task.EmailTask;
import com.zwb.blog.entity.Email;
import com.zwb.blog.entity.User;
import com.zwb.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService service;

    @GetMapping("/get")
    public Result getUser(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        User user = service.getUser(username,password);
        return user == null ?
                Result.failed().put("msg","用户名或密码错误"):
                Result.ok().put("msg","登陆成功")
                           .put("user",user);
    }

    @PutMapping
    public Result addUser(@RequestBody User user){
        Email email = CaptchaCache.get(user.getEmail());
        if (email == null)
            return Result.failed().put("msg","验证码已过期或邮箱不存在!");
        else if (!email.getCaptcha().equals(user.getCaptcha())) {
            return Result.failed().put("msg", "验证码错误,请检查输入的邮箱是否正确！");
        }
        return service.addUser(user);
    }

    @GetMapping("/captcha")
    public Result getCaptcha(@RequestParam("email") String email){
        TaskExecuter.get().execute(new EmailTask(email));
        return Result.ok();
    }


}
