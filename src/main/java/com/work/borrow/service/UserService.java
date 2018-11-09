package com.work.borrow.service;

import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.Message;

/**
 * 用户操作业务类
 */
public interface UserService {

    /**
     * 检验用户是否存在
     * @param mobile 账户信息
     * @return 返回操作结果信息
     */
    public Message checkAccount(String mobile);

    /**
     * 修改密码
     * @param account 账户信息
     * @return 返回操作结果信息
     */
    public Message changePass(Account account);

    /**
     * 密码登录
     * @param account 账户信息
     * @return 操作信息结果
     */
    public Message loginPass(Account account);

    /**
     * 验证码登录
     * @param mobile 手机号
     * @param smsCode 短信验证码
     * @return
     */
    public Message loginSMS(String mobile,String smsCode);

    /**
     * 添加新用户
     * @param account 账户信息
     * @return 操作结果信息
     */
    public Message register(Account account,String smsCode);

    /**
     * 实名认证用户
     * @param account 用户信息
     * @return
     */
    public Message realAccount(AccountInfo account);

    /**
     * 获取实名认证信息
     * @param mobile 手机号
     * @return
     */
    public Message getAccountInfo(String mobile);

}
