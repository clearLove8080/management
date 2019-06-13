package com.vcv.model;

import lombok.Data;

import java.util.Date;

@Data
public class ItemCategory extends BaseObject{
    private int id;
    private  int parentId;
    private String name;
    /**
     * 状态。可选值:1(正常),2(删除)
     */
    private int status;
    private int sortOrder;
    private int isParent;
    private Date created;
    private Date updated;
    private String createdStr;
    private String updatedStr;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getIsParent() {
		return isParent;
	}
	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getCreatedStr() {
		return createdStr;
	}
	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}
	public String getUpdatedStr() {
		return updatedStr;
	}
	public void setUpdatedStr(String updatedStr) {
		this.updatedStr = updatedStr;
	}
    
}
