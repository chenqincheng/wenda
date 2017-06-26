package com.wenda.service;

import com.wenda.pojo.Message;

import java.util.List;

/**
 * Created by chen on 2017/6/23.
 */
public interface IMessageService {
    int addMessage(Message message);

    List<Message> getAllMessageByConversatuonId(String conversationId);

    List<Message> getAllConversationByUserId(int userId);

    int getconversationUnreadCount(int userId,String conversationId);
}
