package com.sensebling.index.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.index.entity.IndexHistoryDetail;
import com.sensebling.index.service.IndexHistoryDetailSvc;
@Service
public class IndexHistoryDetailSvcImpl extends BasicsSvcImpl<IndexHistoryDetail> implements IndexHistoryDetailSvc{

	@Override
	public List<IndexHistoryDetail> getListByHisid(String hisid) {
		String sql = "select * from model_index_history_detail where hisid=? order by sort";
		return baseDao.findBySQLEntity(sql, IndexHistoryDetail.class.getName(), hisid);   
	}

}
