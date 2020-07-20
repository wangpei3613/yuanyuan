package com.sensebling.index.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-指标库表
 *
 */
@Entity
@Table(name = "model_index_library")
@SuppressWarnings("serial")
public class IndexLibrary implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 指标编码 **/
	@Column(updatable=false)
	private String code;
	/** 指标名称 **/
	private String name;
	/** 指标级别（1指标类、2指标） **/
	@Column(updatable=false)
	private String level;
	/** 所属父级id **/
	private String pid;
	/** 指标类别（1定性、2定量）  **/
	private String category;
	/** 指标取值参数 **/
	private String argument;
	/** 指标内容 **/
	private String content;
	/** 指标满分 **/
	private BigDecimal maxscore;
	/** 指标公式 **/
	private String formula;
	/** 指标状态 （1启用、0禁用）**/
	private String status;
	/** 排序号 **/
	private Integer sort;
	/** 备注 **/
	private String remark;
	/** 创建人 **/
	@Column(updatable=false)
	private String createuser;
	/** 创建时间 **/
	@Column(updatable=false)
	private String createdate;
	
	
	@Transient
	private String checked;
	@Transient
	private String indexversionid;
	@Transient
	private String versionid;
	@Transient
	private String indexid;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
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
	public BigDecimal getMaxscore() {
		return maxscore;
	}
	public void setMaxscore(BigDecimal maxscore) {
		this.maxscore = maxscore;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
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
	public String getIndexversionid() {
		return indexversionid;
	}
	public void setIndexversionid(String indexversionid) {
		this.indexversionid = indexversionid;
	}
}
