package com.sensebling.ope.core.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.ope.core.entity.OpeCorePerIncome;

public interface OpeCorePerIncomeSvc extends BasicsSvc<OpeCorePerIncome> {

	/**
	 * 获取档期人均收入
	 * @param org
	 * @return
	 */
	public OpeCorePerIncome getPerIncome(String org);
	
	/**
	 * 当地人均收入配置
	 * @param qp
	 * @return
	 */
	public List<OpeCorePerIncome> queryPerIncome(QueryParameter qp);
	
	/**
	 * 配置支行人均收入
	 * @param p
	 * @return
	 */
	public String doSavePerIncome(OpeCorePerIncome p);
}
