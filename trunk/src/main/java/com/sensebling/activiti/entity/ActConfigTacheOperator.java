package com.sensebling.activiti.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 流程配置环节人员规则表
 *
 */
@Entity
@Table(name = "sen_act_config_tache_operator")
@SuppressWarnings("serial")
public class ActConfigTacheOperator implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	@Column(updatable=false)
	private String tacheid;//环节id
	private Integer type;//类别，1 用户 2 角色  3 岗位  4 其他  5 sql 6 存储
	private String value;//值
	private String remark;//备注
	@Column(updatable=false)
	private String category;//规则人员类别  1审批人员  2移交人员
	@Transient
	private String actname;//流程名称
	@Transient
	private String tachename;//环节名称
	@Transient
	private String values;//新增选择的数据
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTacheid() {
		return tacheid;
	}
	public void setTacheid(String tacheid) {
		this.tacheid = tacheid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getActname() {
		return actname;
	}
	public void setActname(String actname) {
		this.actname = actname;
	}
	public String getTachename() {
		return tachename;
	}
	public void setTachename(String tachename) {
		this.tachename = tachename;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	
}
