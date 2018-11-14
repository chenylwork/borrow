package com.work.borrow.po;

/**
 * 身份证信息
 */
public class Pid {
    private String address; // 住址
    private String birth; // 出生日期
    private String name; // 姓名
    private String code; // 身份证号
    private String sex; // 性别
    private String nation; // 民族

    public Pid() {
    }

    public Pid(String address, String birth, String name, String code, String sex, String nation) {
        this.address = address;
        this.birth = birth;
        this.name = name;
        this.code = code;
        this.sex = sex;
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Override
    public String toString() {
        return "Pid{" +
                "address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                '}';
    }
}
