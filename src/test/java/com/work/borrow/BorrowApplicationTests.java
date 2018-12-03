package com.work.borrow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.borrow.dao.redis.RedisDao;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.Message;
import com.work.borrow.service.OrderService;
import com.work.borrow.service.SMSService;
import com.work.borrow.serviceImpl.MailService;
import com.work.borrow.util.FileUtils;
import com.work.borrow.util.JsonUtils;
import com.work.borrow.util.MySQLUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BorrowApplicationTests {
	@Autowired
	private SMSService smsService;
    @Autowired
	private RedisDao redisDao;
    @Autowired
    private AccountMapper accountMapper;
//    @Autowired
//    private MailService mailService;
    @Value("${pid.img.file.save.path}")
    private String path;

    private static Logger logger = LoggerFactory.getLogger(BorrowApplicationTests.class);
    @Test
    public void configTest() {
        logger.info(path);
    }

	@Test
	public void contextLoads() {
        Message message = smsService.sendSMSCode("18132070787");
        System.err.println(message);
    }
    @Test
	public void redisTest() {
        Message message = smsService.checkSMSCode("18132070787", "869951");
        // boolean isok = redisDao.setSMSCode("18132070787", "869951");
       // String smsCode = redisDao.getSMSCode("18132070787");
        System.err.println(message);
    }
    @Test
    public void jsonTest() {
        Message message = Message.createSuccessMessage("","");
        message.put("lis",false);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = mapper.writeValueAsString(message);
            logger.info(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void sqlTest() {
        //boolean b = accountMapper.addAccount(new Account("58965545", "123456"));
//        boolean bool = accountMapper.changePass(new Account("58965545", "new123456"));
//        boolean bool = accountMapper.checkAccountByMobile("58965545");
        boolean bool = accountMapper.checkAccountByPass(new Account("58965545", "new123456"));
        logger.info("添加新用户："+bool);
    }

    /**
     * 发送邮件测试
     */
//    @Test
//    public void mailTest() {
//        mailService.sendSimpleMail("chenylemail@163.com","错误日志","借贷宝错误日志");
//    }

    @Test
    public void  fileTest() {
        File file = new File("D:/image/borrow/12181101/18132070787/up.img");
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            logger.info(mkdirs+"");
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void loadFile() {
        String bankName = FileUtils.getBankName("ICBC");
        System.err.println(bankName);
    }
    @Test
    public void readPid() {

        JsonUtils.readerPid("");
    }

    /**
     * mysql 备份
     */
    @Test
    public void backupMysql() {
        MySQLUtils.backup("D:/borrow/data/201812/borrow.sql");
    }

    /**
     * 还原数据库
     */
    @Test
    public void restoreMysql() {
        MySQLUtils.restore("D:/borrow/data/201812/borrow.sql");
    }



}
