package com.sensebling.core.risk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.core.risk.entity.CoreRisk;
import com.sensebling.core.risk.entity.CoreRiskLog;
import com.sensebling.core.risk.entity.CoreRiskRule;
import com.sensebling.core.risk.service.CoreRiskLogSvc;
import com.sensebling.core.risk.service.CoreRiskRuleSvc;
import com.sensebling.core.risk.service.CoreRiskSvc;
@Service
public class CoreRiskSvcImpl extends BasicsSvcImpl<CoreRisk> implements CoreRiskSvc{
	@Resource
	private CoreRiskLogSvc coreRiskLogSvc;
	@Resource
	private CoreRiskRuleSvc coreRiskRuleSvc;
	@Override
	public void setEnable(CoreRisk risk) {
		String sql = "update sen_core_risk set status='2' where code=?";
		baseDao.executeSQL(sql, risk.getCode());
		sql = "update sen_core_risk set status='1' where id=?";
		baseDao.executeSQL(sql, risk.getId());
		CoreRiskLog log = new CoreRiskLog();
		log.setAddtime(DateUtil.getStringDate());
		log.setAdduser(HttpReqtRespContext.getUser().getId());
		log.setCode(risk.getCode());
		log.setRiskid(risk.getId());
		coreRiskLogSvc.save(log);
	}
	@Override
	public Result calcRule(String code, Map<String, String> map) {
		Result r = new Result();
		String sql = "select * from sen_core_risk where code=? and status='1'";
		List<CoreRisk> risks = baseDao.findBySQLEntity(sql, CoreRisk.class.getName(), code);
		if(risks!=null && risks.size()==1) {
			CoreRisk risk = risks.get(0);
			List<CoreRiskRule> rules = coreRiskRuleSvc.getRules(risk.getId());
			if(rules!=null && rules.size()>0) {
				for(CoreRiskRule rule:rules) {
					boolean b = true;
					if(StringUtil.notBlank(rule.getCondition())) {//判断规则条件，若满足提交则使用这条规则
						sql = StringUtil.replaceStr(rule.getCondition(), map);  
						List<Object[]> temp = baseDao.queryBySql(sql);
						if(temp==null || temp.size()==0) {
							b = false;
	     				}
					}
					if(b) {
						sql = StringUtil.replaceStr(rule.getRule(), map);
						List<Object[]> temp = baseDao.queryBySql(sql);
						if(temp!=null && temp.size()>0) {
							r.setError(StringUtil.notBlank(rule.getTitle())?rule.getTitle():"规则【"+rule.getName()+"】不通过");
							return r;
	     				}
					}
				}
				r.success();
			}else {
				r.success();//若没有启用的规则，则判断通过
			}
		}else {
			r.setError("风险模型编码【"+code+"】配置有误，未找到模型或找到多个启用的模型");  
		}
		return r;
	}
	@Override
	public Result calcRule(String code, String key, String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return calcRule(code, map);  
	}

}
