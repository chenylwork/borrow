package com.work.borrow.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.borrow.annotation.Rid;
import com.work.borrow.annotation.Table;
import com.work.borrow.mapper.AccountMapper;
import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;

/**
 * 账户详细信息
 */
@Table("account_info")
public class AccountInfo {
    @JsonProperty("infoID")
    private Integer id; // 用户编号
    private String account; // 手机号
    // 身份证信息
    private String name; // 姓名
    private String sex; // 性别
    private String pid; // 身份证号
    private String address; // 户口所在地

    private String workName; // 工作公司名称
    private String workPhone; // 工作公司电话
    private String workAddress; // 工作公司地址
    private String workTime; // 工作年限

    private String income; // 年收入
    private String cardCode; // 银行卡号
    private String cardHolder; // 银行卡持有人

    private String pidUp; // 身份证正面照
    private String pidDown; // 身份证反面照

    private String borrow; // 借款金额
    private String payment; // 付款方式
    private String paymentCode; // 付款单号
    private String life; // 借款期限
    private String startTime; // 开始时间
    private String status; // 信息状态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
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

    public String getPidUp() {
        return pidUp;
    }

    public void setPidUp(String pidUp) {
        this.pidUp = pidUp;
    }

    public String getPidDown() {
        return pidDown;
    }

    public void setPidDown(String pidDown) {
        this.pidDown = pidDown;
    }

    public String getBorrow() {
        return borrow;
    }

    public void setBorrow(String borrow) {
        this.borrow = borrow;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", pid='" + pid + '\'' +
                ", address='" + address + '\'' +
                ", workName='" + workName + '\'' +
                ", workPhone='" + workPhone + '\'' +
                ", workAddress='" + workAddress + '\'' +
                ", workTime='" + workTime + '\'' +
                ", income='" + income + '\'' +
                ", cardCode='" + cardCode + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", pidUp='" + pidUp + '\'' +
                ", pidDown='" + pidDown + '\'' +
                ", borrow='" + borrow + '\'' +
                ", payment='" + payment + '\'' +
                ", paymentCode='" + paymentCode + '\'' +
                ", life='" + life + '\'' +
                ", startTime='" + startTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
