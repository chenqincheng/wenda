package com.wenda.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by chen on 2017/6/26.
 */
@Service
public class JedisPoolService implements InitializingBean {
    private JedisPool pool;
    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6397/2");
    }

    public long sadd(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }


}