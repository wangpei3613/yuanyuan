package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexHistoryParameter;

public interface IndexHistoryParameterSvc extends BasicsSvc<IndexHistoryParameter>{
	/**
	 * 查询列表
	 * @param hisid 版本历史id
	 * @return
	 */
	List<IndexHistoryParameter> getListByHisid(String hisid);

}
