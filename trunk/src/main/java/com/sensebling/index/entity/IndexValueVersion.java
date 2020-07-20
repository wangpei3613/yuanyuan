package com.sensebling.index.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本值表
 *
 */
@Entity
@Table(name = "model_index_value_version")
@SuppressWarnings("serial")
public class IndexValueVersion implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 关联id 或 业务id **/
	private String caseid;
	/** 版本历史id **/
	private String hisid;
	/** 版本id **/
	private String versionid;
	/** 创建时间 **/
	private String createtime;
	/** 创建用户 **/
	private String createuser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getHisid() {
		return hisid;
	}
	public void setHisid(String hisid) {
		this.hisid = hisid;
	}
}
