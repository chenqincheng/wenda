package com.wenda.service.Impl;


import com.wenda.common.RedisKey;
import com.wenda.service.ILikeService;
import com.wenda.service.JedisPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chen on 2017/6/26.
 */
@Service("iLikeService")
public class LikeImpl implements ILikeService {
    @Autowired
    private JedisPoolService jedisPoolService;




    /**
     * 点赞
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public long like(int userId, int entityType, int entityId) {

        String likekey = RedisKey.getLikeKey(entityType, entityId);
        jedisPoolService.sadd(likekey, String.valueOf(userId));

        String dislikeKey = RedisKey.getDislikeKey(entityType, entityId);
        jedisPoolService.srem(dislikeKey, String.valueOf(userId));

        return jedisPoolService.scard(likekey);
    }

    /**
     * 点踩
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public long dislike(int userId, int entityType, int entityId) {
        String dislikeKey = RedisKey.getDislikeKey(entityType, entityId);
        jedisPoolService.sadd(dislikeKey, String.valueOf(userId));

        String likeKey = RedisKey.getLikeKey(entityType, entityId);
        jedisPoolService.srem(likeKey, String.valueOf(userId));
        return jedisPoolService.scard(likeKey);
    }

    /**
     * 当前用户赞踩状态
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKey.getLikeKey(entityType, entityId);
        if (jedisPoolService.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKey.getDislikeKey(entityType, entityId);
        return jedisPoolService.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    /**
     * 当前实体的点赞情况
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKey.getLikeKey(entityType, entityId);
        return jedisPoolService.scard(likeKey);
    }


}
