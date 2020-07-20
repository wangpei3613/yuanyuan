package com.sensebling.common.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pager {
	/**默认每页数据条数*/
	@JsonIgnore
	private static final int DEFAULT_PAGE_SIZE = 10;
	/**默认当前页码*/
	@JsonIgnore
	private static final int DEFAULT_CURRENT_PAGE = 1;
	/**每页的数据条数*/
	@JsonIgnore
	protected int pageSize;
	/**当前页码*/
	@JsonIgnore
	protected int pageIndex;
	/**总记录条数*/
	protected int records;//总记录数
	/**总页数*/
	protected int total;//总页数
	/**汇总数据(jqgrid使用)*/
	protected Object userdata;
	/**此分页使用的查询sql(目前使用hibernate,此参数无值)*/
	@JsonIgnore
	protected String querySql;
	/**分页查询的数据集合*/
	protected List<?> rows;

	public Pager(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		if(pageIndex < 0){
			this.pageIndex = 1;
		}
		
		this.pageSize = pageSize;
	}

	public Pager(int pageIndex) {
		this.pageIndex = pageIndex;
		if(pageIndex < 0){
			this.pageIndex = 1;
		}
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	public Pager() {
		this.pageIndex = DEFAULT_CURRENT_PAGE;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}


	public String getQuerySql() {
		return querySql;
	}

	public List<?> getRows() {
		return rows;
	}
	public void setRow(List<?> row) {
		this.rows = row;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	@JsonIgnore
	public int getFirstIndex() {
		return pageSize * (pageIndex-1);
	}

	public boolean hasPrevious() {
		return pageIndex > 1;
	}

	public boolean hasNext() {
		return pageIndex < getTotal();
	}

	public int getRecords() {
		if (pageSize == -1)
			return rows == null ? 0 : rows.size();
		else
			return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getPage() {
		return pageIndex;
	}

	public int getTotal() {

		long remainder = records % this.getPageSize();

		if (0 == remainder) {
			return records / this.getPageSize();
		}

		return records / this.getPageSize() + 1;
	}

	public Object getUserdata() {
		return userdata;
	}

	public void setUserdata(Object userdata) {
		this.userdata = userdata;
	}
}
