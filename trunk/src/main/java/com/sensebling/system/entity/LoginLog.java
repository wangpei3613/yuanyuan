package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 系统登录日志
 *
 */
@Entity
@Table(name="sen_login_log")
@SuppressWarnings("serial")
public class LoginLog implements Serializable{
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")
	private String id;
	private String logintime;
	private String loginuser;
	private String type;//类别   1:pc  2:app 3:手机端扫一扫 web端登录 
	private String model;//app设备的型号
	private String imei;//app设备的国际移动设备身份码
	private String loginouttime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getLoginuser() {
		return loginuser;
	}
	public void setLoginuser(String loginuser) {
		this.loginuser = loginuser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getLoginouttime() {
		return loginouttime;
	}
	public void setLoginouttime(String loginouttime) {
		this.loginouttime = loginouttime;
	}
}
