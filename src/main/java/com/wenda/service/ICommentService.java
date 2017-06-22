package com.wenda.service;

import com.wenda.pojo.Comment;

import java.util.List;

/**
 * Created by chen on 2017/6/13.
 */
public interface ICommentService {
    List<Comment> getAllCommentByEntity(int entityId, int entityType);
}
