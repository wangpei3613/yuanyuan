package com.sensebling.core.allo.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.core.allo.entity.CoreAlloRule;

public interface CoreAlloRuleSvc extends BasicsSvc<CoreAlloRule>{
	/**
	 * 查询模型分配规则
	 * @param alloid
	 * @return
	 */
	List<CoreAlloRule> getRules(String alloid);

}
