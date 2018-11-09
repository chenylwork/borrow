package com.work.borrow.service;

import com.work.borrow.po.Message;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据操作接口
 */
public interface DataService {

    String PID_IMG_UP = "up";
    String PID_IMG_HAND = "hand";


    /**
     * 上传身份证正面照
     * @param upFile 身份证正面
     * @param mobile 用户手机号
     * @return
     */
    public Message uploadPidImg(MultipartFile upFile,String mobile);

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

}
