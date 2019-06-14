package com.vcv.service;

import java.util.List;
import java.util.Map;

import com.vcv.model.Dictionary;

public interface DictService {

	List<Dictionary> getDictByType(Map params);

}
