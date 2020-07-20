package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 角色菜单权限关联表
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sen_role_module_auth")
public class RoleModuleAuth implements Serializable{
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")
	private String id;
	private String rolemoduleid;
	private String authid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRolemoduleid() {
		return rolemoduleid;
	}
	public void setRolemoduleid(String rolemoduleid) {
		this.rolemoduleid = rolemoduleid;
	}
	public String getAuthid() {
		return authid;
	}
	public void setAuthid(String authid) {
		this.authid = authid;
	}
}
