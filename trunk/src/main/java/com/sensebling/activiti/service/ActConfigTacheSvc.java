package com.sensebling.activiti.service;

import com.sensebling.activiti.entity.ActConfigTache;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;

public interface ActConfigTacheSvc extends BasicsSvc<ActConfigTache>{
	/**
	 * 判断环节编码是否重复
	 * @param tachecode 环节编码 
	 * @param actid 流程id
	 * @param id 环节id（排除项）
	 * @return true代表无重复
	 */
	boolean checkTachecode(String tachecode, String actid, String id);
	/**
	 * 判断流程是否在使用（流程环节走向中）
	 * @param id
	 * @return true代表没有使用
	 */
	boolean delBeforeCheck(String id);
	/**
	 * 查询列表
	 * @param qp
	 * @return
	 */
	Pager select(QueryParameter qp);

}
