package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.City;

public interface CitySvc extends BasicsSvc<City> {
	/**
	 * 根据省份编码查询对应的城市信息
	 * @param procode
	 * @return
	 */
	public List<City>  getCyByProCode(String provinceCode);
}
