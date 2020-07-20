package com.sensebling.activiti.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActConfigTache;
import com.sensebling.activiti.service.ActConfigTacheSvc;
import com.sensebling.activiti.service.ActConfigTacheTrendSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
@Service
public class ActConfigTacheSvcImpl extends BasicsSvcImpl<ActConfigTache> implements ActConfigTacheSvc{
	@Resource
	private ActConfigTacheTrendSvc actConfigTacheTrendSvc;

	@Override
	public boolean checkTachecode(String tachecode, String actid, String id) {
		String sql = "select 1 from sen_act_config_tache a where a.actid=? and a.tachecode=?";
		List<Object> param = new ArrayList<Object>();
		param.add(actid);
		param.add(tachecode);
		if(StringUtil.notBlank(id)) {
			sql += " and a.id!=?";
			param.add(id);
		}
		List<ActConfigTache> list = baseDao.findBySQLEntity(sql, param, ActConfigTache.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delBeforeCheck(String id) {
		String sql = "select * from sen_act_config_tache_trend a where a.curr_tache_id=? or a.next_tache_id=?";
		return baseDao.findSqlCount(sql, id, id) == 0;
	}

	@Override
	public Pager select(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from (select a.*,b.name actname from sen_act_config_tache a join sen_act_config_info b on b.id=a.actid) where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ActConfigTache.class.getName());
	}

}
