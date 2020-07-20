package com.sensebling.activiti.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActConfigEnableLog;
import com.sensebling.activiti.entity.ActConfigInfo;
import com.sensebling.activiti.service.ActConfigEnableLogSvc;
import com.sensebling.activiti.service.ActConfigInfoSvc;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
@Service
public class ActConfigInfoSvcImpl extends BasicsSvcImpl<ActConfigInfo> implements ActConfigInfoSvc{
	@Resource
	private ActConfigEnableLogSvc actConfigEnableLogSvc;
	@Override
	public void setEnable(ActConfigInfo info) {
		String sql = "update sen_act_config_info a set a.enabled='2' where a.processcode=?";
		baseDao.executeSQL(sql, info.getProcesscode());
		sql = "update sen_act_config_info a set a.enabled='1' where a.id=?";
		baseDao.executeSQL(sql, info.getId());
		ActConfigEnableLog log = new ActConfigEnableLog();
		log.setAddtime(DateUtil.getStringDate());
		log.setAdduser(HttpReqtRespContext.getUser().getId());
		log.setInfoid(info.getId());
		log.setProcesscode(info.getProcesscode());
		actConfigEnableLogSvc.save(log);
	}

}
