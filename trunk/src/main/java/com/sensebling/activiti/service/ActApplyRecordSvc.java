package com.sensebling.activiti.service;

import java.util.List;

import com.sensebling.activiti.entity.ActApplyRecord;
import com.sensebling.common.service.BasicsSvc;

public interface ActApplyRecordSvc extends BasicsSvc<ActApplyRecord>{
	/**
	 * 查询审批记录
	 * @param applyid
	 * @return
	 */
	List<ActApplyRecord> getByApplyid(String applyid);

}
