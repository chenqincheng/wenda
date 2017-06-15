package com.wenda.controller;

import com.wenda.common.CurrentUser;
import com.wenda.common.JsonResponse;
import com.wenda.pojo.Question;
import com.wenda.pojo.User;
import com.wenda.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * Created by chen on 2017/6/11.
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private IQuestionService iQuestionService;

    @Autowired
    CurrentUser currentUser;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse add(@RequestParam("title") String title, @RequestParam("content") String content){
        try{
            //TODO 验证数据 比如：用户是否登录
            Question question = new Question();
            //HtmlUtils.htmlEscape 把用户输入的内容过滤
            question.setTitle(HtmlUtils.htmlEscape(title));
            question.setContent(HtmlUtils.htmlEscape(content));
            question.setCreatedTime(new Date());
            question.setUserId(currentUser.getUser().getId());

            JsonResponse<String> response =  iQuestionService.addQueston(question);

            return  response;
        }catch (Exception e){
            System.out.println();
        }
        return JsonResponse.returnErrorMessage("参数有误！");
    }
}
