package com.sensebling.index.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-指标库-参数设置表
 *
 */
@Entity
@Table(name = "model_index_parameter")
@SuppressWarnings("serial")
public class IndexParameter implements Serializable {
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 指标库id **/
	private String indexid;
	/** 参数编码 **/
	private String code;
	/** 参数名称 **/
	private String name;
	/** 参数取值类别 **/
	private String type;
	/** 值类别 1数字 2字符串**/
	private String valuetype;
	/** 参数值 **/
	private String value;
	/** 排序号 **/
	private Integer sort;
	/** 备注 **/
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getValuetype() {
		return valuetype;
	}
	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}
	
}
