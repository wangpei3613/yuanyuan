package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.LoginLog;

public interface LoginLogSvc extends BasicsSvc<LoginLog>{
	/**
	 * 判断用户当天是否第一次登陆
	 * @param id
	 * @return
	 */
	boolean isFirstLogin(String id);

}
