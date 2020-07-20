package com.sensebling.core.allo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.core.allo.entity.CoreAllo;
import com.sensebling.core.allo.entity.CoreAlloLog;
import com.sensebling.core.allo.entity.CoreAlloRule;
import com.sensebling.core.allo.service.CoreAlloLogSvc;
import com.sensebling.core.allo.service.CoreAlloRuleSvc;
import com.sensebling.core.allo.service.CoreAlloSvc;
@Service
public class CoreAlloSvcImpl extends BasicsSvcImpl<CoreAllo> implements CoreAlloSvc{
	@Resource
	private CoreAlloLogSvc coreAlloLogSvc;
	@Resource
	private CoreAlloRuleSvc coreAlloRuleSvc;

	@Override
	public void setEnable(CoreAllo allo) {
		String sql = "update sen_core_allo set status='2' where code=?";
		baseDao.executeSQL(sql, allo.getCode());
		sql = "update sen_core_allo set status='1' where id=?";
		baseDao.executeSQL(sql, allo.getId());
		CoreAlloLog log = new CoreAlloLog();
		log.setAddtime(DateUtil.getStringDate());
		log.setAdduser(HttpReqtRespContext.getUser().getId());
		log.setCode(allo.getCode());
		log.setAlloid(allo.getId());
		coreAlloLogSvc.save(log);
	}

	@Override
	public Result calcRule(String code, Map<String, String> map) {
		Result r = new Result();
		r.setError("未找到符合规则对象"); 
		String sql = "select * from sen_core_allo where code=? and status='1'";
		List<CoreAllo> allos = baseDao.findBySQLEntity(sql, CoreAllo.class.getName(), code);
		if(allos != null && allos.size()==1) {
			CoreAllo allo = allos.get(0);
			List<CoreAlloRule> rules = coreAlloRuleSvc.getRules(allo.getId());
			if(rules!=null && rules.size()>0) {
				Set<String> obj1 = null;
				Set<String> obj2 = null;
				for(CoreAlloRule rule:rules) {
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
							obj2 = new HashSet<String>();
							for(int i=0;i<temp.size();i++) {
								obj2.add(String.valueOf(temp.get(i)).trim());
							}
	     				}else {
	     					r.setError("分配规则【"+rule.getCode()+"】未找到符合规则对象");
	     					return r;
	     				}
						if(obj1 == null) {
							obj1 = obj2;
						}else {
							obj1.retainAll(obj2);
						}
						if("1".equals(rule.getIsend())) {//若是结束规则
							if(obj1!=null && obj1.size()>0) {
								r.setData(new ArrayList<String>(obj1));  
								r.success();
								return r;
							}
						}
					}
				}
				if(obj1!=null && obj1.size()>0) {
					r.setData(new ArrayList<String>(obj1));  
					r.success();
					return r;
				}
			}else {
				r.setError("分配模型编码【"+code+"】配置有误，未找到启用的分配规则"); 
			}
		}else {
			r.setError("分配模型编码【"+code+"】配置有误，未找到模型或找到多个启用的模型"); 
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result calcRuleRandom(String code, Map<String, String> map) {
		Result r = calcRule(code, map);
		if(r.isSuccess()) {
			List<String> list = (List<String>)r.getData();
			r.setData(list.get(new Random().nextInt(list.size())));
		}
		return r;
	}

	@Override
	public Result calcRule(String code, String key, String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return calcRule(code, map);
	}

	@Override
	public Result calcRuleRandom(String code, String key, String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return calcRuleRandom(code, map);  
	}

}
