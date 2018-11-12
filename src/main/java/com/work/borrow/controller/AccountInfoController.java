package com.work.borrow.controller;

import com.work.borrow.po.LinkMan;
import com.work.borrow.po.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 账户信息controller
 */
@RestController
@RequestMapping("/account/info")
public class AccountInfoController {

    /**
     * 录入银行卡信息
     * @return
     */
    @RequestMapping("/input/card")
    public Message inpuCard() {
        return null;
    }

    /**
     * 录入工作信息
     * @return
     */
    @RequestMapping("/input/work")
    public Message inputWork() {
        return null;
    }

    /**
     * 录入紧急联系人
     * @param linkManArray 紧急联系人数组
     * @return
     */
    @RequestMapping("/input/linkman")
    public Message inputLinkMan(LinkMan[] linkManArray) {
        return null;
    }

    /**
     * 上传身份证正反面照片
     * @param account 用户账户手机号
     * @param up 身份证正面照
     * @param down 身份证反面照
     * @return
     */
    @RequestMapping("/upload/pid")
    public Message uploadPid(String account,MultipartFile up,MultipartFile down) {
        return null;
    }

}
