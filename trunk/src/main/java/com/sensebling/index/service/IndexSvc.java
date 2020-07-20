package com.sensebling.index.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexHistoryVersion;
import com.sensebling.system.entity.User;

public interface IndexSvc extends BasicsSvc<Serializable>{
	/**
	 * 计算指标值
	 * @param caseid 关联id  业务id
	 * @param versionid 指标版本id
	 * @param unique 一个caseid是否唯一一条数据
	 */
	void calcIndex(String caseid, String versionid, boolean unique);
	/**
	 * 计算指标值
	 * @param caseid 关联id  业务id
	 * @param versionid 指标版本id
	 * @param unique 一个caseid是否唯一一条数据
	 */
	public void calcIndex(String caseid, String versionid,User user, boolean unique);
	/**
	 * 计算指标值（异步）
	 * @param caseid 关联id  业务id
	 * @param versionid 指标版本id
	 * @param unique 一个caseid是否唯一一条数据
	 */
	void calcIndexSync(String caseid, String versionid, boolean unique);
	/**
	 * 查询指标计算结果 用于页面展示
	 * @param caseid 关联id  业务id
	 * @param versionid 指标版本id
	 * @return
	 */
	IndexHistoryVersion getCalcData(String caseid, String versionid);
	/**
	 * 查询指标计算结果 用于页面展示
	 * @param caseid 关联id  业务id
	 * @param versionid 指标版本id
	 * @return
	 */
	List<Map<String,Object>> getCalcDataTree(String caseid, String versionid);
	/**
	 * 查询指标计算结果 用于页面展示
	 * 通过caseid查询versionid，若查到多个取最新一个
	 * @param caseid 关联id  业务id
	 * @return
	 */
	IndexHistoryVersion getCalcData(String caseid);
	/**
	 * 查询指标计算结果 用于页面展示
	 * 通过caseid查询versionid，若查到多个取最新一个
	 * @param caseid 关联id  业务id
	 * @return
	 */
	List<Map<String,Object>> getCalcDataTree(String caseid);
	/**
	 * 通过caseid查询versionid，若查到多个取最新一个
	 * @param caseid 关联id  业务id
	 * @return
	 */
	String getVersionid(String caseid);
}
