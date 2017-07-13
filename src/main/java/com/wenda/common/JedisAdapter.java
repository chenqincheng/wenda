package com.wenda.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.wenda.pojo.User;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * Created by chen on 2017/6/26.
 */
public class JedisAdapter {
    public static void  print(int index, Object object){
        System.out.println(String.format("%d, %s",index,object));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/1");

        jedis.flushDB();
        /*getter and setter
        * */
        jedis.set("hello","world");
        print(1,jedis.get("hello"));
        //改名
        jedis.rename("hello","newhello");
        print(2,jedis.get("newhello"));
        //有效期,使用场景：验证码、缓存
        jedis.setex("he",15,"word");


        /*
        * */
        jedis.set("pv","100");
        jedis.incr("pv");
        jedis.incrBy("pv",5);
        print(3,jedis.get("pv"));
        jedis.decrBy("pv",3);
        print(3,jedis.get("pv"));

        /*List 双向列表
        * */

        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(4,jedis.lrange(listName,0,12));
        //list长度
        print(5,jedis.llen(listName));
        //弹出
        print(6,jedis.lpop(listName));
        print(6,jedis.llen(listName));
        jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER,"a4","newa1111");
        print(7,jedis.lrange(listName,0,12));


        /**
         * hash
         */
        String uerKey = "user";
        jedis.hset(uerKey,"name","chen");
        jedis.hset(uerKey,"age","18");
        jedis.hset(uerKey,"phone","13500073469");

        print(8,jedis.hget(uerKey,"name"));
        print(9,jedis.hgetAll(uerKey));

        jedis.hdel(uerKey,"phone");
        print(9,jedis.hgetAll(uerKey));
        print(10,jedis.hexists(uerKey,"age"));
        print(11,jedis.hkeys(uerKey));
        print(9,jedis.hvals(uerKey));
        //如果存在不改写
        jedis.hsetnx(uerKey,"name","cqc");
        jedis.hsetnx(uerKey,"school","gdmec");
        print(12,jedis.hgetAll(uerKey));

        /**
         * set 集合 交 并
         */

        String like1 = "like1";
        String like2 = "like2";
        for (int i = 1;i <= 10;i++){
                jedis.sadd(like1,String.valueOf(i));
                jedis.sadd(like2,String.valueOf(i*i));
        }

        //set members 集合成员
        print(13,jedis.smembers(like1));
        print(14,jedis.smembers(like2));

        //并
        print(15, jedis.sunion(like1,like2));

        //like1有，like2没有
        print(16,jedis.sdiff(like1,like2));

        //共同好友
        print(17,jedis.sinter(like1,like2));

        //是否存在
        print(18,jedis.sismember(like1,"12"));

        //删除
        jedis.srem(like1,"5");
        print(19,jedis.smembers(like1));

        //移动元素，把集合2的元素移动到集合1
        jedis.smove(like2,like1,"25");

        //集合成员数量
        print(20,jedis.scard(like1));


        /**
         * 优先队列
         */
        String rank = "rank";
        jedis.zadd(rank,15,"jim");
        jedis.zadd(rank,50,"ben");
        jedis.zadd(rank,60,"lucy");
        jedis.zadd(rank,100,"chen");

        print(21,jedis.zcard(rank));
        //区间
        print(22,jedis.zcount(rank,60,100));
        //查询
        print(23,jedis.zscore(rank,"chen"));
        //修改,加
        jedis.zincrby(rank,2,"chen");

        print(24,jedis.zscore(rank,"chen"));

        print(25,jedis.zrange(rank,0,120));

        //排行 默认从小到大排序
        //前三名
        print(26,jedis.zrevrange(rank,0,2));


        for (Tuple tuple : jedis.zrangeByScoreWithScores(rank,60,120)){
            print(27,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }

        //查询排名
        //从小到大
        print(27,jedis.zrank(rank,"chen"));
        //从大到小
        print(28,jedis.zrevrank(rank,"chen"));

        //默认8条线程，close
        JedisPool pool = new JedisPool();


        User user = new User();
        user.setName("xxx");
        user.setEmail("1111111");
        user.setPhone(123456);
        user.setPassword("123542");
        user.setId(1);
        //序列化
        jedis.set("user1", JSONObject.toJSONString(user));
        print(29,jedis.get("user1"));

        String value = jedis.get("user1");
        User user1 = JSON.parseObject(value,User.class);
        print(30,user1.getEmail());

    }
}
