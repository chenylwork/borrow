package com.work.borrow.controller;

import com.work.borrow.common.MessageCommon;
import com.work.borrow.dao.redis.RedisDao;
import com.work.borrow.po.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RedisDao redisDao;
    @Autowired
    private MessageCommon messageCommon;
    /**
     * 录入广播消息
     * @param ms
     * @return
     */
    @RequestMapping("/radio/input")
    public Message inputRadioMessage(String ms) {
        Message message = null;
        if (ms != null && !ms.equals("")) {
            messageCommon.setRadioMessage(ms);
            message = Message.createSuccessMessage(Message.VALUE_CODE_INPUT_Y,Message.VALUE_CONTENT_INPUT_Y);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_INPUT_N,Message.VALUE_CONTENT_INPUT_N);
        }
        return message;
    }

    /**
     * 获取广播消息
     * @return
     */
    @RequestMapping("/radio/get")
    public Message getRadioMessage(String account) {
        Message message = null;
        Message radioMessage = messageCommon.getMessage(account);
        if (Message.VALUE_STATUS_SUCCESS.equals(radioMessage.get(Message.KEY_STATUS))){
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_Y,Message.VALUE_CONTENT_QUERY_Y);
            message.put(Message.KEY_DATA,radioMessage.get(Message.KEY_DATA));
        } else {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_N,Message.VALUE_CONTENT_QUERY_N);
        }
        return message;
    }
}
