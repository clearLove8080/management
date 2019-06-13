package com.vcv.model;

import lombok.Data;

import java.util.Date;
@Data
public class Order extends BaseObject{
    private String orderId;
    private String itemId;
    private String payment;
    private String refundStatusStr;
    private Integer paymentType;
    private String postFee;
    private String refundReason;
    private Integer refundStatus;
    private int isRefund;
    private Integer status;
    private String itemTitle;
    private Long totalFee;
    private int num;
    private String statusStr;
    private Date createTime;
    private Date updateTime;
    private Date paymentTime;
    private Date consignTime;
    private Date endTime;
    private Date closeTime;
    private String shippingName;
    private String shippingCode;
    private String minOrderTimeStr;
    private String maxOrderTimeStr;
    private Date minOrderTime;
    private Date maxOrderTime;
    private Long userId;
    private String paymentTypeStr;
    private String buyerMessage;
    private String buyerNick;
    private String buyerRateStr;
    private String dateStr1;
    private String dateStr2;
    private String dateStr3;
    private String dateStr4;
    private String dateStr5;
    private Integer buyerRate;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getRefundStatusStr() {
		return refundStatusStr;
	}
	public void setRefundStatusStr(String refundStatusStr) {
		this.refundStatusStr = refundStatusStr;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public String getPostFee() {
		return postFee;
	}
	public void setPostFee(String postFee) {
		this.postFee = postFee;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public int getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(int isRefund) {
		this.isRefund = isRefund;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Date getConsignTime() {
		return consignTime;
	}
	public void setConsignTime(Date consignTime) {
		this.consignTime = consignTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public String getShippingCode() {
		return shippingCode;
	}
	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}
	public String getMinOrderTimeStr() {
		return minOrderTimeStr;
	}
	public void setMinOrderTimeStr(String minOrderTimeStr) {
		this.minOrderTimeStr = minOrderTimeStr;
	}
	public String getMaxOrderTimeStr() {
		return maxOrderTimeStr;
	}
	public void setMaxOrderTimeStr(String maxOrderTimeStr) {
		this.maxOrderTimeStr = maxOrderTimeStr;
	}
	public Date getMinOrderTime() {
		return minOrderTime;
	}
	public void setMinOrderTime(Date minOrderTime) {
		this.minOrderTime = minOrderTime;
	}
	public Date getMaxOrderTime() {
		return maxOrderTime;
	}
	public void setMaxOrderTime(Date maxOrderTime) {
		this.maxOrderTime = maxOrderTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPaymentTypeStr() {
		return paymentTypeStr;
	}
	public void setPaymentTypeStr(String paymentTypeStr) {
		this.paymentTypeStr = paymentTypeStr;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public String getBuyerRateStr() {
		return buyerRateStr;
	}
	public void setBuyerRateStr(String buyerRateStr) {
		this.buyerRateStr = buyerRateStr;
	}
	public String getDateStr1() {
		return dateStr1;
	}
	public void setDateStr1(String dateStr1) {
		this.dateStr1 = dateStr1;
	}
	public String getDateStr2() {
		return dateStr2;
	}
	public void setDateStr2(String dateStr2) {
		this.dateStr2 = dateStr2;
	}
	public String getDateStr3() {
		return dateStr3;
	}
	public void setDateStr3(String dateStr3) {
		this.dateStr3 = dateStr3;
	}
	public String getDateStr4() {
		return dateStr4;
	}
	public void setDateStr4(String dateStr4) {
		this.dateStr4 = dateStr4;
	}
	public String getDateStr5() {
		return dateStr5;
	}
	public void setDateStr5(String dateStr5) {
		this.dateStr5 = dateStr5;
	}
	public Integer getBuyerRate() {
		return buyerRate;
	}
	public void setBuyerRate(Integer buyerRate) {
		this.buyerRate = buyerRate;
	}
    
}