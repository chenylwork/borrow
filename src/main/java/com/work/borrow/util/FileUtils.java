package com.work.borrow.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.borrow.po.Pid;
import com.work.borrow.util.baidu.AuthService;
import com.work.borrow.util.baidu.Base64Util;
import com.work.borrow.util.baidu.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * 文件操作工具类
 */
@Component("fileUtils")
public class FileUtils {
    @Autowired
    private ResourceLoader resourceLoaderAutowired;
    
    private static ResourceLoader resourceLoader;
    
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    
    @PostConstruct
    private void init() {
        this.resourceLoader = resourceLoaderAutowired;
    }

    /**
     *
     * @param path
     * @return
     */
    public static InputStream config(String path) {
        InputStream inputStream = null;
        try {
            inputStream = resourceLoader.getResource("classpath:"+path).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
    
    /**
     * 获取文件，如果文件所在的父文件不存在则创建父文件
     * @param filePath 文件路径
     * @return 文件对象，该文件还未存在磁盘上上
     */
    public static File createFile(String filePath) {
        File file = new File(filePath);
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /**
     * 识别身份证信息
     * @param byteArray 身份证文件字节数组
     * @return
     */
    public static Pid IdentificationCard(byte[] byteArray){
        Pid pid = null;
        // 身份证识别url
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        // 本地图片路径
       // String filePath = "#####本地文件路径#####";
        String result = "";
        try {
            byte[] imgData = byteArray;//FileUtil.readFileByBytes(filePath);

            String imgStr = Base64Util.encode(imgData);
            // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
            String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
//            String accessToken = "#####调用鉴权接口获取的token#####";
            String accessToken = AuthService.getAuth();//"24.d7f18154ed9d66c7189af50a05027ffe.2592000.1544145536.282335-14699690";
            result = HttpUtil.post(idcardIdentificate, accessToken, params);
            pid = JsonUtils.readerPid(result);
            logger.info("读取的身份证信息："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pid;
    }

    /**
     * 读取bank文件中的银行信息
     * @return
     */
    public static String getBankName(String bankAlias) {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(config("bank.json"));
            JsonNode bankNode = jsonNode.get(bankAlias);
            result = bankNode.textValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(result);
        return result;
    }


}
