package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sen_district")
@SuppressWarnings("serial")
public class District implements Serializable {
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;
	private String code;//区县编码
	private String name;
	private String disSort;//序号
	private String cityCode;//城市编码



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


	public String getDisSort() {
		return disSort;
	}


	public void setDisSort(String disSort) {
		this.disSort = disSort;
	}


	public String getCityCode() {
		return cityCode;
	}


	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}




}
