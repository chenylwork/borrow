package com.work.borrow.service;

import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.LinkMan;
import com.work.borrow.po.Message;
import com.work.borrow.util.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 数据操作接口
 */
public interface DataService {

    String PID_IMG_UP = "up"; // 身份证正面照后缀
    String PID_IMG_DOWN = "down"; // 身份证背面照后缀
    String PID_IMG_HAND = "hand"; // 身份证手持照后缀


    /**
     * 上传身份证正面照并识别证件信息
     * @param upFile 身份证正面
     * @param mobile 用户手机号
     * @return
     */
    public Message uploadPidUpImg(MultipartFile upFile,String mobile);

    /**
     * 上传身份证正反面照片
     * @param upFile 正面照
     * @param downFile 反面照
     * @param accountInfo 用户手机号
     * @return
     */
    public Message uploadPidImg(MultipartFile upFile,MultipartFile downFile,AccountInfo accountInfo);

    /**
     * 上传身份证手持照
     * @param pidHand 手持照MultipartFile对象
     * @param mobile 用户手机号
     * @return
     */
    public Message uploadPidHand(MultipartFile pidHand,String mobile);

    /**
     * 设置快速审核
     * @param mobile
     * @return
     */
    public Message setFast(String mobile,String id);

//    /**
//     * 查询用户详情信息
//     * @param accountInfo 查询条件
//     * @return
//     */
//    public Message getAccountInfo(AccountInfo accountInfo,Page<AccountInfo> page);

    /**
     * 录入工作信息
     * @param accountInfo
     * @return
     */
    Message inputAccountWorkInfo(AccountInfo accountInfo);

    /**
     * 录入用户联系人信息
     * @param linkManArray 联系人数组
     * @param account 账户手机号
     * @return
     */
    Message inputLinkmanArray(List<LinkMan> linkManArray, String account);
    /**
     * 录入银行卡信息
     * @param accountInfo
     * @return
     */
    Message inputAccountCard(AccountInfo accountInfo);

    /**
     * 录入账户信息
     * @param accountInfo
     * @return
     */
    Message inputAccountInfo(AccountInfo accountInfo);

    /**
     * 查询用户详细信息
     * @param accountInfo
     * @return
     */
    Message searchAccountInfo(AccountInfo accountInfo,Page<AccountInfo> page);

}
