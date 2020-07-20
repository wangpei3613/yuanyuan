package com.sensebling.activiti.service;

import java.util.List;

import com.sensebling.activiti.entity.ActApplyOption;
import com.sensebling.common.service.BasicsSvc;

public interface ActApplyOptionSvc extends BasicsSvc<ActApplyOption>{
	/**
	 * 查询审批记录
	 * @param applyid
	 * @return
	 */
	List<ActApplyOption> getByApplyid(String applyid);

}
