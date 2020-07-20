package com.sensebling.activiti.service;

import com.sensebling.activiti.entity.ActConfigTacheTrend;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;

public interface ActConfigTacheTrendSvc extends BasicsSvc<ActConfigTacheTrend>{
	/**
	 * 查询流程配置环节走向列表
	 * @param qp
	 * @return
	 */
	Pager select(QueryParameter qp);

}
