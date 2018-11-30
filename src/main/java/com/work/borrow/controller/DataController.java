package com.work.borrow.controller;

import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.Message;
import com.work.borrow.service.DataService;
import com.work.borrow.util.FileUtils;
import com.work.borrow.util.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Properties;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DataController {
    @Autowired
    private DataService dataService;

    private static final String SERVICES_QQ = "services.qq";
    @Value("${borrow.service.qq.path}")
    private String SERVICES_QQ_PATH;
    @Value("${pid.img.file.save.path}")
    private String IMG_PATH;

    /**
     * 上传管理员文件
     * @param file
     * @return
     */
    @RequestMapping("/upload/admin/img/{name}")
    public Message uploadAdminQRImg(MultipartFile file,@PathVariable("name") String name) {
        String fileName = "";
        if ("ali".equals(name)) fileName = "ali_pay_QR";
        if ("wx".equals(name)) fileName = "wx_pay_QR";
        if ("kf".equals(name)) fileName = "services_QR";
        try {
            file.transferTo(new File(IMG_PATH+fileName+".jpg"));
            return Message.createSuccessMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Message.createERRMessage();
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

    @RequestMapping("/input/service")
    public Message inputService(String qq) {
        Message message = null;
        Properties prop = new Properties();
        try {
            File file = FileUtils.createFile(SERVICES_QQ_PATH);
            if (!file.exists())
                file.createNewFile();
            InputStream fis = new FileInputStream(file);
            prop.load(fis);
            //一定要在修改值之前关闭fis
            fis.close();
            OutputStream fos = new FileOutputStream(SERVICES_QQ_PATH);
            prop.setProperty(SERVICES_QQ, qq);
            //保存，并加入注释
            prop.store(fos, "Update '" + SERVICES_QQ + "' value");
            fos.close();
            message = Message.createSuccessMessage();
        } catch (IOException e) {
            System.err.println("Visit " + SERVICES_QQ_PATH + " for updating " + qq + " value error");
            message = Message.createFailMessage();
            message.put(Message.KEY_CONTENT,"客服QQ修改失败！！！");
            e.fillInStackTrace();
        }
        return message;
    }
    @RequestMapping("/get/service")
    public String getService() {
        String qq = "";
        Properties pro = new Properties();
        File file = FileUtils.createFile(SERVICES_QQ_PATH);
        System.out.println(SERVICES_QQ_PATH);
        try {
            if (!file.exists()) file.createNewFile();
            pro.load(new FileInputStream(file));
            qq = pro.getProperty(SERVICES_QQ);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return qq;
    }



}
