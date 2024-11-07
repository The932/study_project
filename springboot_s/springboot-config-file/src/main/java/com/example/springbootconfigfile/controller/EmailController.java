package com.example.springbootconfigfile.controller;

import com.example.springbootconfigfile.pojo.EmailProperties;
import com.example.springbootconfigfile.service.EmailService;
import com.example.springbootconfigfile.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    //注入email配置信息实体类
    @Autowired
    private EmailService emailService;

    //测试方法
    @RequestMapping("/send")
    public Boolean send(){
        //收件人信箱
        String to = "932552569@qq.com";
        //邮件标题
        String title = "测试邮件";
        //邮件正文
        String content  = "我是即将年薪百万的打工仔......";
        //发送邮件
        boolean flag = emailService.send(to,title,content);
        return flag;
    }

}