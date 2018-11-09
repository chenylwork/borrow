package com.work.borrow.util;

/**
 * 消息内容配置
 */
public interface Message extends MessageCode,MessageContent{
    // 消息键信息
    String KEY_STATUS = "status"; // 消息状态
    String KEY_CONTENT = "content"; // 消息内容
    String KEY_CODE = "code"; // 消息编号
    String KEY_SMS_CODE = "smsCode"; // 短信验证码
    String KEY_DATA = "data";

    // 消息值信息
    String VALUE_STATUS_SUCCESS = "1"; // 消息状态值：成功
    String VALUE_STATUS_FAIL = "0";  // 消息状态值：失败
}
