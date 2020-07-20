package com.sensebling.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录二维码信息
 * @author YY
 *
 */
public class LoginTwo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date begin;//开始时间
	private Date end;//结束时间
	private String loginId;//登录工号
	private String uuids;///uuid
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUuids() {
		return uuids;
	}
	public void setUuids(String uuids) {
		this.uuids = uuids;
	}

}
