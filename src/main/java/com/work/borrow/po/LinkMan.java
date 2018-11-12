package com.work.borrow.po;

/**
 * 联系人
 */
public class LinkMan {
    private int id; // 编号
    private String account; // 账号手机号
    private String mobile; // 联系人手机号
    private String name; // 联系人名称
    private String relation; // 联系人关系

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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "LinkMan{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", relation='" + relation + '\'' +
                '}';
    }
}
