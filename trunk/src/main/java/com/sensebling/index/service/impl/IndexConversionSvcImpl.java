package com.sensebling.index.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexConversion;
import com.sensebling.index.service.IndexConversionSvc;
@Service
public class IndexConversionSvcImpl extends BasicsSvcImpl<IndexConversion> implements IndexConversionSvc{

	@Override
	public boolean checkCode(String code, String indexid, String id) {
		String sql = "select 1 from model_index_conversion where indexid=? and code=?";
		List<Object> param = new ArrayList<Object>();
		param.add(indexid);
		param.add(code);
		if(StringUtil.notBlank(id)) {
			sql += " and id!=?";
			param.add(id);
		}
		List<IndexConversion> list = baseDao.findBySQLEntity(sql, param, IndexConversion.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

}
