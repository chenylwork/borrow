package com.work.borrow.util;

import com.work.borrow.config.SMSConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSUtils {
    private static final Logger logger = LoggerFactory.getLogger(SMSUtils.class);
    /**
     * 企业ID
     */
    private static final String USERID = "16127";
    /**
     * 用户
     */
    private static final String ACCOUNT = "wangyu";
    /**
     * 密码
     */
    private static final String PASSWORD = "123456";


    /**
     * 短信第三方接口
     */
    private static final String URL_PATH = "http://www.qf106.com/sms.aspx";

    /**
     * 发送消息
     * @param mobile 需要发送的手机号
     * @param content 发送的内容
     * @param character 内容编码
     *
     */
    public static boolean send(String mobile,String content,String character) {
        String url = initURL(mobile, content, character);
        String responseXML = sendPost(url, "UTF-8");
        String isOk = XMlUtils.getChildNodeValue(responseXML, "returnstatus");
        return  (isOk != null && isOk.equals("Success"));
    }
    public static boolean send(String mobile,String content) {
        return send(mobile,content,"UTF-8");
    }

    /**
     * 组装发送的url
     * @param mobile
     * @param content
     * @param character
     * action=send&userid=12&account=账号&password=密码&mobile=15023239810,13527576163&content=内容&
     * sendTime=&taskName=本次任务描述&checkcontent=1&mobilenumber=10&countnumber=12&telephonenumber=2
     * @return
     */

    private static String initURL(String mobile,String content,String character) {
        String sendContent = "您的验证码："+content+"。\n如非本人操作请忽略本条信息";
        StringBuffer stringBuffer = new StringBuffer(URL_PATH+"?action=send");
        stringBuffer.append("&userid="+USERID);
        stringBuffer.append("&account="+ACCOUNT);
        stringBuffer.append("&password="+PASSWORD);
        stringBuffer.append("&mobile="+mobile);
        try {
            stringBuffer.append("&content=").append(URLEncoder.encode(sendContent,character));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        stringBuffer.append("&sendTime=");
        stringBuffer.append("&taskName=smscode");
        stringBuffer.append("&checkcontent=0");

        if (mobile!=null && !mobile.equals("") ){
            String[] mobiles = mobile.split(",");
            stringBuffer.append("&mobilenumber="+mobiles.length);
            stringBuffer.append("&countnumber="+mobiles.length);
        }
        stringBuffer.append("&telephonenumber=0");
        return stringBuffer.toString();
    }
    /**
     * 发送短信消息
     * @param sendUrl
     * @param outEncoding
     * @return
     */
    private static String sendPost(String sendUrl, String outEncoding) {
        String retMsg = "";
        BufferedReader reader = null;
        logger.info("请求地址："+sendUrl);
        try {
            URL url = new URL(sendUrl);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            // 发送域信息
            OutputStreamWriter out = new OutputStreamWriter(connection
                    .getOutputStream(), outEncoding);
            out.flush();
            out.close();
            // 获取返回数据
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            retMsg = buffer.toString();
            logger.info(retMsg);
        } catch (Exception e) {
            e.printStackTrace();
            retMsg = "reuid_error";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retMsg.trim();
    }
}
