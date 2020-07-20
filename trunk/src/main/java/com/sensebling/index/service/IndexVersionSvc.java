package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexVersion;

public interface IndexVersionSvc extends BasicsSvc<IndexVersion>{
	/**
	 * 判断指标版本code是否存在相同的 
	 * @param code 版本号
	 * @param id 排除的id 
	 * @return 返回true代表没有相同的
	 */
	boolean checkCode(String code, String id);
	/**
	 * 级联删除指标版本
	 * @param id
	 */
	void delVersion(String id);
	/**
	 * 版本配置设为已修改
	 * @param versionid 版本id
	 */
	void setChanged(String versionid);
	/**
	 * 版本配置设为未修改
	 * @param versionid 版本id
	 */
	void removeChanged(String versionid);
	/**
	 * 当指标库修改时，同步更新影响的指标版本的版本配置状态为已修改
	 * @param indexid 版本id
	 */
	void updateChangedByIndex(String indexid);
	/**
	 * 当指标库参数修改时，同步更新影响的指标版本的版本配置状态为已修改
	 * @param indexid 版本id
	 * @param code 参数编码
	 */
	void updateChangedByIndexParameter(String indexid, String code);
	/**
	 * 当指标库分值转换修改时，同步更新影响的指标版本的版本配置状态为已修改
	 * @param indexid 版本id
	 * @param code 分值转换编码
	 */
	void updateChangedByIndexConversion(String indexid, String code);
	
	public List<Object[]> queryBySql(String sql, Object... objects);
	public String callProcedure(String sql, Object... obj);

}
