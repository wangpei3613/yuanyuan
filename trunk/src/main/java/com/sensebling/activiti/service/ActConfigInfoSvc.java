package com.sensebling.activiti.service;

import com.sensebling.activiti.entity.ActConfigInfo;
import com.sensebling.common.service.BasicsSvc;

public interface ActConfigInfoSvc extends BasicsSvc<ActConfigInfo>{
	/**
	 * 启用流程
	 * @param info
	 */
	void setEnable(ActConfigInfo info);

}
