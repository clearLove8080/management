package com.vcv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.OrderItem;

@Mapper
public interface OrderItemMapper {

    int deleteByPrimaryKey(String id);

    int insert(OrderItem record);

    OrderItem selectByPrimaryKey(String id);

    OrderItem selectByPrimaryOrderKey(String orderId);

    List<OrderItem> selectAll();

    int updateByPrimaryKey(OrderItem record);
}