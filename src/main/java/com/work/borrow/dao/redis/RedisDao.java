package com.work.borrow.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作接口
 */
public interface RedisDao {

    /**
     * 存储手机验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return 存储成功返回true存储失败返回false
     */
    public boolean setSMSCode(String mobile, String code);

    /**
     * 获取手机验证码
     *
     * @param mobile 手机号
     * @return 验证码
     */
    public String getSMSCode(String mobile);

    /**
     * 删除验证码
     * @param mobile
     */
    public void delCode(String mobile);

    /**
     * 存储key
     * @param key
     * @param value
     */
    public void set(String key,String value);

    /**
     * 存储key，保存一定的时间
     * @param key
     * @param value
     * @param time
     */
    public void set(String key,String value,long time);

    /**
     * 获取key
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * 删除key
     * @param key
     * @return
     */
    public boolean del(String key);

}
