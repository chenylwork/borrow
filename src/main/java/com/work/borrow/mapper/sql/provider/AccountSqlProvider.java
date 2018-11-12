package com.work.borrow.mapper.sql.provider;

import com.work.borrow.po.AccountInfo;
import com.work.borrow.util.Page;
import org.apache.ibatis.annotations.SelectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
             stringBuffer.append(" and id = #{account.id}");
         }
         // 账户判断
         if (account.getMobile() != null && !account.getMobile().equals("")) {
            stringBuffer.append(" and mobile = #{account.mobile}");
        }
        // 名称判断
        if (account.getName() != null && !account.getName().equals("")) {
            stringBuffer.append(" and name = #{account.name}");
        }
        // 身份证判断
        if (account.getPid() != null && !account.getPid().equals("")) {
            stringBuffer.append(" and pid = #{account.pid}");
        }
        // 审批状态
        if (account.getCheck() != null && !account.getCheck().equals("")) {
            stringBuffer.append(" and `check` = #{account.check}");
        }
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

}
