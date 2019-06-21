package com.vcv.model;

import lombok.Data;
@Data
public class ResObject<T> {

	private String resCode;
	private String resMsg;
	private InfoData infoData;
	private Boolean flag;
	
	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public InfoData getInfoData() {
		return infoData;
	}

	public void setInfoData(InfoData infoData) {
		this.infoData = infoData;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public ResObject() {
		
	}
	
	public ResObject(String resCode,String resMsg,InfoData infoData,Boolean flag){
		this.resCode = resCode;
		this.resMsg = resMsg;
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
