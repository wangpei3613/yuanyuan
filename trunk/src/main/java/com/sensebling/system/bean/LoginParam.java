package com.sensebling.system.bean;
/**
 * 登陆相关属性
 * @author llmke
 *
 */
public class LoginParam {
	private String username;//登陆用户名
	private String password;//登陆密码
	private String logintype;//登陆类别 1：pc  2：app  3:pc扫描登陆
	private String imei;//登陆手机串号
	private String model;//登陆手机型号
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogintype() {
		return logintype;
	}
	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
}
