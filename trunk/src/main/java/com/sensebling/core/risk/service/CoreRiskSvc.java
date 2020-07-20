package com.sensebling.core.risk.service;

import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Result;
import com.sensebling.core.risk.entity.CoreRisk;

public interface CoreRiskSvc extends BasicsSvc<CoreRisk>{
	/**
	 * 启用风险模型
	 * @param risk
	 */
	void setEnable(CoreRisk risk);
	/**
	 * 计算风险模型
	 * @param code 风险模型编码
	 * @param map sql传参，key为参数名（sql中待替换文本），value为参数值
	 * @return
	 */
	Result calcRule(String code, Map<String, String> map);
	/**
	 * 计算风险模型(单个参数使用)
	 * @param code 风险模型编码
	 * @param key sql中待替换文本
	 * @param value 参数值
	 * @return
	 */
	Result calcRule(String code, String key, String value);

}
