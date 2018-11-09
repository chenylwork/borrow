package com.work.borrow.controller;

import com.work.borrow.po.Message;
import com.work.borrow.po.Order;
import com.work.borrow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 添加订单信息
     * @param order
     * @return
     */
    @RequestMapping("/order/add")
    public Message addOrder(Order order) {
        return orderService.addOrder(order);
    }

    /**
     * 修改订单状态
     * @param order
     * @return
     */
    @RequestMapping("/order/update")
    public Message updateStatus(Order order) {
        return orderService.updateStatus(order);
    }

    /**
     * 还款
     * @param order 订单对象
     * @return
     */
    @RequestMapping("/order/repay")
    public Message repayMoney(Order order) {
        return orderService.repayOrder(order);
    }
    @RequestMapping("/order/query")
    public Message getOrder(Order order) {
        return orderService.getOrder(order);
    }
}
