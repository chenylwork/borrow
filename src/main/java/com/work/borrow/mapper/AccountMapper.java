package com.work.borrow.mapper;

import com.work.borrow.mapper.sql.provider.AccountSqlProvider;
import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import com.work.borrow.po.LinkMan;
import com.work.borrow.util.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 账户数据操作mapper
 */
@Mapper
@Component("accountMapper")
public interface AccountMapper {
    String ACCOUNT_INFO_PREFIX = "account";
    String PAGE_ALIAS = "page";
    String NO_SHOW_ERROR_INFO = "use";
    String STATUS_OPEN = "0"; // 前台录入还未结束--》》》》录入中
    String STATUS_WRIT = "1"; //  等待后台审核-》》》》》》待审核
    String STATUS_PASS = "2"; // 审核通过，等待付款--》》》待付款
    String STATUS_ERROR = "3"; // 审核驳回 --》》》》》》》被驳回
    String STATUS_BEFORE = "4"; // 用户付款，等待放款 -》》待放款
    String STATUS_START = "5"; // 后台放款，-- 》》》》》》待还款
    String STATUS_PAST = "6"; // 借款过期--》》》》》》》》已逾期
    String STATUS_END = "7"; // 借款结束，已还款-》》》》》已还款

    // 用户不能重新借款的状态
    String BORROW_UN_OVER = "'"+STATUS_WRIT+"','"+STATUS_PASS+"','"+
            STATUS_BEFORE+"','"+STATUS_START+"','"+STATUS_PAST+"'";
    // "','"+STATUS_ERROR+

    // 修改的状态
    String UPDATE_STATUS = "'"+STATUS_OPEN+"','"+STATUS_PASS+"'";

    /**
     * 添加新账户
     * @param account 账户信息
     * @return 添加乘车返回true，失败失败false
     */
    @Insert("INSERT INTO account(mobile,password) VALUES(#{mobile},#{password});")
    public boolean addAccount(Account account);

    /**
     * 根据用户手机号，检查用户是否存在
     * @param mobile 账户手机号
     * @return 成功返回账户信息，失败返回null
     */
    @Select("SELECT COUNT(id) FROM account WHERE mobile = #{mobile};")
    public boolean checkAccountByMobile(String mobile);

    /**
     * 根据用户手机号和密码查询用户是否存在
     * @param account 账户信息
     * @return
     */
    @Select("SELECT COUNT(id) FROM account WHERE mobile = #{mobile} and password = #{password};")
    public boolean checkAccountByPass(Account account);

    /**
     * 修改账户密码
     * @param account 账户信息
     * @return
     */
    @Update("UPDATE account SET password = #{password} WHERE mobile = #{mobile};")
    public boolean changePass(Account account);


    /**
     * 添加账户详细信息，身份证正反面
     * @param accountInfo 账户信息
     * @return
     */
    @Insert("INSERT into account_info(account,pid_up,pid_down) " +
            "values(#{account},#{pidUp},#{pidUp});")
    public boolean inputAccountInfoPid(AccountInfo accountInfo);

    /**
     * 录入工作信息
     * @param accountInfo 录入工作信息
     * @return
     */
    @Update("update account_info set workPhone = #{workPhone} ,workAddress = #{workAddress}," +
            " income = #{income}, workTime = #{workTime} " +
            "where account = #{account} and `status` = "+STATUS_OPEN+";")
    public boolean inputWorkInfo(AccountInfo accountInfo);

    /**
     * 录入用户紧急联系人
     * @param linkMan
     * @return
     */
    @Insert("INSERT INTO linkman(infoID,account,mobile,name,relation) " +
            "values(#{infoID},#{account},#{mobile},#{name},#{relation});")
    public boolean inputAccountLinkMan(LinkMan linkMan);

    /**
     * 录入用户银行卡信息
     * @param accountInfo
     * @return
     */
    @Update("UPDATE account_info SET cardCode = #{cardCode},cardHolder = #{cardHolder} " +
            "where account = #{account} and `status` = "+STATUS_OPEN+";")
    public boolean inputAccountCard(AccountInfo accountInfo);

    /**
     * 录入用户信息
     * @param accountInfo
     * @return
     */
    @InsertProvider(type = AccountSqlProvider.class,method = "insertAccountInfoSql")
    public boolean inputAccountInfo(AccountInfo accountInfo);

    /**
     * 修改用户信息
     * @param accountInfo
     * @return
     */
    @UpdateProvider(type = AccountSqlProvider.class,method = "updateAccountSql")
    public boolean updateAccountInfo(AccountInfo accountInfo);

    /**
     * 查询用户信息
     * @param map
     * @return
     */
    @SelectProvider(type = AccountSqlProvider.class,method = "queryAccountInfosql")
    public List<AccountInfo> queryAccountInfo(Map<String,Object> map);
    /**
     * 获取当前使用的实名信息
     * @param account 用户手机号
     * @return
     */
    @Select("SELECT * FROM account_info WHERE account = #{account} AND status in ("+UPDATE_STATUS+") limit 1;")
    public AccountInfo getUseAccountByMobile(@Param("account") String account);

