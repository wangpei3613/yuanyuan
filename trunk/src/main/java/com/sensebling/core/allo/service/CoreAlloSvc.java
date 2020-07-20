package com.sensebling.core.allo.service;

import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Result;
import com.sensebling.core.allo.entity.CoreAllo;

public interface CoreAlloSvc extends BasicsSvc<CoreAllo>{
	/**
	 * 启用模型
	 * @param allo
	 */
	void setEnable(CoreAllo allo);
	/**
	 * 计算分配模型
	 * @param code 分配模型编码
	 * @param map sql传参，key为参数名（sql中待替换文本），value为参数值
	 * @return 若返回res=0，则data为分配的结果，结果为List集合
	 */
	Result calcRule(String code, Map<String, String> map);
	/**
	 * 计算分配模型(单个参数使用)
	 * @param code 风险模型编码
	 * @param key sql中待替换文本
	 * @param value 参数值
	 * @return 若返回res=0，则data为分配的结果，结果为List集合
	 */
	Result calcRule(String code, String key, String value);
	/**
	 * 计算分配模型
	 * @param code 分配模型编码
	 * @param map sql传参，key为参数名（sql中待替换文本），value为参数值
	 * @return 若返回res=0，则data为分配的结果，结果为List集合中随机取一个对象
	 */
	Result calcRuleRandom(String code, Map<String, String> map);
	/**
	 * 计算分配模型(单个参数使用)
	 * @param code 分配模型编码
	 * @param key sql中待替换文本
	 * @param value 参数值
	 * @return 若返回res=0，则data为分配的结果，结果为List集合中随机取一个对象
	 */
	Result calcRuleRandom(String code, String key, String value);
}
