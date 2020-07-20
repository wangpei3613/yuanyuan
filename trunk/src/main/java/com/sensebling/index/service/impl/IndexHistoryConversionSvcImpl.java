package com.sensebling.index.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.index.entity.IndexHistoryConversion;
import com.sensebling.index.service.IndexHistoryConversionSvc;
@Service
public class IndexHistoryConversionSvcImpl extends BasicsSvcImpl<IndexHistoryConversion> implements IndexHistoryConversionSvc{
	@Override
	public List<IndexHistoryConversion> getListByHisid(String hisid) {
		String sql = "select a.* from model_index_history_conversion a where a.indexid in (select b.id from model_index_history_detail b where b.hisid=?) order by a.indexid,a.sort";
		return baseDao.findBySQLEntity(sql, IndexHistoryConversion.class.getName(), hisid);  
	}
}
