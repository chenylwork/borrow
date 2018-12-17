package com.work.borrow.serviceImpl;

import com.work.borrow.mapper.LinkmanMapper;
import com.work.borrow.po.LinkMan;
import com.work.borrow.po.Message;
import com.work.borrow.service.LinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @ClassName LinkmanServicesImpl
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2018/12/10- 12:43
 * @Version 1.0
 **/
@Service("linkmanService")
public class LinkmanServicesImpl implements LinkmanService{
    @Autowired
    private LinkmanMapper linkmanMapper;

    @Override
    public Message getLinkman(int infoId) {
        Message message = null;
        List<LinkMan> linkman = linkmanMapper.getLinkman(infoId);
        if (linkman != null && !linkman.isEmpty()) {
            message = Message.createSuccessMessage(Message.VALUE_CODE_QUERY_Y,Message.VALUE_CONTENT_QUERY_Y);
            message.put(Message.KEY_DATA,linkman);
        } else {
            message = Message.createFailMessage(Message.VALUE_CODE_QUERY_N,Message.VALUE_CONTENT_QUERY_N);
        }
        return message;
    }
}
