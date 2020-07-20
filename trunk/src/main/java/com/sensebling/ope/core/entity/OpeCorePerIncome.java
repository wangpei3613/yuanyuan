package com.sensebling.ope.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 信用等级系数表
 */
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="ope_core_perincome")
public class OpeCorePerIncome implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator ="uuid")// 主键生成策略
	private String id;
	private String orgCode;
	@Transient
	private String orgName;
	@Transient
	private String orgPartCode;
	private BigDecimal perIncome;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public BigDecimal getPerIncome() {
		return perIncome;
	}
	public void setPerIncome(BigDecimal perIncome) {
		this.perIncome = perIncome;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgPartCode() {
		return orgPartCode;
	}
	public void setOrgPartCode(String orgPartCode) {
		this.orgPartCode = orgPartCode;
	}
	
	
	

}
