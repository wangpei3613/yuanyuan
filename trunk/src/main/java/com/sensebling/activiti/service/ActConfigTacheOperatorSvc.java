package com.sensebling.activiti.service;

import com.sensebling.activiti.entity.ActConfigTacheOperator;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;

public interface ActConfigTacheOperatorSvc extends BasicsSvc<ActConfigTacheOperator>{
	/**
	 * 查询流程配置环节人员规则列表
	 * @param qp
	 * @return
	 */
	Pager select(QueryParameter qp);

}
