package com.work.borrow.serviceImpl;

import com.work.borrow.config.SMSConfig;
import com.work.borrow.dao.redis.RedisDao;
import com.work.borrow.po.Message;
import com.work.borrow.service.SMSService;
import com.work.borrow.util.CharUtils;
import com.work.borrow.util.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信业务操作实现类
 */
@Service("smsService")
public class SMSServiceImpl implements SMSService {
    @Autowired
    private SMSConfig smsConfig;
    @Autowired
    private RedisDao redisDao;

    @Override
    public Message sendSMSCode(String mobile) {
        Message message = null;
        if (mobile == null || mobile.equals("")) {
            return Message.createFailMessage(Message.VALUE_CODE_SMS_SEND_MOBILE_N,Message.VALUE_CONTENT_SMS_SEND_MOBILE_N);
        }
        // 获取验证码
        String code = CharUtils.randomNumCode(6);
        // 发送验证码
        boolean isok = SMSUtils.send(mobile, code);
        if(isok) {
            // 缓存验证码
            redisDao.setSMSCode(mobile,code);
            message = Message.createSMSMessage(code);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_SMS_SEND_NO,Message.VALUE_CONTENT_SMS_SEND_NO);
        }
        return message;
    }

    @Override
    public Message checkSMSCode(String mobile,String code) {
        Message message = null;
        String oldCode = redisDao.getSMSCode(mobile);
        if (oldCode == null || "".equals(oldCode)) {
            message = Message.createFailMessage(Message.VALUE_CODE_SMS_EXISTS_NO,Message.VALUE_CONTENT_SMS_EXISTS_NO);
        } else {
            if (oldCode.equals(code)) {
                message = Message.createSuccessMessage(Message.VALUE_CODE_SMS_CHK_YES,Message.VALUE_CONTENT_SMS_CHK_YES);
                redisDao.delCode(mobile);// 验证成功，删除验证码
            } else {
                message = Message.createFailMessage(Message.VALUE_CODE_SMS_CHK_NO,Message.VALUE_CONTENT_SMS_CHK_NO);
            }
        }
        return message;
    }

}
