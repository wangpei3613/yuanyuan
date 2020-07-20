package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Province;
import com.sensebling.system.service.ProvinceSvc;
@Service("provinceSvc")
public class ProvinceSvcImpl extends BasicsSvcImpl<Province> implements
		ProvinceSvc {
	/**
	 * 查询所有的省份信息
	 */
	public List<Province> getAllProvince(String proId) {
		StringBuffer hql=new StringBuffer("from Province where 1=1");
		List<Object> list=new ArrayList<Object>();
		if(!StringUtil.isBlank(proId)){
			hql.append(" and code=?");
			list.add(proId);
		}
		return baseDao.find(hql.toString(),list);
	}
}
