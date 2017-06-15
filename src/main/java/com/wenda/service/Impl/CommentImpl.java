package com.wenda.service.Impl;

import com.wenda.dao.CommentMapper;
import com.wenda.pojo.Comment;
import com.wenda.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chen on 2017/6/13.
 */
@Service("iCommentService")
public class CommentImpl implements ICommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<Comment> getCommentByEntity(int entityId,int entityType) {
        return null;
    }
}
