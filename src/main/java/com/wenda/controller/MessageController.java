package com.wenda.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenda.common.CurrentUser;
import com.wenda.common.JSONResponse;
import com.wenda.common.ViewObject;
import com.wenda.pojo.Message;
import com.wenda.pojo.User;
import com.wenda.service.IMessageService;
import com.wenda.service.IUserService;
import com.wenda.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen on 2017/6/23.
 */
@Controller
public class MessageController {

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private IMessageService iMessageService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SensitiveService sensitiveService;

    @RequestMapping(path = "/addMessage",method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse addMessage(@RequestParam(value = "toName") String toName,
                                   @RequestParam(value = "content") String content){

        /**
         * 如果用户未登录，返回999让前端自动跳转到登录页面
         */

        if (currentUser.getUser() == null){
            return JSONResponse.returnStatusAndMessage(999,"未登录");
        }

        User user = iUserService.selectUserByName(toName);
        if (user == null){
            return JSONResponse.returnStatusAndMessage(404,"收信人不存在");
        }

        /*id, from_id, to_id, content, created_time, has_read, conversation_id*/
        Message message = new Message();
        message.setFromId(currentUser.getUser().getId());
        message.setToId(user.getId());
        message.setContent(HtmlUtils.htmlEscape(sensitiveService.filter(content)));
        message.setCreatedTime(new Date());
        iMessageService.addMessage(message);

        return JSONResponse.returnSuccessMessage("发信成功！");
    }

    @RequestMapping(path = "/msg/list",method = RequestMethod.GET)
    public String showMessageList(Model model){
        if (currentUser.getUser() == null){
            return "redirect:/login";
        }
        int userId = currentUser.getUser().getId();
        List<Message> conversationList = iMessageService.getAllConversationByUserId(userId);
        List<ViewObject> conversations = new ArrayList<ViewObject>();
        for (Message message :conversationList){
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            int targetId = message.getFromId() == userId ? message.getToId() : message.getFromId();
            vo.set("user",iUserService.selectUserByUserId(targetId));
            vo.set("unread",iMessageService.getconversationUnreadCount(userId,message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations",conversations);
        return "letter";
    }

    @RequestMapping(path = "/msg/detail",method = RequestMethod.GET)
    public String showMessageDetail(Model model,@RequestParam(value = "conversationId",required = false) String conversationId){
        List<Message> messageList = iMessageService.getAllMessageByConversatuonId(conversationId);
        List<ViewObject> messages = new ArrayList<ViewObject>();
        for (Message message : messageList){
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            vo.set("fuser",iUserService.selectUserByUserId(message.getFromId()));
            vo.set("tuser",iUserService.selectUserByUserId(message.getToId()));
            System.out.println(message);
            messages.add(vo);
        }
        model.addAttribute("messages",messages);
        return "letterDetail";
    }

}
