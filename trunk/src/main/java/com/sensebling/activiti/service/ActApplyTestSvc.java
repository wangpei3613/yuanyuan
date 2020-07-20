package com.sensebling.activiti.service;

import com.sensebling.activiti.entity.ActApplyTest;
import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.system.entity.User;

public interface ActApplyTestSvc extends BasicsSvc<ActApplyTest>{
	/**
	 * 新增流程测试业务
	 * @param apply
	 * @param user 
	 * @return
	 */
	Result addApply(ActApplyTest apply, User user);
	/**
	 * 查询业务申请列表
	 * @param qp
	 * @return
	 */
	Pager select(QueryParameter qp);
	/**
	 * 查询申请信息
	 * @param id
	 * @return
	 */
	ActApplyTest getApply(String id);
	/**
	 * 删除申请信息
	 * @param id
	 */
	Result delApply(String id);
	/**
	 * 取消申请
	 * @param id
	 * @return
	 */
	Result cancelApply(String id);
	/**
	 * 查询流程测试审批列表
	 * @return
	 */
	Pager auditList(QueryParameter qp);
	/**
	 * 提交前回调
	 * @param act
	 * @return
	 */
	Result submitCallBefore(ActAudit act);
	/**
	 * 提交后回调
	 * @param act
	 * @return
	 */
	void submitCallAfter(ActAudit act);
	/**
	 * 审批前回调
	 * @param act
	 * @return
	 */
	Result auditCallBefore(ActAudit act);
	/**
	 * 审批后回调
	 * @param act
	 * @return
	 */
	void auditCallAfter(ActAudit act);

}
