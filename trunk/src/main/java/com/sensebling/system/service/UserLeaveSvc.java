package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.UserLeave;

public interface UserLeaveSvc extends BasicsSvc<UserLeave>{
	/**
	 * 分页查询请假记录
	 * @param qp
	 * @return
	 */
	Pager getPager(QueryParameter qp);

}
