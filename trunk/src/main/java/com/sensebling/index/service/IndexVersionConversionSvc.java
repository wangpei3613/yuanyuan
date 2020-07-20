package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.index.entity.IndexVersionConversion;

public interface IndexVersionConversionSvc extends BasicsSvc<IndexVersionConversion>{
	/**
	 * 查询版本指标分值转换列表
	 * @param qp
	 * @return
	 */
	Pager getConversionPager(QueryParameter qp);
	/**
	 * 判断版本指标转换编码是否存在
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
	List<IndexVersionConversion> getListByVersionid(String id);

}
