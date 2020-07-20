package com.sensebling.common.util;

import java.io.Serializable;

import javax.persistence.Transient;
/**
 * 前台调用共通返回bean
 * @author  
 *
 */
public class ResultBean implements Serializable{
	public ResultBean() {
		success = false;
		msg = "";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 前台调用是否成功
	 */
	private boolean success;
	/**
	 * 返回信息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private Object data;
	/**
	 * 一共有多少条数据
	 */
//	@Transient
//	private long total = 0;
	/**
	 *当前页
	 */
//	@Transient
//	protected int page;
	/**
	 *总页数
	 */
//	@Transient
//	protected int pageTotal;
//	/**
//	 * 数据列表
//	 */
	@Transient
	private String code;//nixiang  主要用来返回主键或者其他参数
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

//	private List<Map<String, Object>> rows;
//	private List<Map<String, Object>> footer;
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
//	public long getTotal() {
//		return total;
//	}
//	public void setTotal(long total) {
//		this.total = total;
//	}
//	
//	public int getPage() {
//		return page;
//	}
//	public void setPage(int page) {
//		this.page = page;
//	}
	
//	public int getPageTotal() {
//		return pageTotal;
//	}
//	public void setPageTotal(int pageTotal) {
//		this.pageTotal = pageTotal;
//	}
	
//	public List<Map<String, Object>> getRows() {
//		return rows;
//	}
//	public void setRows(List<Map<String, Object>> rows) {	
//		if(rows == null)this.rows = new ArrayList<Map<String,Object>>();
//		else this.rows = rows;
//	}
//	public List<Map<String, Object>> getFooter() {
//		return footer;
//	}
//	public void setFooter(List<Map<String, Object>> footer) {
//		if(footer == null)this.footer = new ArrayList<Map<String,Object>>();
//		else this.footer = footer;
//	}
}
