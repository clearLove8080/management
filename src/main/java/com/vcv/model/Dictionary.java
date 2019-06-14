package com.vcv.model;

import lombok.Data;

@Data
public class Dictionary {
	private int id;
	private String dicCode;
	private String dicName;
	private String dicType;
	private String parentCode;
	private int dicLevel;
	private int dicSort;
	private String isEnable;
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getDicType() {
		return dicType;
	}
	public void setDicType(String dicType) {
		this.dicType = dicType;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public int getDicLevel() {
		return dicLevel;
	}
	public void setDicLevel(int dicLevel) {
		this.dicLevel = dicLevel;
	}
	public int getDicSort() {
		return dicSort;
	}
	public void setDicSort(int dicSort) {
		this.dicSort = dicSort;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
