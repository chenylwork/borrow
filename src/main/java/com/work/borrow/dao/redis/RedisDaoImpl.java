package com.work.borrow.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis操作类
 */
@Component("redisDao")
public class RedisDaoImpl implements RedisDao {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean setSMSCode(String mobile, String code) {
        try {
            redisTemplate.opsForValue().set(mobile, code);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getSMSCode(String mobile) {
        return redisTemplate.opsForValue().get(mobile);
    }
    @Override
    public void delCode(String mobile){redisTemplate.delete(mobile);}

    @Override
    public void set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(String key, String value, long time) {
        redisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }


}
