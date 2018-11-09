package com.work.borrow.po;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 借款订单对象
 */
public class Order {
    @JsonProperty("orderID")
    private int id; // 编号
    private String account; // 账号：用户手机号
    private String money; // 借款金额
    private int life; // 借款期限:单位月
    private String startTime; // 借款时间
    private String status; // 还款状态

    public Order(){

    }

    public Order(String account, String status) {
        this.account = account;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
