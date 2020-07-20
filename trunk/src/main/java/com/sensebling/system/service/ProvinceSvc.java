package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.Province;

public interface ProvinceSvc extends BasicsSvc<Province> {
	/**
	 * 查询所有的省份信息
	 */
	public List<Province> getAllProvince(String proId);
}
