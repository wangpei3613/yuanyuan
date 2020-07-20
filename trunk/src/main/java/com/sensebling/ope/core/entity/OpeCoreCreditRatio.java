package com.sensebling.ope.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 信用等级系数表
 */
@Entity
@Table(name="ope_core_creditratio")
public class OpeCoreCreditRatio implements Serializable {
	   private static final long serialVersionUID = 1L;
	   @Id
	   @GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	   @GeneratedValue(generator ="uuid")// 主键生成策略
	   private String id;
	   private String code;
	   private String name;
	   private BigDecimal industryCoe;
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
	public BigDecimal getIndustryCoe() {
		return industryCoe;
	}
	public void setIndustryCoe(BigDecimal industryCoe) {
		this.industryCoe = industryCoe;
	}
	   
	   
}
