package com.wenda.controller;

import com.wenda.common.CurrentUser;
import com.wenda.common.EntityType;
import com.wenda.dao.CommentMapper;
import com.wenda.pojo.Comment;
import com.wenda.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by chen on 2017/6/21.
 */
@Controller
public class CommentController {
    @Autowired
    CurrentUser currentUser;
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    SensitiveService sensitiveService;

    /**
     * 增加问题评论
     * @param questionId
     * @param content
     * @return
     */
    @RequestMapping(value = "/question/addcomment",method = RequestMethod.POST)
    public String addQuestionComment(@RequestParam(value = "questionId") Integer questionId, @RequestParam(value = "content") String content){
        if (currentUser.getUser() == null){
            return "redirect:/login";
        }
        Comment comment = new Comment();
        /*id, user_id, entity_id, entity_type, content, created_time, status*/
        comment.setUserId(currentUser.getUser().getId());
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.COMMENT);
        comment.setContent(sensitiveService.filter(content));
        comment.setCreatedTime(new Date());
        comment.setStatus(0);
        int resultCount = commentMapper.insert(comment);
        return "redirect:/question/"+questionId;
    }
}
