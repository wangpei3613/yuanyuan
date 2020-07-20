package com.sensebling.activiti.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActApplyTransferRecord;
import com.sensebling.activiti.service.ActApplyTransferRecordSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
@Service
public class ActApplyTransferRecordSvcImpl extends BasicsSvcImpl<ActApplyTransferRecord> implements ActApplyTransferRecordSvc{

	@Override
	public List<ActApplyTransferRecord> getByApplyid(String applyid) {
		String sql = "SELECT a.*, c.nickname receive_nickname, c.username receive_username,d.nickname transfer_nickname,d.username transfer_username FROM sen_act_apply_transfer_record a JOIN sen_user c ON c.id=a.receive_userid join sen_user d on d.id=a.transfer_userid WHERE a.record_id IN( SELECT b.id FROM sen_act_apply_record b WHERE b.applyid=?) ORDER BY a.add_time ASC";
		return baseDao.findBySQLEntity(sql, ActApplyTransferRecord.class.getName(), applyid);  
	}

}
