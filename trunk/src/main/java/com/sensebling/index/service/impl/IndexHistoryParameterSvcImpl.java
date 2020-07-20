package com.sensebling.index.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.index.entity.IndexHistoryParameter;
import com.sensebling.index.service.IndexHistoryParameterSvc;
@Service
public class IndexHistoryParameterSvcImpl extends BasicsSvcImpl<IndexHistoryParameter> implements IndexHistoryParameterSvc{

	@Override
	public List<IndexHistoryParameter> getListByHisid(String hisid) {
		String sql = "select a.* from model_index_history_parameter a where a.indexid in (select b.id from model_index_history_detail b where b.hisid=?) order by a.indexid,a.sort";
		return baseDao.findBySQLEntity(sql, IndexHistoryParameter.class.getName(), hisid);  
	}

}
