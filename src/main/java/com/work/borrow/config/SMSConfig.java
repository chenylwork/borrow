package com.work.borrow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/sms.properties")
public class SMSConfig {
    @Value("${sms.root.userID}")
    private String userID; // 企业账号
    @Value("${sms.root.account}")
    private String account; // 发送消息的账号
    @Value("${sms.root.password}")
    private String password; // 发送消息的账号密码

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
