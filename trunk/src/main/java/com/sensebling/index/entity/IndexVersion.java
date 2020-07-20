package com.sensebling.index.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本表
 *
 */
@Entity
@Table(name = "model_index_version")
@SuppressWarnings("serial")
public class IndexVersion implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本号 **/
	@Column(updatable=false)
	private String code;
	/** 版本名称 **/
	private String name;
	/** 状态（1启用、0禁用） **/
	private String status;
	/** 创建时间 **/
	@Column(updatable=false)
	private String createdate;
	/** 创建者 **/
	@Column(updatable=false)
	private String createuser;
	/** 备注 **/
	private String remark;
	@Column(updatable=false)
	private String changed;//配置是否被修改  1代表已修改
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	
}
