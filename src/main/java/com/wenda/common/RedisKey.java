package com.wenda.common;

/**
 * Created by chen on 2017/6/26.
 */
public class RedisKey {
    private static String SPLIT = ":";
    /*业务前缀
    * */
    private static String LIKE = "LIKE";
    private static String DISLIKE = "DISLIKE";
    private static String EventQUEUE = "EventQUEUE";

    public static String getLikeKey(int entityType, int entityId){
        return LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getDislikeKey(int entityType, int entityId){
        return DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getEventQUEUE(){
        return EventQUEUE;
    }
}
