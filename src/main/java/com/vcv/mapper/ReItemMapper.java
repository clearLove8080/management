package com.vcv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.ReItem;

@Mapper
public interface ReItemMapper {

    int deleteByPrimaryKey(int id);

    int insert(ReItem record);

    ReItem selectByPrimaryKey(int id);

    List<ReItem> selectAll();

    int updateByPrimaryKey(ReItem record);
}