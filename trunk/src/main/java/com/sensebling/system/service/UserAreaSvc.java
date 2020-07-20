package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.UserArea;

public interface UserAreaSvc extends BasicsSvc<UserArea> {
	public int updateUserArea(String userId, String areas,String createUserId);
}
