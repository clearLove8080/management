package com.vcv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.ItemCategory;

/**
 * Created by jiangyunxiong on 2018/3/10.
 */
@Mapper
public interface ItemCategoryMapper {

    ItemCategory findById(ItemCategory itemCategory);

    List<ItemCategory> list(ItemCategory itemCategory);

    List<ItemCategory> list1();

    int count(ItemCategory itemCategory);

    int insert(ItemCategory itemCategory);

    int update(ItemCategory itemCategory);

    void delete(ItemCategory itemCategory);

    int updateStatus(ItemCategory itemCategory);

}
