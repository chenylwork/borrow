package com.work.borrow.mapper;

import com.work.borrow.po.Account;
import com.work.borrow.po.AccountInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * 账户数据操作mapper
 */
@Mapper
@Component("accountMapper")
public interface AccountMapper {
    String USER_Y = "1"; // 默认的使用状态
    String USR_N = "0"; // 借款已经结束了，已还款
    String CHECK_W = "0"; // 待审核
    String CHECK_Y = "1"; // 审核通过
    String CHECK_N = "2"; // 审核失败
    String FAST_Y = "1"; // 快速审核
    String FAST_N = "0"; // 正常审核
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
     * 获取当前使用的实名信息
     * @param mobile 用户手机号
     * @return
     */
    @Select("SELECT * FROM info WHERE mobile = #{mobile} AND isuse = '"+USER_Y+"';")
    public AccountInfo getUseAccountByMobile(@Param("mobile") String mobile);

    /**
     * 设置快速审核
     * @param mobile
     * @return
     */
    @Update("UPDATE info SET `fast` = '"+FAST_Y+"' WHERE mobile = #{mobile} AND id = #{info};")
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
    @Update("UPDATE info SET isuse = '"+USR_N+"' WHERE mobile = #{mobile} AND id = #{id};")
    public boolean changeAccountUse(@Param("mobile")String mobile,@Param("id") int id);
}
