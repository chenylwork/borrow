package com.work.borrow.controller;

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
    private static final String RADIO_MESSAGE_KEY = "radio_message";
    /**
     * 录入广播消息
     * @param ms
     * @return
     */
    @RequestMapping("/radio/input")
    public Message inputRadioMessage(String ms) {
        Message message = null;
        if (ms != null && !ms.equals("")) {
            redisDao.set(RADIO_MESSAGE_KEY,ms);
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
    public Message getRadioMessage() {
        Message message = null;
        String radio = redisDao.get(RADIO_MESSAGE_KEY);
        if (radio != null && !radio.equals("")){
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_Y,Message.VALUE_CONTENT_QUERY_Y);
            message.put(Message.KEY_DATA,radio);
        } else {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_N,Message.VALUE_CONTENT_QUERY_N);
        }
        return message;
    }
}
