package com.work.borrow.service;

import com.work.borrow.po.Message;
import com.work.borrow.po.Order;

/**
 * 借款订单服务接口
 */
public interface OrderService {
    /**
     * 添加订单信息
     * @param order 订单信息
     * @return
     */
    Message addOrder(Order order);

    /**
     * 修改订单状态
     * @param order 订单信息
     * @return
     */
    Message updateStatus(Order order);

    /**
     * 还款
     * @param order
     * @return
     */
    Message repayOrder(Order order);

    /**
     * 获取订单信息
     * @param order
     * @return
     */
    Message getOrder(Order order);
}
