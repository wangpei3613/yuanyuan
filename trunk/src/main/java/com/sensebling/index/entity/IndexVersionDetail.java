package com.sensebling.index.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本指标表
 *
 */
@Entity
@Table(name = "model_index_version_detail")
@SuppressWarnings("serial")
public class IndexVersionDetail implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本ID **/
	private String versionid;
	/** 模型指标ID（指标库指标id） **/
	private String indexid;
	/** 指标取值参数 **/
	private String argument;
	/** 指标内容 **/
	private String content;
	/** 指标满分 **/
	private BigDecimal maxscore;
	/** 指标公式 **/
	private String formula;
	/** 备注 **/
	private String remark;
	/** 状态（1启用、0禁用） **/
	private String status;
	/** 排序 **/
	private Integer sort;
	/** 创建时间 **/
	@Column(updatable=false)
	private String createdate;
	/** 创建人 **/
	@Column(updatable=false)
	private String createuser;
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
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public BigDecimal getMaxscore() {
		return maxscore;
	}
	public void setMaxscore(BigDecimal maxscore) {
		this.maxscore = maxscore;
	}
	
	
}
