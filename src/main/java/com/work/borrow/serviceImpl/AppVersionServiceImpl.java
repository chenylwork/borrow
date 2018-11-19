package com.work.borrow.serviceImpl;

import com.work.borrow.mapper.AppVersionMapper;
import com.work.borrow.po.AppVersion;
import com.work.borrow.po.Message;
import com.work.borrow.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {
    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public Message inputAppVersion(AppVersion appVersion) {
        Message message = null;
        boolean inputVersion = appVersionMapper.inputVersion(appVersion);
        if (inputVersion) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_INPUT_Y,Message.VALUE_CONTENT_INPUT_Y);
        } else {
            message = Message.createSuccessMessage(Message.VALUE_CODE_INPUT_N,Message.VALUE_CONTENT_INPUT_N);
        }
        return message;
    }

    @Override
    public Message queryAppVersion(String type) {
        Message message = null;
        List<AppVersion> appVersions = appVersionMapper.queryVersion(type);
        if (appVersions != null && !appVersions.isEmpty()) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_Y,Message.VALUE_CONTENT_QUERY_Y);
            message.put(Message.KEY_DATA,appVersions);
        } else {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_N,Message.VALUE_CONTENT_QUERY_N);
        }
        return message;
    }

    @Override
    public Message queryMaxVersion(String type) {
        Message message = null;
        AppVersion appVersion = appVersionMapper.queryMaxVersion(type);
        if (appVersion != null) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_Y,Message.VALUE_CONTENT_QUERY_Y);
            message.put(Message.KEY_DATA,appVersion);
        } else {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_N,Message.VALUE_CONTENT_QUERY_N);
        }
        return message;
    }
}
