package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexHistoryConversion;

public interface IndexHistoryConversionSvc extends BasicsSvc<IndexHistoryConversion>{
	/**
	 * 查询列表
	 * @param hisid 版本历史id
	 * @return
	 */
	List<IndexHistoryConversion> getListByHisid(String hisid);

}
