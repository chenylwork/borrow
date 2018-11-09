package com.work.borrow.service;

import com.work.borrow.po.Message;

/**
 * 短息操作业务接口
 */
public interface SMSService {

    /**
     * 发送短信验证码
     * @param mobile 需要发送到的手机号
     * @return 验证码
     */
    public Message sendSMSCode(String mobile);

    /**
     * 验证短信验证码
     * @param mobile 需要发送到的手机号
     * @param code 短信验证码
     * @return
     */
    public Message checkSMSCode(String mobile,String code);


}
