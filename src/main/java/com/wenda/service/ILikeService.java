package com.wenda.service;

/**
 * Created by chen on 2017/6/26.
 */
public interface ILikeService {
    long like(int userId, int entityType, int entityId);
    long dislike(int userId,int entityType,int entityId);

    int getLikeStatus (int userId,int entityType,int entityId);

    long getLikeCount(int entityType,int entityId);
}
