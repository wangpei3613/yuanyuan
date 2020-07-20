package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 区域
 * @author Administrator
 *
 */
@Entity
@Table(name="sen_area")
@SuppressWarnings("serial")
public class Area implements Serializable {
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;//主键

	@Column(name = "areaname")
	private String areaname;
	@Column(name = "areaLevel")
	private int areaLevel;//区域等级
	@Column(name = "code")
	private String code;//编码
	@Column(name = "parentcode")
	private String parentcode;//父级名称
	@Column(name = "bz")
	private String bz;//备注
	
	public int getAreaLevel() {
		return areaLevel;
	}
	public void setAreaLevel(int areaLevel) {
		this.areaLevel = areaLevel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getParentcode() {
		return parentcode;
	}
	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
}
