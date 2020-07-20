package com.sensebling.index.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexHistoryConversion;
import com.sensebling.index.entity.IndexHistoryDetail;
import com.sensebling.index.entity.IndexHistoryParameter;
import com.sensebling.index.entity.IndexHistoryVersion;
import com.sensebling.index.entity.IndexVersion;

public interface IndexHistoryVersionSvc extends BasicsSvc<IndexHistoryVersion>{
	/**
	 * 获取计算使用版本数据
	 * @param versionid
	 * @return
	 */
	IndexHistoryVersion getCalcData(String versionid);
	/**
	 * 新增一个历史版本   并返回计算使用版本数据
	 * @param version
	 * @return
	 */
	IndexHistoryVersion addCalcData(IndexVersion version);
	/**
	 * 组装版本历史数据
	 * @param historyVersion 版本历史
	 * @param historyDetailList 版本历史指标
	 * @param historyParameterList 版本历史指标参数
	 * @param historyConversionList 版本历史指标分值转换
	 * @return
	 */
	IndexHistoryVersion initData(IndexHistoryVersion historyVersion, List<IndexHistoryDetail> historyDetailList,
			List<IndexHistoryParameter> historyParameterList, List<IndexHistoryConversion> historyConversionList);

}
