package com.work.borrow.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.borrow.po.Pid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * json处理
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 身份证读取信息转换为Pid对象
     * @param data 身份证读取的json字符串
     * @return
     */
    public static Pid readerPid(String data) {
        Pid pid = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode =  mapper.readTree(data);
            JsonNode wordsResult = jsonNode.get("words_result");
            String address = wordsResult.get("住址").get("words").textValue();
            String birth = wordsResult.get("出生").get("words").textValue();
            String name = wordsResult.get("姓名").get("words").textValue();
            String code = wordsResult.get("公民身份号码").get("words").textValue();
            String sex = wordsResult.get("性别").get("words").textValue();
            String nation = wordsResult.get("民族").get("words").textValue();
            pid = new Pid(address,birth,name,code,sex,nation);
            logger.info("json转换的身份证对象："+pid.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pid;
    }

}
