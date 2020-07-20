package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.District;
import com.sensebling.system.service.DistSvc;

@Service("distSvc")
public class DistSvcImpl extends BasicsSvcImpl<District> implements DistSvc {
	/**
	 * 查询所有的小区信息
	 */
	public List<District> getAllDist(String code) {
		StringBuffer hql=new StringBuffer("from District where 1=1");
		List<Object> list=new ArrayList<Object>();
		if(!StringUtil.isBlank(code)){
			hql.append(" and cityCode=?");
			list.add(code);
		}
		return baseDao.find(hql.toString(),list);
	}
}
