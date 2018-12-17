package com.work.borrow.controller;

import com.work.borrow.po.Message;
import com.work.borrow.service.LinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @ClassName LinkmanController
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2018/12/10- 12:47
 * @Version 1.0
 **/
@RestController
@RequestMapping("linkman")
public class LinkmanController {
    @Autowired
    private LinkmanService linkmanService;
    @RequestMapping("/get")
    public Message getLinkman(int infoId) {
        return linkmanService.getLinkman(infoId);
    }
}
