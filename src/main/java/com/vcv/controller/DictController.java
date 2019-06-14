package com.vcv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.vcv.model.Dictionary;
import com.vcv.service.DictService;

@Controller
@RequestMapping(value="/dictController")
public class DictController {
	@Autowired
	private DictService dictService;
	
	@RequestMapping(value="/getDictByType",method=RequestMethod.GET)
	@ResponseBody
	public List<Dictionary> getDictByType(String type){
		Map params=Maps.newHashMap();
		params.put("dictType", type);
		return dictService.getDictByType(params);
	}
}
