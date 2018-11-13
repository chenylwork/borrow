package com.work.borrow.util;

/**
 * 消息键值对信息
 */
public interface MessageCode {
    // 消息编号信息
    String VALUE_CODE_ERR = "500000"; // 消息编号：未知错误异常
    String VALUE_CODE_USER_LOGIN_YES = "112000"; // 消息编号：登录成功
    String VALUE_CODE_ACCOUNT_CHK_NO = "112001"; // 消息编号：账户不存在
    String VALUE_CODE_ACCOUNT_CHK_YES = "112002"; // 消息编号：账户存在
    String VALUE_CODE_ACCOUNT_ADD_YES = "112003"; // 消息内容：账户注册成功
    String VALUE_CODE_ACCOUNT_ADD_NO = "112004"; // 消息内容：账户注册失败
    String VALUE_CODE_PASS_CHK_NO = "112005"; // 消息编号：密码错误
    String VALUE_CODE_PASS_CHK_YES = "112006"; // 消息编号：密码正确
    String VALUE_CODE_PASS_CHG_NO = "112007"; // 消息编号：密码修改失败
    String VALUE_CODE_PASS_CHG_YES = "112008"; // 消息编号：密码修改成功
    String VALUE_CODE_SMS_SEND_NO = "112009"; // 消息编号：验证码发送失败
    String VALUE_CODE_SMS_SEND_YES = "112010"; // 消息编号：验证码发送成功
    String VALUE_CODE_SMS_EXISTS_NO = "112011"; // 消息编号：验证码不存在
    String VALUE_CODE_SMS_CHK_YES = "112012"; // 消息编号：验证码正确
    String VALUE_CODE_SMS_CHK_NO = "112013"; // 消息编号：验证码错误
    String VALUE_CODE_ACCOUNT_VALIDATION_Y = "112014"; // 消息编号：实名认证成功
    String VALUE_CODE_ACCOUNT_VALIDATION_N = "112027"; // 消息编号：实名认证失败
    String VALUE_CODE_ACCOUNT_PID_SAVE_Y = "112015"; // 身份证图片上传成功
    String VALUE_CODE_ACCOUNT_PID_SAVE_N = "112016"; // 身份证图片上传失败
    String VALUE_CODE_ACCOUNT_PID_SAVE_UP_E = "12017"; // 没有提交身份证正面照
    String VALUE_CODE_ACCOUNT_PID_SAVE_DOWN_E = "12018"; // 没有提交身份证反面照
    String VALUE_CODE_ORDER_ADD_Y = "12019"; // 借款订单添加成功
    String VALUE_CODE_ORDER_ADD_N = "12020"; // 借款订单添加失败
    String VALUE_CODE_ORDER_STATUS_UPDATE_Y = "12021"; // 订单状态修改成功
    String VALUE_CODE_ORDER_STATUS_UPDATE_N = "12022"; // 订单状态修改失败
    String VALUE_CODE_ORDER_MONEY_UPDATE_Y = "12023"; // 订单还款成功
    String VALUE_CODE_ORDER_MONEY_UPDATE_N = "12024"; // 订单还款失败
    String VALUE_CODE_ORDER_QUERY_Y = "12025"; // 订单查询成功
    String VALUE_CODE_ORDER_QUERY_N = "12026"; // 订单查询失败
    String VALUE_CODE_SMS_SEND_MOBILE_N = "12029"; // 手机号为空
    String VALUE_CODE_ACCOUNT_FAST_SET_Y = "12031"; // 开启快速审核成功
    String VALUE_CODE_ACCOUNT_FAST_SET_N = "12032"; // 开启快速审核失败
    String VALUE_CODE_ACCOUNT_INFO_SEARCH_Y = "12040"; // 用户信息查询成功
    String VALUE_CODE_ACCOUNT_INFO_SEARCH_N = "12041"; // 用户信息查询失败

    String VALUE_CODE_UPLOAD_FILE_N = "12050"; // 上传的文件为null
    String VALUE_CODE_ACCOUNT_INFO_INPUT_Y = "12060"; // 用户认证信息录入成功
    String VALUE_CODE_ACCOUNT_INFO_INPUT_N = "12061"; //用户认证信息录入失败

}
