package com.sensebling.core.risk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.core.risk.entity.CoreRiskRule;
import com.sensebling.core.risk.service.CoreRiskRuleSvc;
@Service
public class CoreRiskRuleSvcImpl extends BasicsSvcImpl<CoreRiskRule> implements CoreRiskRuleSvc{

	@Override
	public List<CoreRiskRule> getRules(String riskid) {
		String sql = "select * from sen_core_risk_rule where riskid=? and status='1' order by sort";
		return baseDao.findBySQLEntity(sql, CoreRiskRule.class.getName(), riskid);
	}

}
