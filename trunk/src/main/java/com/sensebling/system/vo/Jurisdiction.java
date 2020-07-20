package com.sensebling.system.vo;

import java.io.Serializable;

import javax.persistence.Entity;


/**
 * 用户权限视图 
 * @author 
 * @date 2014-9-24
 */
@Entity
@SuppressWarnings("serial")
public class Jurisdiction implements Serializable{
	
	private String userId;//用户id
	private String roleId;//角色id
	private String moduleId;//模块id
	private String url;//模块url
	private Integer priority;//角色优先级
	private int crud;//增删改查权限
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public int getCrud() {
		return crud;
	}
	public void setCrud(int crud) {
		this.crud = crud;
	}
	
	

}
