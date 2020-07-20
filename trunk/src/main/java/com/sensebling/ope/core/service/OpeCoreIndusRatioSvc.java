package com.sensebling.ope.core.service;

import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.ope.core.entity.OpeCoreIndusRatio;
/**
 * 行业系数
 *
 */
public interface OpeCoreIndusRatioSvc extends BasicsSvc<OpeCoreIndusRatio> {
	
	/**
	 * 拼装指标树数据
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> getTreeGrid(List<OpeCoreIndusRatio> list);
	
	/**
	 * 判断行业code是否存在
	 * @return
	 */
	public boolean checkCode(String code,String id);

}
