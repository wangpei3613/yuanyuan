package com.sensebling.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
/**
 * demo
 * @author YY
 *
 */
@Entity
@Table(name="sen_demo")
public class Demo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	private String id;
	@Column
	private Short colShort;
	@Column
	private Integer colInteger;
	@Column
	private Long colLong;
	@Column
	private Character colCharacter;	
	@Column(length=20)
	private String colString;
	@Column(precision=12,scale=2)
	private BigDecimal colBigDecimal;
	
	@Column
	private Date colDate;
	@Column
	private Time colTime;
	@Column
	private Timestamp colTimestamp;
	@Version
	private Integer version;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Short getColShort() {
		return colShort;
	}
	public void setColShort(Short colShort) {
		this.colShort = colShort;
	}
	public Integer getColInteger() {
		return colInteger;
	}
	public void setColInteger(Integer colInteger) {
		this.colInteger = colInteger;
	}
	public Long getColLong() {
		return colLong;
	}
	public void setColLong(Long colLong) {
		this.colLong = colLong;
	}
	public Character getColCharacter() {
		return colCharacter;
	}
	public void setColCharacter(Character colCharacter) {
		this.colCharacter = colCharacter;
	}
	public String getColString() {
		return colString;
	}
	public void setColString(String colString) {
		this.colString = colString;
	}
	public BigDecimal getColBigDecimal() {
		return colBigDecimal;
	}
	public void setColBigDecimal(BigDecimal colBigDecimal) {
		this.colBigDecimal = colBigDecimal;
	}
	public Date getColDate() {
		return colDate;
	}
	public void setColDate(Date colDate) {
		this.colDate = colDate;
	}
	public Time getColTime() {
		return colTime;
	}
	public void setColTime(Time colTime) {
		this.colTime = colTime;
	}
	public Timestamp getColTimestamp() {
		return colTimestamp;
	}
	public void setColTimestamp(Timestamp colTimestamp) {
		this.colTimestamp = colTimestamp;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}	
	
}
