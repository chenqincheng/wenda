package com.wenda.controller;

import com.wenda.common.CurrentUser;
import com.wenda.common.EntityType;
import com.wenda.common.JSONResponse;
import com.wenda.common.ViewObject;
import com.wenda.pojo.Comment;
import com.wenda.pojo.Question;
import com.wenda.pojo.User;
import com.wenda.service.ICommentService;
import com.wenda.service.IQuestionService;
import com.wenda.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * Created by chen on 2017/6/11.
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private IQuestionService iQuestionService;

    @Autowired
    private  CurrentUser currentUser;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IUserService iUserService;


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse add(@RequestParam("title") String title, @RequestParam("content") String content){
        try{
            //TODO 验证数据 比如：用户是否登录
            Question question = new Question();
            //HtmlUtils.htmlEscape 把用户输入的内容过滤
            question.setTitle(HtmlUtils.htmlEscape(title));
            question.setContent(HtmlUtils.htmlEscape(content));
            question.setCreatedTime(new Date());
            question.setUserId(currentUser.getUser().getId());

            JSONResponse response =  iQuestionService.addQueston(question);
            return  response;
        }catch (Exception e){
            System.out.println();
        }
        return JSONResponse.returnErrorMessage("参数有误！");
    }

    @RequestMapping(path = "/{questionId}",method = RequestMethod.GET)
    public String showQuestionPage(Model model, @PathVariable(value = "questionId") Integer questionId){
       //获取问题实体
        Question question = iQuestionService.selectQuestionByQuestionId(questionId);
        if (question == null){
            model.addAttribute("msg","问题不存在！");
            return "404";
        }
        //获取问题的回答
        List<Comment> commentList = iCommentService.getAllCommentByEntity(questionId,EntityType.COMMENT);

        List<ViewObject> comments = new ArrayList<ViewObject>();

        for (Comment comment :commentList){
            ViewObject vo = new ViewObject();
            User user = iUserService.selectUserByUserId(comment.getUserId());
            vo.set("comment",comment);
            vo.set("user",user);
            comments.add(vo);
        }

        model.addAttribute("comments",comments);
        model.addAttribute(question);

        return "detail";
    }
}
