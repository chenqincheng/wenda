package com.wenda.service.Impl;


import com.wenda.common.RedisKey;
import com.wenda.common.RedisPool;
import com.wenda.service.ILikeService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by chen on 2017/6/26.
 */
@Service("iLikeService")
public class LikeImpl implements ILikeService {


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
        Jedis jedis =  RedisPool.getJedis();
        jedis.sadd(likekey, String.valueOf(userId));
        String dislikeKey = RedisKey.getDislikeKey(entityType, entityId);
        jedis.srem(dislikeKey, String.valueOf(userId));
        Long result = jedis.scard(likekey);

        jedis.close();

        return result;
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
        Jedis jedis =  RedisPool.getJedis();
        String dislikeKey = RedisKey.getDislikeKey(entityType, entityId);
        jedis.sadd(dislikeKey, String.valueOf(userId));

        String likeKey = RedisKey.getLikeKey(entityType, entityId);
        jedis.srem(likeKey, String.valueOf(userId));
        Long result = jedis.scard(dislikeKey);

        jedis.close();

        return result;
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
        Jedis jedis =  RedisPool.getJedis();

        if (jedis.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKey.getDislikeKey(entityType, entityId);

        int result = jedis.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;

        jedis.close();

        return result;
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
        Jedis jedis =  RedisPool.getJedis();
        Long result = jedis.scard(likeKey);
        return result;
    }


}
