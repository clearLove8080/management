package com.vcv.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class BaseController {
	/**
	 * 获取提交的表单数据，多个值用,分割
	 *
	 * @return
	 */
	public static Map<String, String> getFormData(HttpServletRequest request) {
		Map<String, String> formData = new HashMap<String, String>();
		String value;
		for (Map.Entry<String, String[]> map : request.getParameterMap().entrySet()) {
			value = StringUtils.join(map.getValue(), ",");
			if (StringUtils.isNotBlank(value)) {
				formData.put(map.getKey(), value.trim());
			}
		}
		return formData;
	}
}
