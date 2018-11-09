package com.work.borrow.po;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    // 用户信息
    @JsonProperty("accountID")
    private int id; // 用户编号
    private String mobile; // 手机号
    private String password; // 密码
    private boolean isAdmin;


    public Account() {
        super();
    }

    public Account(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
