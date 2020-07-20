package com.sensebling.ope.core.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型使用版本记录表
 *
 */
@Entity
@Table(name = "ope_core_model_version_log")
@SuppressWarnings("serial")
public class OpeCoreModelVersionLog implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 模型id **/
	private String modelid;
	/** 版本id **/
	private String versionid;
	private String addtime;
	private String adduser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelid() {
		return modelid;
	}
	public void setModelid(String modelid) {
		this.modelid = modelid;
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
}
