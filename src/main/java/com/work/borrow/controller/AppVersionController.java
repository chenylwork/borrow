package com.work.borrow.controller;

import com.work.borrow.po.AppVersion;
import com.work.borrow.po.Message;
import com.work.borrow.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class AppVersionController {

    private static final String APK_TYPE_ANDROID = "android";
    private static final String APK_TYPE_IOS= "IOS";

    @Autowired
    private AppVersionService appVersionService;

    private String getType(String type) {
        if (type.equals("0")) {
           return APK_TYPE_ANDROID;
        } else {
            return APK_TYPE_IOS;
        }
    }

    /**
     * 录入app版本信息
     * @param appVersion
     * @return
     */
    @RequestMapping("/app/input/{type}")
    public Message inputAppVersion(AppVersion appVersion,@PathVariable("type") String type){
        appVersion.setType(getType(type));
        return appVersionService.inputAppVersion(appVersion);
    }

    /**
     * 查询app版本信息
     * @return
     */
    @RequestMapping("/app/query/{type}")
    public Message queryAppVersion(@PathVariable("type") String type){
        return appVersionService.queryAppVersion(getType(type));
    }

    /**
     * 获取最大版本信息
     * @return
     */
    @RequestMapping("/app/query/max/{type}")
    public Message queryMaxAppVersion(@PathVariable("type") String type) {
        return appVersionService.queryMaxVersion(getType(type));
    }

}
