package com.sensebling.index.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-指标参数值表
 *
 */
@Entity
@Table(name = "model_index_value_parameter")
@SuppressWarnings("serial")
public class IndexValueParameter implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本指标值id **/
	private String valueindexid;
	/** 版本历史指标参数id **/
	private String parameterid;
	/** 值 **/
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValueindexid() {
		return valueindexid;
	}
	public void setValueindexid(String valueindexid) {
		this.valueindexid = valueindexid;
	}
	public String getParameterid() {
		return parameterid;
	}
	public void setParameterid(String parameterid) {
		this.parameterid = parameterid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
