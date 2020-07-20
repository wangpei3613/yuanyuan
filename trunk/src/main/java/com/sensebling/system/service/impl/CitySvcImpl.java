package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.City;
import com.sensebling.system.service.CitySvc;


@Service("citySvc")
public class CitySvcImpl extends BasicsSvcImpl<City> implements CitySvc {
	/**
	 * 根据省份编码查询对应的城市信息
	 * @param procode
	 * @return
	 */
	public List<City>  getCyByProCode(String provinceCode){
		StringBuffer hql=new StringBuffer("from City where 1=1");
		List<Object> list=new ArrayList<Object>();
		if(!StringUtil.isBlank(provinceCode)){
			hql.append(" and provinceCode=?");
			list.add(provinceCode);
		}
		return baseDao.find(hql.toString(),list);
	}
}
