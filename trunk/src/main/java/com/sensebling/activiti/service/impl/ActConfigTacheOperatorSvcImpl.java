package com.sensebling.activiti.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActConfigTacheOperator;
import com.sensebling.activiti.service.ActConfigTacheOperatorSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
@Service
public class ActConfigTacheOperatorSvcImpl extends BasicsSvcImpl<ActConfigTacheOperator> implements ActConfigTacheOperatorSvc{

	@Override
	public Pager select(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from (select a.*,b.name tachename,c.name actname,nvl(s1.nickname,s2.rolename,s3.fullname,a.value) values from sen_act_config_tache_operator a join sen_act_config_tache b on b.id=a.tacheid join sen_act_config_info c on c.id=b.actid left join sen_user s1 on s1.id=a.value and a.type=1 left join sen_role s2 on s2.id=a.value and a.type=2 left join sen_department s3 on s3.id=a.value and a.type=3) where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ActConfigTacheOperator.class.getName());
	}

}
