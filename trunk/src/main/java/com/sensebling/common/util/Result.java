package com.sensebling.common.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Result implements Serializable{

	protected String res = "-1";
	protected String error = "系统异常";
	protected String message = "系统异常";
	protected Object data;
	protected boolean success = false;
	
	public Result() {
		
	}
	public Result success() {
		this.setRes("0");   
		this.success = true;
		return this;
	}
	public Result error(String error) {
		this.error = error;
		return this;
	}
	public Result(String res,String error) {
		this.res = res;
		this.error = error;
		this.message = error;
		this.success = "0".equals(res);
	}
	public boolean isSuccess() {
		return success;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
		this.success = "0".equals(res);
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
		this.message = error;
	}
	public Object getData() {
		return data;
	}
	public Result setData(Object data) {
		this.data = data;
		return this;
	}
	public String getMessage() {
		return message;
	}
}
