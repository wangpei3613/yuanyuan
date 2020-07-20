package com.sensebling.core.allo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.core.allo.entity.CoreAlloRule;
import com.sensebling.core.allo.service.CoreAlloRuleSvc;
@Service
public class CoreAlloRuleSvcImpl extends BasicsSvcImpl<CoreAlloRule> implements CoreAlloRuleSvc{

	@Override
	public List<CoreAlloRule> getRules(String alloid) {
		String sql = "select * from sen_core_allo_rule where alloid=? and status='1' order by sort";
		return baseDao.findBySQLEntity(sql, CoreAlloRule.class.getName(), alloid);  
	}

}
