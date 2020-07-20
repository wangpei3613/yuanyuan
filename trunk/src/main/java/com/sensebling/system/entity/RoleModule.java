package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统“角色——模块”关联 实体类
 * @author 
 * @date 2014-9-17
 */
@Entity
@Table(name="sen_role_module",uniqueConstraints={@UniqueConstraint(columnNames={"moduleId","roleId"})})
@SuppressWarnings("serial")
public class RoleModule implements Serializable {
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;//主键
	private String moduleid;//模块信息，
	private String roleid;//角色信息
	@Column(updatable=false)
	private String createUser;// 创建人
	@Column(updatable=false)
	private String createDate;// 创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
