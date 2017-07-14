package com.wenda.controller;

import com.wenda.service.IQuestionService;
import com.wenda.service.IUserService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by chen on 2017/6/8.
 */
@Controller
public class IndexController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IUserService iUserService;



    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/mail")
    @ResponseBody
    public String senMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("admin@amodao.com");
        helper.setTo("191318455@qq.com");
        helper.setSubject("验证码");
        Map<String, Object> model = new HashMap<>();
        model.put("code", "14965");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfigurer.getConfiguration().getTemplate("mail.ftl"), model);
        helper.setText(content, true);
        mailSender.send(mimeMessage);

        return "邮件发送成功";

    }
}
