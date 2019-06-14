package com.vcv.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcv.mapper.DictMapper;
import com.vcv.model.Dictionary;
import com.vcv.service.DictService;
@Service
public class DictServiceImpl implements DictService {
	@Autowired
	private DictMapper dictMapper;
	@Override
	public List<Dictionary> getDictByType(Map params) {
		
		return dictMapper.getDictByType(params);
	}

}
