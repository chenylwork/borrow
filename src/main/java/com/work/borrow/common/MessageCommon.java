package com.work.borrow.common;

import com.work.borrow.dao.redis.RedisDao;
import com.work.borrow.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @ClassName MessageCommon
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2018/12/4- 13:57
 * @Version 1.0
 **/
@Component
public class MessageCommon {
    @Autowired
    private RedisDao redisDao;

    /**
     * 获取消息
     * @param account 用户账户
     * @return
     */
    public Message getMessage(String account) {
        // 获取个人消息
        String content = redisDao.get(getReviewMessageKey(account));
        // 获取系统消息
        if (content == null || content.equals("")) {
            content = redisDao.get(RADIO_MESSAGE_KEY);
        }
        if (content != null && !content.equals("")) {
            return Message.createSuccessMessage(content);
        } else {
            return Message.createFailMessage();
        }
    }
    /**
     * 设置审核信息
     * @param account  信息对应用户
     * @param message 提示消息
     */
    public void setReviewMessage(String account,String status,String message) {
        redisDao.set(getReviewMessageKey(account),message);
    }

    /**
     * 设置全局消息
     * @param message 消息内容
     */
    public void setRadioMessage(String message) {
        redisDao.set(RADIO_MESSAGE_KEY,message);
    }

    /**
     * 获取审核通过消息
     * @param account
     * @return
     */
    public String getReviewSuccessMessage(String account) {
        return "您的借款审核已经通过，赶紧付款开始借款吧！！";
    }

    /**
     * 获取审核失败消息
     * @param account
     * @return
     */
    public String getReviewFailMessage(String account) {
        return "您的借款审核被驳回，请认真填写借款资料以便于，尽快借款";
    }
    /**
     * 获取审核消息存储在redis中的key
     * @param account 用户账户
     * @return
     */
    private String getReviewMessageKey(String account) {
        return REVIEW_MESSAGE_PREFIX+account;
    }
    private static final String REVIEW_MESSAGE_PREFIX = "review_message_"; // 审核个人广播消息
    private static final String RADIO_MESSAGE_KEY = "radio_message"; // 系统广播消息

}
