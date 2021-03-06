package com.sensebling.index.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本指标分值转换设置表
 *
 */
@Entity
@Table(name = "model_index_version_conversion")
@SuppressWarnings("serial")
public class IndexVersionConversion implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本指标id **/
	private String indexversionid;
	/** 转换名称 **/
	private String name;
	/** 转换编码 **/
	private String code;
	/** 转换分值 **/
	private BigDecimal value;
	/** 排序号 **/
	private Integer sort;
	/** 备注 **/
	private String remark;
	
	@Transient
	private String indexid;
	@Transient
	private String datatype;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndexversionid() {
		return indexversionid;
	}
	public void setIndexversionid(String indexversionid) {
		this.indexversionid = indexversionid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	
}
