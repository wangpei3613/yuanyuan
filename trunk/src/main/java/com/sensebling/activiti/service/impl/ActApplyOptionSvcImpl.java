package com.sensebling.activiti.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActApplyOption;
import com.sensebling.activiti.service.ActApplyOptionSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
@Service
public class ActApplyOptionSvcImpl extends BasicsSvcImpl<ActApplyOption> implements ActApplyOptionSvc{

	@Override
	public List<ActApplyOption> getByApplyid(String applyid) {
		String sql = "select a.*,c.nickname,c.username from sen_act_apply_option a join sen_user c on c.id=a.add_user where a.record_id in (select b.id from sen_act_apply_record b where b.applyid=?) order by a.add_time asc";
		return baseDao.findBySQLEntity(sql, ActApplyOption.class.getName(), applyid);  
	}

}
