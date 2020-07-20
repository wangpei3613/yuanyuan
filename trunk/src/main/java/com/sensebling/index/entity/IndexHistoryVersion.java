package com.sensebling.index.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本历史表
 *
 */
@Entity
@Table(name = "model_index_history_version")
@SuppressWarnings("serial")
public class IndexHistoryVersion implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本id **/
	private String versionid;
	/** 版本号 **/
	private String code;
	/** 版本名称 **/
	private String name;
	/** 创建时间 **/
	private String createdate;
	/** 备注 **/
	private String remark;
	
	@Transient
	private List<IndexHistoryDetail> indexs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
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
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<IndexHistoryDetail> getIndexs() {
		return indexs;
	}
	public void setIndexs(List<IndexHistoryDetail> indexs) {
		this.indexs = indexs;
	}
}
