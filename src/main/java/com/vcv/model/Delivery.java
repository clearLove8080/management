package com.vcv.model;

import lombok.Data;

@Data
public class Delivery {

    private Integer id;

    private String deliveryName;

    private String deliveryCode;

    private String deliverySort;

    private String expressNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getDeliverySort() {
		return deliverySort;
	}

	public void setDeliverySort(String deliverySort) {
		this.deliverySort = deliverySort;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
    
    
}