package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.District;

public interface DistSvc extends BasicsSvc<District> {
	/**
	 * 查询所有的小区信息
	 */
	public List<District> getAllDist(String code);
}
