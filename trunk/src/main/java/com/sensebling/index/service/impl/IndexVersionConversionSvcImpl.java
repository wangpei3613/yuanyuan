package com.sensebling.index.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.index.entity.IndexVersionConversion;
import com.sensebling.index.service.IndexVersionConversionSvc;
@Service
public class IndexVersionConversionSvcImpl extends BasicsSvcImpl<IndexVersionConversion> implements IndexVersionConversionSvc{

	@Override
	public Pager getConversionPager(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from v_model_index_version_conversion where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), IndexVersionConversion.class.getName());
	}

	@Override
	public boolean checkCode(String code, String indexversionid) {
		String sql = "select 1 from v_model_index_version_conversion where indexversionid=? and code=?";
		List<IndexVersionConversion> list = baseDao.findBySQLEntity(sql, new Object[] {indexversionid, code}, IndexVersionConversion.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public List<IndexVersionConversion> getListByVersionid(String versionid) {
		String sql = "select * from v_model_index_version_conversion where versionid=? order by indexversionid, sort";
		return baseDao.findBySQLEntity(sql, IndexVersionConversion.class.getName(), versionid);  
	}
}
