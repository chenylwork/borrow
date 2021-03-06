package com.work.borrow.mapper.sql.provider;

import com.work.borrow.annotation.Rid;
import com.work.borrow.annotation.Table;
import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.po.Account;
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
         // 编号判断
         if (account.getId() != null && account.getId() != 0) {
             stringBuffer.append(" and id = #{id}");
         }
         // 账户判断
         if (account.getAccount() != null && !account.getAccount().equals("")) {
            stringBuffer.append(" and account = #{account}");
        }
        // 名称判断
        if (account.getName() != null && !account.getName().equals("")) {
            stringBuffer.append(" and name = #{name}");
        }
        // 身份证判断
        if (account.getPid() != null && !account.getPid().equals("")) {
            stringBuffer.append(" and pid = #{pid}");
        }
        // 审批状态
         if (account.getStatus() != null && !account.getStatus().equals("")) {
             stringBuffer.append(" and `status` = #{status}");
         }
        logger.info("where语句："+stringBuffer.toString());
        return stringBuffer;
    }
    /**
     * 查询数据个数语句
     * @param accountInfo
     * @return
     */
    public String querySizeSql(AccountInfo accountInfo) {
        Class<? extends AccountInfo> accountInfoClass = accountInfo.getClass();
        // 获取数据表名称
        Table table = accountInfoClass.getAnnotation(Table.class);
        String tableName = table.value();
        StringBuffer stringBuffer = new StringBuffer("select count(id) from "+tableName+" where 1=1 ");
        stringBuffer.append(whereSql(accountInfo));
        logger.info("查询语句："+stringBuffer);
        return stringBuffer.toString();
    }

    /**
     * 查询数据个数语句
     * @param accountInfo
     * @return
     */
    public String queryErrorSizeSql(AccountInfo accountInfo) {
        String sql = querySizeSql(accountInfo);
        sql = sql + " and `status` in ('"+AccountMapper.STATUS_ERROR+"') ";
        return sql;
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
        int pageNo = page.getNo();
        int length = page.getLength();

        int start = (pageNo - 1)  * length;
        return (" limit "+start+","+length);
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
     * @param map
     * @return
     */
    public String queryAccountInfosql(Map<String,Object> map) throws Exception{
        AccountInfo accountInfo = (AccountInfo)map.get(AccountMapper.ACCOUNT_INFO_PREFIX);
        Page page = (Page) map.get("page");
        int use = (Integer) map.get(AccountMapper.NO_SHOW_ERROR_INFO);
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
                if (!fieldValueIsEmpty(field,accountInfo)) {
                    whereBuffer.append(" `"+field.getName()+"` = #{"+AccountMapper.ACCOUNT_INFO_PREFIX+"."+field.getName()+"} and");
                }
            }
        }
        String where = " where 1=1 ";
        if (whereBuffer.length() > 0) {
            where = where + " and "+whereBuffer.substring(0,whereBuffer.lastIndexOf("and"));
        }
//        if (use == 1) {
//            where = where+" and `status` not in ('"+AccountMapper.STATUS_ERROR+"') ";
//        }
        String querySql = "select * from "+tableName+where+" order by opentime desc "+mysqlPageSql(page);
        logger.info(querySql);
        return querySql;
    }

    /**
     * 判断字段是否为空
     * @param field
     * @param t
     * @param <T>
     * @return
     */
    private <T> boolean fieldValueIsEmpty(Field field,T t) {
        // 获取字段类型
        String fieldType = field.getType().getSimpleName();
        // 获取字段值
        try {
            if (field.get(t) == null) {
                return true;
            }
            if (fieldType.equals("int") && field.get(t).equals(0)) {
                return true;
            }
            if (fieldType.equals("String") && field.get(t).equals("")) {
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  false;
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
        String updateSql = "update "+tableName+" set "+updateStr+" where account = #{account} and `status` in ("+ AccountMapper.UPDATE_STATUS+");";
        logger.info(updateSql);
        return updateSql;
    }

}
