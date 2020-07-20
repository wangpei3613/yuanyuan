package com.sensebling.ope.core.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.ope.core.entity.OpeCoreModel;

public interface OpeCoreModelSvc extends BasicsSvc<OpeCoreModel>{
	/**
	 * 查询模型列表
	 * @param qp
	 * @return
	 */
	Pager getModelPager(QueryParameter qp);
	/**
	 * 判断模型code是否存在相同的 
	 * @param code 模型编码
	 * @param id 排除的id 
	 * @return 返回true代表没有相同的
	 */
	boolean checkCode(String code, String id);
	/**
	 * 模型分配指标版本
	 * @param id
	 * @param versionid
	 * @return
	 */
	Result updateVersion(String id, String versionid);

}
