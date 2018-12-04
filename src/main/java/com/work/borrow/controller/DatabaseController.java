package com.work.borrow.controller;

import com.work.borrow.po.Message;
import com.work.borrow.util.MySQLUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * @ClassName DatabaseController
 * @Description 作用描述
 * @Author 陈彦磊
 * @Date 2018/12/3- 13:42
 * @Version 1.0
 **/
@Controller
@RequestMapping("/database")
public class DatabaseController {
    @Value("${borrow.path}")
    private String path;
    @RequestMapping("/backup")
    public Message download(HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = simpleDateFormat.format(new Date())+".sql";
        String fileParentPath = path+"/data/"+fileName.substring(0,8);
        String filePath = fileParentPath+"/"+fileName;
        File file = new File(filePath);
        MySQLUtils.backup(filePath);
        /////////////
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

        byte[] buffer = new byte[1024];
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;

        OutputStream os = null; //输出流
        try {
            os = response.getOutputStream();
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while(i != -1){
                os.write(buffer);
                i = bis.read(buffer);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("----------file download" + fileName);
        try {
            bis.close();
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /////////////
        return null;
    }
}
