package com.work.borrow.mapper;

import com.work.borrow.po.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("orderMapper")
public interface OrderMapper {
    String ORDER_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    String ORDER_STATUS_WAIT = "0"; // 待还款状态
    String ORDER_STATUS_END = "1"; // 已还款状态
    String ORDER_STATUS_OUT = "2"; // 已逾期状态

    /**
     * 添加订单信息
     * @param order
     * @return
     */
    @Insert("INSERT INTO `order`(account,borrow_money,life,STATUS) " +
            "VALUES(#{account},#{money},#{life},0);")
    boolean add(Order order);

    /**
     * 修改订单状态
     * @param order
     * @return
     */
    @Update("UPDATE `order` SET STATUS = #{status} WHERE account = #{account};")
    boolean updateStatus(Order order);

    /**
     * 查询订单，根据账号
     * @param account
     * @return
     */
    @Select("SELECT * FROM `order` WHERE account = #{account}")
    List<Order> getOrder(@Param("account") String account);

    /**
     * 查询订单，根据账号和状态值
     * @param account
     * @param status
     * @return
     */
    @Select("SELECT * FROM `order` WHERE account = #{account} and status = #{status}")
    List<Order> getOrderByStatus(@Param("account") String account,@Param("status") String status);

    @Update("UPDATE `order` SET repay_money =  repay_money + #{repay},status = #{status} WHERE account = #{account};")
    boolean updateMoney(Order order);


}
