package com.sensebling.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="sen_app_version")
public class AppVersion implements Serializable {

	/**
	 * 2018年7月10日-上午11:10:35
	 * YY
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String 	id	;	//	
	private String 	downUrls	;	//	下载地址
	private String 	currentV	;	//	当前版本
	private String 	opNo	;	//	操作人编码
	private String 	opName	;	//	操作人姓名
	private Timestamp	opTime	;	//	操作时间
	private Integer type;//0 无 1：安卓 2：苹果
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDownUrls() {
		return downUrls;
	}
	public void setDownUrls(String downUrls) {
		this.downUrls = downUrls;
	}
	public String getCurrentV() {
		return currentV;
	}
	public void setCurrentV(String currentV) {
		this.currentV = currentV;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public Timestamp getOpTime() {
		return opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
