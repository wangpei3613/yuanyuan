package com.sensebling.index.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexConversion;

public interface IndexConversionSvc extends BasicsSvc<IndexConversion>{
	/**
	 * 判断指标下分值转换code是否存在相同的 
	 * @param code 参数编码
	 * @param indexid 指标id
	 * @param id 排除的id 
	 * @return 返回true代表没有相同的
	 */
	boolean checkCode(String code, String indexid, String id);

}
