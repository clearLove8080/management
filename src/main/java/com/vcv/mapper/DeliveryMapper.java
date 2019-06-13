package com.vcv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.Delivery;

@Mapper
public interface DeliveryMapper {

    int deleteByPrimaryKey(Integer id);
    int deleteByDeliveryName(String deliveryName);

    int insert(Delivery record);

    Delivery selectByPrimaryKey(Integer id);

    List<Delivery> selectAll();

    int updateByPrimaryKey(Delivery record);

    Delivery selectByName(String name);
}