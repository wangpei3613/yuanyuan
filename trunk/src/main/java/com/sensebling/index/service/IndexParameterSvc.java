package com.sensebling.index.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.index.entity.IndexParameter;

public interface IndexParameterSvc extends BasicsSvc<IndexParameter>{
	/**
	 * 判断指标下参数code是否存在相同的 
	 * @param code 参数编码
	 * @param indexid 指标id
	 * @param id 排除的参数id 
	 * @return 返回true代表没有相同的
	 */
	boolean checkCode(String code, String indexid, String id);

}
