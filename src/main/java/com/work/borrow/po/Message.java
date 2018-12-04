package com.work.borrow.po;

import com.work.borrow.util.MessageCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *消息对象
 */
public class Message extends LinkedHashMap implements com.work.borrow.util.Message{
    private Message() {
    }
    private Message(String status) {
        this.put(KEY_STATUS,status);
    }

    private Message(String status,String code,String content) {
        this.put(KEY_STATUS,status); // 消息状态，success，fail
        this.put(KEY_CODE,code); // 消息编号，
        this.put(KEY_CONTENT,content); // 消息内容
    }

    /**
     * 创建一个普通消息
     * @return
     */
    public static <T> Message createMessage(T t) {
        Message message = new Message();
        message.put(KEY_DATA,t);
        return message;
    }

    public static <T> Message createSuccessMessage(T data) {
        Message message = createSuccessMessage();
        message.put(KEY_DATA,data);
        return message;
    }
    /**
     * 获取成功消息
     * @param code 消息编号
     * @param content 消息内容
     * @return
     */
    public static Message createSuccessMessage(String code,String content) {
        return new Message(VALUE_STATUS_SUCCESS,code,content);
    }

    /**
     * 获取失败消息
     * @param code 消息编号
     * @param content 消息内容
     * @return
     */
    public static Message createFailMessage(String code,String content) {
        return new Message(VALUE_STATUS_FAIL,code,content);
    }

    /**
     * 创建一个错误消息对象
     * @return
     */
    public static Message createFailMessage() {
        return  new Message(VALUE_STATUS_FAIL);
    }

    /**
     * 创建一个错误消息对象
     * @return
     */
    public static Message createSuccessMessage() {
        return  new Message(VALUE_STATUS_SUCCESS);
    }

    /**
     * 获取含有短信验证码的消息
     * @param smsCode 验证码
     * @return
     */
    public static Message createSMSMessage(String smsCode) {
        Message message = createSuccessMessage(VALUE_CODE_SMS_SEND_YES,VALUE_CONTENT_SMS_SEND_YES);
        message.put(KEY_SMS_CODE,smsCode);
        return message;
    }

    /**
     * 创建未知错误消息
     * @return
     */
    public static Message createERRMessage() {
        return createFailMessage(VALUE_CODE_ERR,VALUE_CONTENT_ERR);
    }

}
