package com.sensebling.ope.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型类别表
 *
 */
@Entity
@Table(name = "ope_core_model")
@SuppressWarnings("serial")
public class OpeCoreModel implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 模型编码 **/
	private String code;
	/** 模型名称 **/
	private String name;
	/** 使用指标版本 **/
	@Column(updatable=false)
	private String versionid;
	/** 状态 1启用2禁用 **/
	private String status;
	@Column(updatable=false)
	private String addtime;
	@Column(updatable=false)
	private String adduser;
	private String updatetime;
	private String updateuser;
	private String remark;
	
	@Transient
	private String versioncode;
	@Transient
	private String versionname;
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
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAdduser() {
		return adduser;
	}
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersioncode() {
		return versioncode;
	}
	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}
	public String getVersionname() {
		return versionname;
	}
	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
}
