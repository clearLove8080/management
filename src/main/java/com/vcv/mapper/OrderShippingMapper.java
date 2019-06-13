package com.vcv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.OrderShipping;


@Mapper
public interface OrderShippingMapper {

    int deleteByPrimaryKey(String orderId);

    int insert(OrderShipping record);

    OrderShipping selectByPrimaryKey(String orderId);

    List<OrderShipping> selectAll();

    int updateByPrimaryKey(OrderShipping record);
}