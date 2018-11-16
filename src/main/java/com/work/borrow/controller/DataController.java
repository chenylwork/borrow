package com.work.borrow.controller;

import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.Message;
import com.work.borrow.service.DataService;
import com.work.borrow.util.FileUtils;
import com.work.borrow.util.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class DataController {
    @Autowired
    private DataService dataService;

    /**
     * 上传管理员文件
     * @param file
     * @return
     */
    @RequestMapping("/upload/admin/img/{name}")
    public Message uploadAdminQRImg(MultipartFile file,@Param("name") String name) {
        String fileName = "";
        if ("ali".equals(name)) fileName = "";
        if ("wx".equals(name)) fileName = "";
        if ("kf".equals(name)) fileName = "";
        try {
            File imgFile = FileUtils.getResourceLoader().getResource("classpath:/static/img/"+fileName+".jpg").getFile();
            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream(imgFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传身份证正反面图片
     * @param up 身份证正面
     * @param mobile 用户手机号
     * @return
     */
    //@RequestMapping("/upload/pid/up")
    public Message uploadPidImg(MultipartFile up,String mobile) {
        return dataService.uploadPidUpImg(up,mobile);
    }

    /**
     * 上传身份证手持照
     * @param file 上传文件
     * @param mobile 操作用户
     * @return
     */
    @RequestMapping("/upload/pid/hand")
    public Message uploadPidHand(MultipartFile file,String mobile) {
        return dataService.uploadPidHand(file,mobile);
    }

    /**
     * 设置快速审核
     * @param mobile
     * @return
     */
    @RequestMapping("/account/check/fast")
    public Message changeFast(String mobile,String info){return dataService.setFast(mobile, info);}

}
