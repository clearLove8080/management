package com.vcv.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.Dictionary;

@Mapper
public interface DictMapper {

	List<Dictionary> getDictByType(Map params);

}
