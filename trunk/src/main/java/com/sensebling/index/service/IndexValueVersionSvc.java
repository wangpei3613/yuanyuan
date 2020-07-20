package com.sensebling.index.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexValueVersion;

public interface IndexValueVersionSvc extends BasicsSvc<IndexValueVersion>{
	/**
	 * 删除指标计算数据
	 * @param caseid 关联id
	 * @param versionid 版本id
	 */
	void delData(String caseid, String versionid);
	/**
	 * 查询值数据
	 * @param caseid 关联id
	 * @param versionid 版本id
	 * @return
	 */
	IndexValueVersion getByParams(String caseid, String versionid);
	/**
	 * 删除指标计算数据
	 * @param caseid 关联id
	 */
	void delData(String caseid);

}
