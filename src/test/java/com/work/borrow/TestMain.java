package com.work.borrow;

import com.work.borrow.annotation.Rid;
import com.work.borrow.annotation.Table;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.AccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class TestMain {
    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) throws Exception{
        update();
    }

    /**
     * insert into account(account,name,)
     * values(#{account},#{name},'')
     * @throws Exception
     */
    public static void ann() throws Exception{
        StringBuffer keysStr = new StringBuffer();
        StringBuffer valuesStr = new StringBuffer();
        StringBuffer whereBuffer = new StringBuffer();
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccount("1125456");
        accountInfo.setPidUp("456456456465465");
        accountInfo.setBorrow("22578元");

        Class<? extends AccountInfo> accountInfoClass = accountInfo.getClass();
        // 类操作
        Table table = accountInfoClass.getAnnotation(Table.class);
        String tableName = table.value();
        // 属性操作
        Field[] fields = accountInfoClass.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);
        for (Field field : fieldList) {
            field.setAccessible(true);
            Rid ridAnnotation = field.getAnnotation(Rid.class);
            if (ridAnnotation == null) {
                if (field.get(accountInfo) != null) {
                    whereBuffer.append(" `"+field.getName()+"` = #{"+field.getName()+"} and");
                    keysStr.append(" `"+field.getName()+"`, "); // 添加keys部分
                    valuesStr.append(" #{"+field.getName()+"}, ");
                }
            }
        }
        String keys = keysStr.substring(0, keysStr.lastIndexOf(","));
        String values = valuesStr.substring(0, valuesStr.lastIndexOf(","));
        String where = whereBuffer.substring(0,whereBuffer.lastIndexOf("and"));
        String querysql = "select * from "+tableName+" where"+where;
        String insertSql = "insert into "+tableName+"("+keys+") values("+values+");";
        logger.error(querysql);
        logger.error(insertSql);
    }
    public static void update() throws Exception{
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccount("1125456");
        accountInfo.setPidUp("456456456465465");
        accountInfo.setBorrow("22578元");

        StringBuffer updateBuffer = new StringBuffer();
        Class<? extends AccountInfo> accountInfoClass = accountInfo.getClass();
        // 类操作
        Table table = accountInfoClass.getAnnotation(Table.class);
        String tableName = table.value();
        // 属性操作
        Field[] fields = accountInfoClass.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);
        for (Field field : fieldList) {
            field.setAccessible(true);
            Rid ridAnnotation = field.getAnnotation(Rid.class);
            if (ridAnnotation == null) {
                if (field.get(accountInfo) != null) {
                    updateBuffer.append(field.getName()+" = " +"#{"+field.getName()+"}, ");
                }
            }
        }
        String updateStr = updateBuffer.substring(0,updateBuffer.lastIndexOf(","));
        String updateSql = "update "+tableName+" set "+updateStr+" where account = #{account} and `status` = "+ AccountMapper.STATUS_OPEN+";";
        logger.error(updateSql);
    }
}
