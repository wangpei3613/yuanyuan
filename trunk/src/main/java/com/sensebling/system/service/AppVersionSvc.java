package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.AppVersion;
import com.sensebling.system.entity.User;

public interface AppVersionSvc extends BasicsSvc<AppVersion> {

	/**
	 * 新增与修改
	 * @param d
	 * @throws Exception
	 * 2018年7月10日-上午11:29:59
	 * YY
	 */
	void addAppVersion(AppVersion d,User u)throws Exception;

	/**
	 * 按照时间倒叙获得一条信息
	 * @return
	 * @throws Exception
	 * 2018年7月11日-下午2:08:53
	 * YY
	 */
	AppVersion getListByTime(int type)throws Exception;
}