    /**
     * 查询linkman信息
     * @param linkMan
     * @return
     */
    @Select("select * from linkman where account = #{account} and infoID = #{infoID};")
    List<LinkMan> searchLinkman(LinkMan linkMan);

    /**
     * 删除联系人信息
     * @param linkMan
     * @return
     */
    @Delete("DELETE from linkman where account = #{account} and infoID = #{infoID};")
    boolean deleteLinkMan(LinkMan linkMan);

    /**
     * 查询用户,借款未完成的信息状态
     * @param account
     * @return
     */
    @Select("select `status` from account_info where account = #{accoun} " +
            "and `status` in ("+BORROW_UN_OVER+") limit 0,1;")
    AccountInfo getAccountStartStatus(String account);

    /**
     * 修改借款信息状态码
     * @param accountInfo
     * @return
     */
    @Update("UPDATE account_info set `status` = #{status},startTime = #{startTime} where `id` = #{id}")
    boolean updateStatus(AccountInfo accountInfo);

    /**
     * 获取用户不能重新借款的信息
     * @param accountInfo
     * @return
     */
    @Select("select * FROM account_info where account = #{account} " +
            "and `status` in ("+BORROW_UN_OVER+") limit 0,1;")
    AccountInfo getUnFinashInfo(AccountInfo accountInfo);

    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/
    /**
     * 添加详细信息
     * @param account 账户信息
     * @return
     */
    @Insert("INSERT  INTO `info`(`mobile`,`name`,`sex`,`pid`,`house`,`live`,`married`,`loan`,`income`,`workTime`," +
            "`companyName`,`companyPhone`,`companyAddress`,`contactName`,`contactPhone`,`contactRelation`,`card_code`," +
            "`card_holder`,`card_bank`,`isuse`,`check`,`fast`,`max_money`)" +
            "VALUES (#{mobile},#{name},#{sex},#{pid},#{house},#{live},#{married},#{loan},#{income},#{workTime}," +
            "#{companyName},#{companyPhone},#{companyAddress},#{contactName},#{contactPhone},#{contactRelation}," +
            "#{cardCode},#{cardHolder},#{cardBank},#{use},#{check},#{fast},#{maxMoney});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    public boolean addInfo(AccountInfo account);

    @Update("UPDATE info SET pid_up = #{up} WHERE mobile = #{mobile};")
    public boolean addPidImg(@Param("mobile") String mobile, @Param("up") String up);

    /**
     * 添加用户手持身份证照
     * @param mobile 用户手机号
     * @param pidHand 上传的文件路径
     * @return
     */
    @Update("UPDATE info SET pid_hand = #{pidHand} WHERE mobile = #{mobile};")
    public boolean addPidHand(@Param("mobile") String mobile,@Param("pidHand") String pidHand);



    /**
     * 设置快速审核
     * @param mobile
     * @return
     */
    @Update("UPDATE info SET `fast` = '"+STATUS_OPEN+"' WHERE mobile = #{mobile} AND id = #{info};")
    public boolean changeAccountFast(@Param("mobile")String mobile,@Param("info") String id);

    /**
     * 设置最大借款金额
     * @param mobile
     * @param money
     * @return
     */
    @Update("UPDATE info SET max_money = #{money}' WHERE mobile = #{mobile} AND id = #{id};")
    public boolean changeAccountMaxMoney(@Param("mobile")String mobile,@Param("money") String money,@Param("id") int id);

    /**
     * 修改审核状态
     * @param mobile
     * @param status
     * @return
     */
    @Update("UPDATE info SET `check` = #{status} WHERE mobile = #{mobile} AND id = #{id};")
    public boolean changeAccountCheck(@Param("mobile")String mobile,@Param("status") String status,@Param("id") int id);

    /**
     * 修改使用状态
     * @param mobile
     * @return
     */
    @Update("UPDATE info SET isuse = '"+STATUS_OPEN+"' WHERE mobile = #{mobile} AND id = #{id};")
    public boolean changeAccountUse(@Param("mobile")String mobile,@Param("id") int id);

    /**
     * 条件查询审核信息
     * @param map
     * @return
     */
    @SelectProvider(type=AccountSqlProvider.class,method="queryPageDataSql")
    public List<AccountInfo> searchAccountInfo(Map<String,Object> map);

    /**
     * 查询个数
     * @param accountInfo
     * @return
     */
    @SelectProvider(type=AccountSqlProvider.class,method = "querySizeSql")
    public int size(AccountInfo accountInfo);

    @SelectProvider(type=AccountSqlProvider.class,method = "queryErrorSizeSql")
    public int errorSize(AccountInfo accountInfo);
}
