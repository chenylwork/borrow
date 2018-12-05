package com.work.borrow.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.LinkMan;
import com.work.borrow.po.Message;
import com.work.borrow.service.DataService;
import com.work.borrow.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账户信息controller
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/account/info")
public class AccountInfoController {

    @Autowired
    private DataService dataService;

    @RequestMapping("/input/any")
    public Message input(AccountInfo accountInfo) {
        return dataService.inputAccountInfo(accountInfo);
    }

    /**
     * 上传身份证正反面照片
     * @param account 用户账户手机号
     * @param up 身份证正面照
     * @param down 身份证反面照
     * @return
     */
    @RequestMapping("/upload/pid")
    public Message uploadPid(AccountInfo account, MultipartFile up, MultipartFile down) {
        return dataService.uploadPidImg(up,down,account);
    }


    /**
     * 录入工作信息
     * @return
     */
    @RequestMapping("/input/work")
    public Message inputWork(AccountInfo accountInfo) {
        return dataService.inputAccountInfo(accountInfo);
    }

    /**
     * 录入紧急联系人
     * @param account 用户手机号
     * @param info 联系人信息数组字符串
     * @return
     */
    @RequestMapping("/input/linkman")
    public Message inputLinkMan(String account,String info) throws Exception {
        List<LinkMan> linkManList = new ArrayList<>();
        String[] linkMans = info.split(";");
        for (String linkManBuffer : linkMans) {
            LinkMan linkMan = new LinkMan();
            String[] params = linkManBuffer.split(",");
            linkMan.setMobile(params[0]);
            linkMan.setName(params[1]);
            linkMan.setRelation(params[2]);
            linkManList.add(linkMan);
        }
        return dataService.inputLinkmanArray(linkManList,account);
    }

    /**
     * 录入银行卡信息
     * @return
     */
    @RequestMapping("/input/card")
    public Message inputCard(AccountInfo accountInfo) {
        accountInfo.setStatus(AccountMapper.STATUS_WRIT); // 等待审核
        return dataService.inputAccountInfo(accountInfo);
    }




    /**
     * 录入借款金额
     * @param accountInfo
     * @return
     */
    @RequestMapping("/input/borrow")
    public Message inputAccountBorrow(AccountInfo accountInfo) {
        return dataService.inputAccountInfo(accountInfo);
    }

    /**
     * 录入付款方式，和付款单号
     * @param accountInfo
     * @return
     */
    @RequestMapping("/input/payment")
    public Message inputAccountPayment(AccountInfo accountInfo) {
        accountInfo.setStatus(AccountMapper.STATUS_BEFORE);// 用户付款，等待放款
        return dataService.inputAccountInfo(accountInfo);
    }

    /**
     * 修改借款信息状态码
     * @param accountInfo
     * @return
     */
    @RequestMapping("/input/status")
    public Message inputStatus(AccountInfo accountInfo) {
        return dataService.inputStatus(accountInfo);
    }

    /**
     * 查询用户详细信息
     * @param accountInfo
     * @return
     */
    @RequestMapping("/search")
    public Message searchAccountInfo(AccountInfo accountInfo, Page<AccountInfo> page,
                                     @RequestParam(value = "use",defaultValue = "0") String use) {
        int unShowErrorInfo = 0;
        if (use != null && !use.equals("")) {
            unShowErrorInfo = Integer.valueOf(use);
        }
        return dataService.searchAccountInfo(accountInfo,page,unShowErrorInfo);
    }

    /**
     * 获取当前正在使用的借款信息
     * @param account
     * @return
     */
    @RequestMapping("/search/use")
    public Message searchNowUseInfo(String account) {
        return dataService.getNowUseInfo(account);
    }
    /**
     * 获取未完成的借款信息
     * @param account
     * @return
     */
    @RequestMapping("/search/un")
    public Message getUnFinishInfoStatus(String account){
        return dataService.getUnOkInfo(account);
    }

    @RequestMapping("/search/finash/n")
    public Message getUnFinishInfo(AccountInfo account) {
        return dataService.getUnFinish(account);
    }
    /**
     * 查询订单信息
     * @param accountInfo
     * @return
     */
    @RequestMapping("/search/order")
    public Message searchAccountOrder(AccountInfo accountInfo) {
        return null;
    }

}
