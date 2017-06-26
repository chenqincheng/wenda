package com.wenda.service.Impl;

import com.wenda.dao.MessageMapper;
import com.wenda.pojo.Message;
import com.wenda.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chen on 2017/6/23.
 */
@Service("iMessageService")
public class MessageIpml implements IMessageService {

    @Autowired
    MessageMapper messageMapper;
    @Override
    public int addMessage(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public List<Message> getAllMessageByConversatuonId(String conversationId) {
        return messageMapper.getAllMessageByConversationId(conversationId);
    }

    @Override
    public List<Message> getAllConversationByUserId(int userId) {
        return messageMapper.getAllConversationByUserId(userId);
    }

    @Override
    public int getconversationUnreadCount(int userId, String conversationId) {
        return messageMapper.getConversationUnreadCount(userId,conversationId);
    }
}
