package com.work.borrow.controller;

import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.Message;
import com.work.borrow.service.SMSService;
import com.work.borrow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private SMSService smsService;
    @Autowired
    private UserService userService;

    /**
     * 密码登录
     * @param
     * @return
     */
    @RequestMapping("/login/pass")
    public Message login(Account account) {
        return userService.loginPass(account);
    }

    /**
     * 验证码登录
     * @param mobile 手机号
     * @param smsCode 验证码
     * @return
     */
    @RequestMapping("/login/sms")
    public Message login(String mobile,String smsCode)  {
        return userService.loginSMS(mobile,smsCode);
    }

    /**
     * 验证码验证
     * @param mobile 手机号
     * @param code 验证码
     * @return
     */
    @RequestMapping("/sms/check")
    public Message checkSMS(String mobile,String code) {
        return smsService.checkSMSCode(mobile, code);
    }

    /**
     * 用户注册
     * @param account 需要注册的用户
     * @return
     */
    @RequestMapping("/register")
    public Message register(Account account,String smsCode) {
        return userService.register(account,smsCode);
    }

    /**
     * 发送验证码
     * @param mobile 需要发送验证码的手机号
     * @return
     */
    @RequestMapping("/sms/send")
    public Message sendSMS(String mobile) {
        return smsService.sendSMSCode(mobile);
    }

    /**
     * 用户检验，查看是否已经注册
     * @param mobile 用户信息
     * @return
     */
    @RequestMapping("/check/account")
    public Message checkAccount(String mobile) {
        return userService.checkAccount(mobile);
    }

    /**
     * 更改密码
     * @param account 账号
     * @return
     */
    @RequestMapping("/chang/pass")
    public Message changePass(Account account) {
        return userService.changePass(account);
    }

    /**
     *  认证用户信息
     * @param account 账户信息
     * @return
     */
    @RequestMapping("/real/account")
    public Message realAccount(AccountInfo account) {
        return userService.realAccount(account);
    }

    @RequestMapping("/search/account/info")
    public Message getInfo(String mobile) {
        return userService.getAccountInfo(mobile);
    }

}
