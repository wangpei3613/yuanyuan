package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.index.entity.IndexVersionParameter;

public interface IndexVersionParameterSvc extends BasicsSvc<IndexVersionParameter>{
	/**
	 * 查询版本指标参数列表
	 * @param qp
	 * @return
	 */
	Pager getParameterPager(QueryParameter qp);
	/**
	 * 判断版本指标参数编码是否存在
	 * @param code 编码
	 * @param indexversionid 版本指标id
	 * @return true代表不存在
	 */
	boolean checkCode(String code, String indexversionid);
	/**
	 * 查询列表 通过版本id
	 * @param versionid
	 * @return
	 */
	List<IndexVersionParameter> getListByVersionid(String versionid);

}
