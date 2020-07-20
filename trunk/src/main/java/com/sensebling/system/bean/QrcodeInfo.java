package com.sensebling.system.bean;

import java.io.Serializable;

/**
 * 二维码登陆获取信息
 * @author llmke
 *
 */
public class QrcodeInfo implements Serializable{
	private static final long serialVersionUID = -7178993074312698892L;
	private String id;//用户id
	private String username;//员工号
	private String nickname;//姓名
	private String result = "0";//登陆状态  0未扫描  1已扫描  2成功登陆  3取消登陆  9失效
	private String token;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
