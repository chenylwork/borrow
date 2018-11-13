package com.work.borrow.mapper.sql.provider;

import com.work.borrow.annotation.Rid;
import com.work.borrow.annotation.Table;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.util.Page;
import org.apache.ibatis.annotations.SelectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户信息sql 供应类
 */
public class AccountSqlProvider {
    private static final Logger logger = LoggerFactory.getLogger(AccountSqlProvider.class);

    /**
     * 多条件where语句
     * @param account
     * @return
     */
     public StringBuffer whereSql(AccountInfo account) {
         StringBuffer stringBuffer = new StringBuffer();
//         // 编号判断
//         if (account.getId() != null && account.getId() != 0) {
//             stringBuffer.append(" and id = #{account.id}");
//         }
//         // 账户判断
//         if (account.getMobile() != null && !account.getMobile().equals("")) {
//            stringBuffer.append(" and mobile = #{account.mobile}");
//        }
//        // 名称判断
//        if (account.getName() != null && !account.getName().equals("")) {
//            stringBuffer.append(" and name = #{account.name}");
//        }
//        // 身份证判断
//        if (account.getPid() != null && !account.getPid().equals("")) {
//            stringBuffer.append(" and pid = #{account.pid}");
//        }
//        // 审批状态
//        if (account.getCheck() != null && !account.getCheck().equals("")) {
//            stringBuffer.append(" and `check` = #{account.check}");
//        }
        logger.info("where语句："+stringBuffer.toString());
        return stringBuffer;
    }
    /**
     * 查询数据个数语句
     * @param map
     * @return
     */
    public String querySizeSql(Map<String,Object> map) {
        AccountInfo accountInfo = null;
        Page page = null;
        StringBuffer stringBuffer = new StringBuffer("select count(id) from info where 1=1 ");
        if (map != null && ((accountInfo = (AccountInfo)map.get("account")) != null) ){
            stringBuffer.append(whereSql(accountInfo)).append(mysqlPageSql(page));
        }
        logger.info("查询语句："+stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * 分页查询数据语句
     * @param map
     * @return
     */
    public String queryPageDataSql(Map<String,Object> map) {
        AccountInfo accountInfo = null;
        Page page = null;
        StringBuffer stringBuffer = new StringBuffer("select * from info where 1=1 ");
        if (map != null && (accountInfo = (AccountInfo)map.get("account")) != null ){
            stringBuffer.append(whereSql(accountInfo)).append(mysqlPageSql(page));
        }
        if (map != null && ((page = (Page)map.get("page")) != null)) {
            logger.info("查询语句："+stringBuffer);
        }
        return stringBuffer.toString();
    }

    /**
     * mysql分页部分
     * @param page
     * @return
     */
    public String mysqlPageSql(Page page) {
        if (page == null || page.getNo() == 0 || page.getLength() == 0) return "";
        int start = page.getNo() * page.getLength();
        int end = (page.getNo()+1) * page.getLength();
        return (" limit "+start+","+end);
    }

    /**
     * 录入用户信息sql语句
     * @param accountInfo
     * @return
     */
    public String insertAccountInfoSql(AccountInfo accountInfo) throws Exception{
        StringBuffer keysStr = new StringBuffer();
        StringBuffer valuesStr = new StringBuffer();
        Class<? extends AccountInfo> accountInfoClass = accountInfo.getClass();
        // 获取数据表名称
        Table table = accountInfoClass.getAnnotation(Table.class);
        String tableName = table.value();

        Field[] fields = accountInfoClass.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);
        for (Field field : fieldList) {
            field.setAccessible(true);
            Rid ridAnnotation = field.getAnnotation(Rid.class);
            if (ridAnnotation == null) {
                if (field.get(accountInfo) != null) {
                    keysStr.append("`"+field.getName()+"`,"); // 添加keys部分
                    valuesStr.append("#{"+field.getName()+"},");
                }
            }
        }
        String keys = keysStr.substring(0, keysStr.lastIndexOf(","));
        String values = valuesStr.substring(0, valuesStr.lastIndexOf(","));
        String sql = "insert into "+tableName+"("+keys+") values("+values+");";
        logger.info("执行sql为：\n"+sql);
        return sql;
    }

    /**
     * 查询用户详细信息sql
     * @param accountInfo
     * @return
     */
    public String queryAccountInfosql(AccountInfo accountInfo) throws Exception{
        StringBuffer whereBuffer = new StringBuffer();
        Class<? extends AccountInfo> accountInfoClass = accountInfo.getClass();
        // 获取数据表名称
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
                }
            }
        }
        String where = whereBuffer.substring(0,whereBuffer.lastIndexOf("and"));
        String querySql = "select * from "+tableName+" where"+where;
        logger.info(querySql);
        return querySql;
    }

    /**
     * 修改用户详细信息
     * @param accountInfo
     * @return
     */
    public String updateAccountSql(AccountInfo accountInfo) throws Exception{
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
        logger.info(updateSql);
        return updateSql;
    }

}
