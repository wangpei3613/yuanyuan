package com.sensebling.index.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.index.entity.IndexVersionParameter;
import com.sensebling.index.service.IndexVersionParameterSvc;
@Service
public class IndexVersionParameterSvcImpl extends BasicsSvcImpl<IndexVersionParameter> implements IndexVersionParameterSvc{

	@Override
	public Pager getParameterPager(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from v_model_index_version_parameter where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), IndexVersionParameter.class.getName());
	}

	@Override
	public boolean checkCode(String code, String indexversionid) {
		String sql = "select * from v_model_index_version_parameter where indexversionid=? and code=?";
		List<IndexVersionParameter> list = baseDao.findBySQLEntity(sql, new Object[] {indexversionid, code}, IndexVersionParameter.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public List<IndexVersionParameter> getListByVersionid(String versionid) {
		String sql = "select * from v_model_index_version_parameter where versionid=? order by indexversionid, sort";
		return baseDao.findBySQLEntity(sql, IndexVersionParameter.class.getName(), versionid);  
	}

}
