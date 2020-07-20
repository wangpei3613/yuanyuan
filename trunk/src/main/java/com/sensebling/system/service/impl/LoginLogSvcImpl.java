package com.sensebling.system.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.LoginLog;
import com.sensebling.system.service.LoginLogSvc;
@Service
public class LoginLogSvcImpl extends BasicsSvcImpl<LoginLog> implements LoginLogSvc{

	@Override
	public boolean isFirstLogin(String id) {
		QueryParameter qp = new QueryParameter();
		qp.addParamter("loginuser", id);
		qp.addParamter("logintime", DateUtil.getStringDateShort(), QueryCondition.like_start);
		return findAllCount(qp) == 1;
	}
}
