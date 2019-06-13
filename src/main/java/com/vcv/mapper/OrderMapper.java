package com.vcv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.Order;

@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    Order selectByPrimaryKey(String orderId);

    List<Order> list(Order order);

    List<Order> listRefund(Order order);

    List<Order> selectAll();

    Integer selectCurOrderNum();
    Integer selectLastOrderNum();

    Long selectCurPayment();
    Long selectLastPayment();

    Integer selectCurRefundOrder();
    Integer selectLastRefundOrder();

    Integer selectDayOrderSum(Order order);
    Integer selectDayOrderNum(Order order);

    Integer updateByPrimaryKey(Order record);
}