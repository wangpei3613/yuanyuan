package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.OperaCreditLog;
/**
 * 操作日志
 * @author YY
 *
 */
public interface OperaCreditLogSvc extends BasicsSvc<OperaCreditLog>{

	/**
	 * 操作日志
	 * @param obj
	 * @param oldData
	 * @param newData
	 * @param dist
	 * @throws Exception
	 * 2018年4月17日-上午9:00:52
	 * YF
	 */
	public String saveOperaCreditLog(Object obj,String oldData,String newData,String dist,String creditLogId)throws Exception;

	/**
	 * 查询修改记录
	 * @param queryParameter
	 * @return
	 * @throws Exception
	 * 2018年4月17日-下午7:09:15
	 * YF
	 */
	public Pager queryAll(QueryParameter queryParameter)throws Exception;
}
