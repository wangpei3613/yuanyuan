package com.sensebling.index.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本指标值表
 *
 */
@Entity
@Table(name = "model_index_value_detail")
@SuppressWarnings("serial")
public class IndexValueDetail implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本值id **/
	private String valueid;
	/** 版本历史指标id **/
	private String indexid;
	/** 值 **/
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValueid() {
		return valueid;
	}
	public void setValueid(String valueid) {
		this.valueid = valueid;
	}
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
