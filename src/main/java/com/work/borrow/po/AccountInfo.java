package com.work.borrow.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.borrow.mapper.AccountMapper;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;

/**
 * 账户详细信息
 */
public class AccountInfo {
    @JsonProperty("infoID")
    private Integer id; // 用户编号
    private String mobile; // 手机号
    private String name; // 用户真实名称
    private String sex; // 性别
    private String pid; // 身份证号
    private String house; // 户籍
    private String live; // 居住地
    private String married; // 婚姻状况：未婚，已婚
    private String loan; // 是否有房贷：无房贷，有房有贷，有房无贷
    private String income; // 税后年收入：
    private String workTime; // 工作年限：
    // 单位信息
    private String companyName; // 公司名称
    private String companyPhone; // 公司电话
    private String companyAddress; // 公司地址
    // 紧急联系人信息
    private String contactName; // 紧急联系人姓名
    private String contactPhone; // 紧急联系人电话
    private String contactRelation; // 紧急联系人关系
    // 身份证照片信息
    @JsonIgnore
    private String pidUp; // 身份证正面照
    @JsonIgnore
    private String pidHand; // 身份证手持照
    // 银行卡信息
    private String cardCode; // 银行卡号
    private String cardHolder; // 银行卡持有人
    private String cardBank; // 银行卡所属行
    @JsonIgnore
    private String use = AccountMapper.USER_Y; // 是否在使用中
    @JsonIgnore
    private String check = AccountMapper.CHECK_W; // 审核状态：默认为待审核
    @JsonIgnore
    private String fast = AccountMapper.FAST_N; // 是否快速审核
    private String maxMoney = "0"; // 最大借款金额
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public String getPidUp() {
        return pidUp;
    }

    public void setPidUp(String pidUp) {
        this.pidUp = pidUp;
    }

    public String getPidHand() {
        return pidHand;
    }

    public void setPidHand(String pidHand) {
        this.pidHand = pidHand;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardBank() {
        return cardBank;
    }

    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getFast() {
        return fast;
    }

    public void setFast(String fast) {
        this.fast = fast;
    }

    public String getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(String maxMoney) {
        this.maxMoney = maxMoney;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", pid='" + pid + '\'' +
                ", house='" + house + '\'' +
                ", live='" + live + '\'' +
                ", married='" + married + '\'' +
                ", loan='" + loan + '\'' +
                ", income='" + income + '\'' +
                ", workTime='" + workTime + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactRelation='" + contactRelation + '\'' +
                ", pidUp='" + pidUp + '\'' +
                ", pidHand='" + pidHand + '\'' +
                ", cardCode='" + cardCode + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardBank='" + cardBank + '\'' +
                ", use='" + use + '\'' +
                ", check='" + check + '\'' +
                ", fast='" + fast + '\'' +
                ", maxMoney='" + maxMoney + '\'' +
                '}';
    }
}
