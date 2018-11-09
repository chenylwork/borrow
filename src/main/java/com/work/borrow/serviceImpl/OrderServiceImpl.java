package com.work.borrow.serviceImpl;

import com.work.borrow.mapper.AccountMapper;
import com.work.borrow.mapper.OrderMapper;
import com.work.borrow.po.Message;
import com.work.borrow.po.Order;
import com.work.borrow.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Message addOrder(Order order) {
        boolean add = orderMapper.add(order);
        if (add) {
            return Message.createSuccessMessage(Message.VALUE_CODE_ORDER_ADD_Y,Message.VALUE_CONTENT_ORDER_ADD_Y);
        } else {
            return Message.createSuccessMessage(Message.VALUE_CODE_ORDER_ADD_N,Message.VALUE_CONTENT_ORDER_ADD_N);
        }
    }

    @Override
    public Message updateStatus(Order order) {
        boolean add = orderMapper.updateStatus(order);
        if (add) {
            return Message.createSuccessMessage(Message.VALUE_CODE_ORDER_STATUS_UPDATE_Y,Message.VALUE_CONTENT_ORDER_STATUS_UPDATE_Y);
        } else {
            return Message.createSuccessMessage(Message.VALUE_CODE_ORDER_STATUS_UPDATE_N,Message.VALUE_CONTENT_ORDER_STATUS_UPDATE_N);
        }
    }

    @Override
    public Message repayOrder(Order order) {
        // 状态修改为已还款
        order.setStatus(OrderMapper.ORDER_STATUS_END);
        boolean update = orderMapper.updateMoney(order);
        if (update) {
            return Message.createSuccessMessage(Message.VALUE_CODE_ORDER_MONEY_UPDATE_Y,Message.VALUE_CONTENT_ORDER_MONEY_UPDATE_Y);
        } else {
            return Message.createSuccessMessage(Message.VALUE_CODE_ORDER_MONEY_UPDATE_N,Message.VALUE_CONTENT_ORDER_MONEY_UPDATE_N);
        }
    }

    @Override
    public Message getOrder(Order order) {
        List<Order> orderList = null;
        Message message = null;
        // 改变逾期订单状态
        changeStatus(order.getAccount());
        if (order.getStatus() == null || order.getStatus().equals("")) {
            orderList = orderMapper.getOrder(order.getAccount());
        } else {
            orderList = orderMapper.getOrderByStatus(order.getAccount(),order.getStatus());
        }
        if (orderList == null) {
            message = Message.createFailMessage(Message.VALUE_CODE_ORDER_QUERY_N,Message.VALUE_CONTENT_ORDER_QUERY_N);
        } else {
            message = Message.createSuccessMessage(Message.VALUE_CODE_ORDER_QUERY_Y,Message.VALUE_CONTENT_ORDER_QUERY_Y);
        }
        message.put(Message.KEY_DATA,orderList);
        return message;
    }

    /**
     * 改变订单状态，逾期订单状态
     * @param account 需要修改的账户信息
     */
    public void changeStatus(String account) {
        // 获取待还款订单
        List<Order> waitOrder = orderMapper.getOrderByStatus(account, OrderMapper.ORDER_STATUS_WAIT);
        SimpleDateFormat format = new SimpleDateFormat(OrderMapper.ORDER_DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        // 获取当前时间
        long nowDate = new Date().getTime();
        waitOrder.forEach(order -> {
            try {
                Date date = format.parse(order.getStartTime());
                calendar.setTime(date);
                calendar.add(Calendar.MONTH,order.getLife());
                calendar.getTimeInMillis();
                // 修改逾期状态
                if(nowDate > calendar.getTime().getTime()) {
                    orderMapper.updateStatus(new Order(account,OrderMapper.ORDER_STATUS_OUT));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}