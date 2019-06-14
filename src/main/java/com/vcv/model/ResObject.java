package com.vcv.model;

import lombok.Data;
@Data
public class ResObject<T> {

	private String resCode;
	private String resMessage;
	private InfoData infoData;
	private Boolean flag;
	
	public ResObject() {
		
	}
	
	public ResObject(String resCode,String resMessage,InfoData infoData,Boolean flag){
		this.resCode = resCode;
		this.resMessage = resMessage;
		this.infoData = infoData;
		this.flag = flag;
	}
	public  ResObject<T> successRes() {
		return  new ResObject<T>("666", "操作成功", infoData, true);
	}
	
	public  ResObject<T> failRes() {
		return  new ResObject<T>("000", "操作失败", infoData, false);
	}
}
