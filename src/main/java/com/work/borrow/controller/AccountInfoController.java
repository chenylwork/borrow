package com.work.borrow.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.LinkMan;
import com.work.borrow.po.Message;
import com.work.borrow.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/account/info")
public class AccountInfoController {

    @Autowired
    private DataService dataService;

    /**
     * 录入银行卡信息
     * @return
     */
    @RequestMapping("/input/card")
    public Message inputCard(AccountInfo accountInfo) {
        return dataService.inputAccountInfo(accountInfo);
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
     * @param map 紧急联系人数组
     * @return
     */
    @RequestMapping("/input/linkman")
    public Message inputLinkMan(@RequestBody Map<String,Object> map) throws Exception {
        String account = (String)map.get("account");
        Object linkMans = map.get("linkMans");
        ObjectMapper mapper = new ObjectMapper();
        JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, LinkMan.class);
        List<LinkMan> linkManList = mapper.readValue(mapper.writeValueAsString(linkMans), jt);
        return dataService.inputLinkmanArray(linkManList,account);
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nowDate = simpleDateFormat.format(new Date()).toString();
        accountInfo.setStartTime(nowDate);
        accountInfo.setStatus(AccountMapper.STATUS_WRIT);// 订单生效，开始审核
        return dataService.inputAccountInfo(accountInfo);
    }

    /**
     * 查询用户详细信息
     * @param accountInfo
     * @return
     */
    @RequestMapping("/search")
    public Message searchAccountInfo(AccountInfo accountInfo) {
        return dataService.searchAccountInfo(accountInfo);
    }

    /**
     * 查询订单信息
     * @param accountInfo
     * @return
     */
    @RequestMapping("/search/account/order")
    public Message searchAccountOrder(AccountInfo accountInfo) {
        return null;
    }

}
