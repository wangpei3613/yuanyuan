package com.sensebling.index.service;

import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexLibrary;

public interface IndexLibrarySvc extends BasicsSvc<IndexLibrary>{
	/**
	 * 返回序列值（使用序列：S_MODEL_INDEX）
	 * @return
	 */
	String getNextSeq();
	/**
	 * 查询版本指标
	 * @param versionid 版本id
	 * @return
	 */
	List<IndexLibrary> getVersionIndex(String versionid);
	/**
	 * 查询版本指标(只查询启用的指标)
	 * @param versionid 版本id
	 * @return
	 */
	List<IndexLibrary> getVersionIndexEnable(String versionid);
	/**
	 * 拼装指标树数据
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> getTreeGrid(List<IndexLibrary> list);
	/**
	 * 查询版本待选择指标树
	 * @param versionid 版本id
	 * @return
	 */
	List<IndexLibrary> getIndexToVersionTree(String versionid);
	/**
	 * 删除指标 同时级联删除参数和分值转换
	 * @param id
	 */
	void delLibrary(String id);

}
