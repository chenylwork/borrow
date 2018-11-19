package com.work.borrow.po;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * App版本信息
 */
public class AppVersion {
    @JsonProperty("versionID")
    private int id; //
    private String version;// 版本号
    private String content; // 升级信息
    private String url; // apk下载地址
    private String time; // 更新时间
    private String type; // apk类型Android 或者ISO

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
