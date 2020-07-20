package com.sensebling.activiti.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActConfigTacheTrend;
import com.sensebling.activiti.service.ActConfigTacheTrendSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
@Service
public class ActConfigTacheTrendSvcImpl extends BasicsSvcImpl<ActConfigTacheTrend> implements ActConfigTacheTrendSvc{

	@Override
	public Pager select(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from (select a.*,b.name actname,c.name curr_tache_name,d.name next_tache_name from sen_act_config_tache_trend a join sen_act_config_info b on b.id=a.actid join sen_act_config_tache c on c.id=a.curr_tache_id join sen_act_config_tache d on d.id=a.next_tache_id) where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ActConfigTacheTrend.class.getName());
	}

}
