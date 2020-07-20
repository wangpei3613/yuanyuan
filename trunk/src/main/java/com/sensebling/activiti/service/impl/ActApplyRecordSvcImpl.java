package com.sensebling.activiti.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActApplyRecord;
import com.sensebling.activiti.service.ActApplyRecordSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
@Service
public class ActApplyRecordSvcImpl extends BasicsSvcImpl<ActApplyRecord> implements ActApplyRecordSvc{

	@Override
	public List<ActApplyRecord> getByApplyid(String applyid) {
		String sql = "select b.*,c.name tachename,c.tachecode from sen_act_apply_record b join sen_act_config_tache c on c.id=b.tacheid where b.applyid=? order by b.start_time asc";
		return baseDao.findBySQLEntity(sql, ActApplyRecord.class.getName(), applyid);  
	}

}
