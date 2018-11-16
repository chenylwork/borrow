package com.work.borrow.util;

public interface MessageContent {

    // 消息编号信息
    String VALUE_CONTENT_ERR = "未知错误,错误信息已发送管理员解决。"; // 消息内容：位置错误
    String VALUE_CONTENT_USER_LOGIN_YES = "登录成功"; // 消息编号：登录成功
    String VALUE_CONTENT_ACCOUNT_CHK_NO = "账号不存在"; // 消息编号：账户不存在
    String VALUE_CONTENT_ACCOUNT_CHK_YES = "账户已存在"; // 消息编号：账户存在
    String VALUE_CONTENT_ACCOUNT_ADD_YES = "账户注册成功"; // 消息内容：账户注册成功
    String VALUE_CONTENT_ACCOUNT_ADD_NO = "账户注册失败"; // 消息内容：账户注册失败
    String VALUE_CONTENT_PASS_CHK_NO = "密码验证错误"; // 消息编号：密码错误
    String VALUE_CONTENT_PASS_CHK_YES = "密码验证成功"; // 消息编号：密码正确
    String VALUE_CONTENT_PASS_CHG_NO = "密码修改失败"; // 消息编号：密码修改失败
    String VALUE_CONTENT_PASS_CHG_YES = "密码修改成功"; // 消息编号：密码修改成功
    String VALUE_CONTENT_SMS_SEND_NO = "验证码发送失败"; // 消息编号：验证码发送失败
    String VALUE_CONTENT_SMS_SEND_YES = "验证码发送成功"; // 消息编号：验证码发送成功
    String VALUE_CONTENT_SMS_EXISTS_NO = "请先获取验证码"; // 消息内容：没有对应的验证码
    String VALUE_CONTENT_SMS_CHK_YES = "验证码验证成功"; // 消息编号：验证码正确
    String VALUE_CONTENT_SMS_CHK_NO = "验证码验证失败"; // 消息编号：验证码错误
    String VALUE_CONTENT_ACCOUNT_VALIDATION_Y = "实名认证成功";
    String VALUE_CONTENT_ACCOUNT_VALIDATION_N = "认证失败";
    String VALUE_CONTENT_ACCOUNT_PID_SAVE_Y = "身份证上传成功";
    String VALUE_CONTENT_ACCOUNT_PID_SAVE_N = "身份证上传失败";
    String VALUE_CONTENT_ACCOUNT_PID_SAVE_UP_E = "请上传身份证正面";
    String VALUE_CONTENT_ACCOUNT_PID_SAVE_DOWN_E = "请上传身份证反面";
    String VALUE_CONTENT_ORDER_ADD_Y = "借款成功";
    String VALUE_CONTENT_ORDER_ADD_N = "借款失败";
    String VALUE_CONTENT_ORDER_STATUS_UPDATE_Y = "状态修改成功";
    String VALUE_CONTENT_ORDER_STATUS_UPDATE_N = "状态修改失败";
    String VALUE_CONTENT_ORDER_MONEY_UPDATE_Y = "还款成功"; // 订单还款成功
    String VALUE_CONTENT_ORDER_MONEY_UPDATE_N = "还款失败"; // 订单还款失败
    String VALUE_CONTENT_ORDER_QUERY_Y = "查询成功"; // 订单查询成功
    String VALUE_CONTENT_ORDER_QUERY_N = "没有您需要查询的信息！！！"; // 订单查询失败
    String VALUE_CONTENT_SMS_SEND_MOBILE_N = "手机号为空";

    String VALUE_CONTENT_ACCOUNT_FAST_SET_Y = "已开启快速审核"; // 开启快速审核成功
    String VALUE_CONTENT_ACCOUNT_FAST_SET_N = "快速审核开启失败"; // 开启快速审核失败

    String VALUE_CONTENT_ACCOUNT_INFO_SEARCH_Y = "查询成功"; // 用户信息查询成功
    String VALUE_CONTENT_ACCOUNT_INFO_SEARCH_N = "没有你要查询的用户信息"; // 用户信息查询失败

    String VALUE_CONTENT_UPLOAD_FILE_N = "请选择上传文件"; //
    String VALUE_CONTENT_ACCOUNT_INFO_INPUT_Y = "信息录入成功"; //
    String VALUE_CONTENT_ACCOUNT_INFO_INPUT_N = "信息录入失败"; //
    String VALUE_CONTENT_ACCOUNT_INFO_INPUT_PID_N = "请上传正确的身份证正面照";
}
