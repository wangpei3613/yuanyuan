package com.sensebling.index.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexVersionDetail;

public interface IndexVersionDetailSvc extends BasicsSvc<IndexVersionDetail>{
	/**
	 * 新增版本指标
	 * @param ids
	 * @param versionid  
	 */
	void addVersionIndex(String ids, String versionid);
	/**
	 * 判断版本下指标是否存在
	 * @param indexid 指标id
	 * @param versionid 版本id
	 * @return 返回true代表不存在
	 */
	boolean check(String indexid, String versionid);
	/**
	 * 级联删除版本指标
	 * @param id
	 */
	void delVersionIndex(String id);
}
