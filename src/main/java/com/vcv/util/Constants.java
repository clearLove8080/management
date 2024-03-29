package com.vcv.util;

public enum Constants {
	
	Code111("111"),
	Code000("000"),
	Msg111("请求成功;"),
	Msg000("请求失败"),
	OrderByAddDateDesc("ADDDATE DESC"),
	OrderByAddDateAsc("ADDDATE ASC"),
	OrderByLikesDesc("LIKES DESC"),
	OrderByBrowsesDesc("BROWSES DESC"),
	OrderByCommentsDesc("COMMENTS DESC"),
	OrderByScoreDesc("SCORE DESC"),
	DEV_LOG_ROOT_DAILY("info,error,CONSOLE,DEBUG"),
	DEV_LOG_PATH_DAILY("E:/logs/infos"),
	PRO_LOG_ROOT_DAILY("CONSOLE,info,error,DEBUG"),
	PRO_LOG_PATH_DAILY("/home/admin/logs/infos"),
	DEV_SAVE_PATH("E:/files/save/"),
	PRO_SAVE_PATH("/home/admin/files/save/"),
	LOGIN_TICKET("login_ticket"),
	QINIU_URL("http://ptnfl8sld.bkt.clouddn.com"),
	FILE_SIZE("61457280");
	
	private String value;
	private Constants(String value) {
		this.value=value;
	}
	public String getValue() {
		return value;
	}
}
