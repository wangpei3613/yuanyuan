package com.sensebling.activiti.service;

import java.util.List;

import com.sensebling.activiti.entity.ActApplyTransferRecord;
import com.sensebling.common.service.BasicsSvc;

public interface ActApplyTransferRecordSvc extends BasicsSvc<ActApplyTransferRecord>{
	/**
	 * 查询移交记录
	 * @param applyid
	 * @return
	 */
	List<ActApplyTransferRecord> getByApplyid(String applyid);

}
