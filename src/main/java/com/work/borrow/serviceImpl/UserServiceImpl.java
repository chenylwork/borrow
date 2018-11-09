package com.work.borrow.serviceImpl;

import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.Message;
import com.work.borrow.service.SMSService;
import com.work.borrow.service.UserService;
import com.work.borrow.util.CharUtils;
import com.work.borrow.util.MessageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户操作实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SMSService smsService;

    @Override
    public Message checkAccount(String mobile) {
        Message message = null;
        boolean isHas = accountMapper.checkAccountByMobile(mobile);
        if(isHas) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_CHK_YES,Message.VALUE_CONTENT_ACCOUNT_CHK_YES);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_CHK_NO,Message.VALUE_CONTENT_ACCOUNT_CHK_NO);
        }
        return message;
    }

    @Override
    public Message changePass(Account account) {
        Message message = null;
        message = checkAccount(account.getMobile());
        // 检查用户信息是否存在
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) {
          return message;
        }
        // 密码加密
        account.setPassword(CharUtils.charMD5Hex(account.getPassword()));
        // 修改密码
        boolean change = accountMapper.changePass(account);
        if (change) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_PASS_CHG_YES,Message.VALUE_CONTENT_PASS_CHG_YES);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_PASS_CHG_NO,Message.VALUE_CONTENT_PASS_CHG_NO);
        }

        return message;
    }


    @Override
    public Message loginSMS(String mobile, String smsCode) {
        Message message = null;
        message = checkAccount(mobile);
        // 检查用户信息
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) {
            return message;
        }
        // 检查验证码
        message = smsService.checkSMSCode(mobile, smsCode);
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_SUCCESS)) {
            // 修改为登录成功编号
            message.put(Message.KEY_CODE,Message.VALUE_CODE_USER_LOGIN_YES);
            message.put(Message.KEY_CONTENT,Message.VALUE_CONTENT_USER_LOGIN_YES);
        }
        return message;
    }

    @Override
    public Message loginPass(Account account) {
        Message message = null;
        message = checkAccount(account.getMobile());
        // 检查用户信息
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) {
            return message;
        }
        // 密码加密
        account.setPassword(CharUtils.charMD5Hex(account.getPassword()));
        // 检查密码
        boolean passIsOk = accountMapper.checkAccountByPass(account);
        if (passIsOk) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_USER_LOGIN_YES,Message.VALUE_CONTENT_USER_LOGIN_YES);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_PASS_CHK_NO,Message.VALUE_CONTENT_PASS_CHK_NO);
        }
        return message;
    }

    @Override
    public Message register(Account account,String smsCode) {
        Message message = smsService.checkSMSCode(account.getMobile(), smsCode);
        // 检查验证码
        if (message.get(Message.KEY_STATUS).equals(Message.VALUE_STATUS_FAIL)) {
            return message;
        }
        boolean isHas = accountMapper.checkAccountByMobile(account.getMobile());
        if (isHas) {
            return message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_CHK_YES,Message.VALUE_CONTENT_ACCOUNT_CHK_YES);
        }
        // 密码加密
        account.setPassword(CharUtils.charMD5Hex(account.getPassword()));
        // 注册用户
        boolean add = accountMapper.addAccount(account);
        if (add) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_ADD_YES,Message.VALUE_CONTENT_ACCOUNT_ADD_YES);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_ADD_NO,Message.VALUE_CONTENT_ACCOUNT_ADD_NO);
        }
        return message;
    }

    @Override
    public Message realAccount(AccountInfo account) {
        Message message = null;
        // 账户信息验证
        // 添加账户信息
        boolean addInfo = accountMapper.addInfo(account);
        if (addInfo) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ACCOUNT_VALIDATION_Y,Message.VALUE_CONTENT_ACCOUNT_VALIDATION_Y);
            message.put("infoID",account.getId());
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_ACCOUNT_VALIDATION_N,Message.VALUE_CONTENT_ACCOUNT_VALIDATION_N);
        }
        return message;
    }

    @Override
    public Message getAccountInfo(String mobile) {
        Message message = null;
        AccountInfo accountInfo = accountMapper.getUseAccountByMobile(mobile);
        if(accountInfo != null && accountInfo.getId() != null ) {
            message = Message.createSuccessMessage();
            message.put(Message.KEY_DATA,accountInfo);
        } else {
            message = Message.createFailMessage();
        }
        return message;
    }
}
