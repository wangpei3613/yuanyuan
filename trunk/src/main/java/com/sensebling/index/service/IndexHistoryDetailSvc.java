package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexHistoryDetail;

public interface IndexHistoryDetailSvc extends BasicsSvc<IndexHistoryDetail>{
	/**
	 * 查询列表
	 * @param hisid 版本历史id
	 * @return
	 */
	List<IndexHistoryDetail> getListByHisid(String hisid);

}
