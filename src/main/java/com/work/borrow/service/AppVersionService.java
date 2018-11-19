package com.work.borrow.service;

import com.work.borrow.po.AppVersion;
import com.work.borrow.po.Message;

/**
 * 版本信息操作接口
 */
public interface AppVersionService {

    /**
     * 录入app版本信息
     * @param appVersion
     * @return
     */
    public Message inputAppVersion(AppVersion appVersion);

    /**
     * 查询app版本信息
     * @return
     */
    public Message queryAppVersion(String type);

    /**
     * 获取最高版本信息
     * @return
     */
    public Message queryMaxVersion(String type);
}
