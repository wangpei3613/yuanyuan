package com.sensebling.core.risk.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.core.risk.entity.CoreRiskRule;

public interface CoreRiskRuleSvc extends BasicsSvc<CoreRiskRule>{
	/**
	 * 查询模型规则
	 * @param riskid 模型id
	 * @return
	 */
	List<CoreRiskRule> getRules(String riskid);

}
