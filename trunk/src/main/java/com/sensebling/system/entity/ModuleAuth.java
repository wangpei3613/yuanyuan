package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 菜单权限表
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sen_module_auth")
public class ModuleAuth implements Serializable{
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")
	private String id;
	private String moduleid;
	private String code;
	private Integer sort;
	private String name;
	private String type;
	private String status;
	private String titlle;
	private String icon;
	private String action;
	private String remark;
	@Transient
	private String checked;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitlle() {
		return titlle;
	}
	public void setTitlle(String titlle) {
		this.titlle = titlle;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
