package com.work.borrow;

import com.work.borrow.annotation.Rid;
import com.work.borrow.annotation.Table;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.util.MessageCode;
import com.work.borrow.util.MessageContent;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class TestMain {
    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) throws Exception{
        ann();
    }

    /**
     * insert into account(account,name,)
     * values(#{account},#{name},'')
     * @throws Exception
     */
    public static void ann() throws Exception{
        Class<?> messageCode = MessageCode.class;
        Class<?> messageContent = MessageContent.class;
        Field[] fields = messageCode.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String replace = fieldName.replace("CODE", "CONTENT");
            String key = field.get(messageCode).toString();
            String value = messageContent.getDeclaredField(replace).get(messageContent).toString();
            System.out.println("|"+key+"|"+value+"|");
        }
    }

    @Test
    public void fileTest() {
        Account account = new Account();
        account.setId(5);
        Class<? extends Account> accountClass = account.getClass();
        try {
            Field id = accountClass.getDeclaredField("id");
            id.setAccessible(true);
            boolean equals = id.get(account).equals(5);
            logger.info(equals+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Field[] fields = accountClass.getDeclaredFields();
//        List<Field> fieldList = Arrays.asList(fields);
//        fieldList.forEach(field -> {
//            logger.info(field.getType().getSimpleName());
//        });
    }
    @Test
    public void fun() {
        String intern = "nishibushihsha".intern();
        System.out.println(intern);
    }

}
