package com.sensebling.activiti.service;

import java.util.List;

import com.sensebling.activiti.entity.ActApplyInfo;
import com.sensebling.activiti.entity.ActApplyOption;
import com.sensebling.activiti.entity.ActApplyRecord;
import com.sensebling.activiti.entity.ActApplyTache;
import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.activiti.entity.ActConfigInfo;
import com.sensebling.activiti.entity.ActConfigTache;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Result;
import com.sensebling.system.entity.User;

public interface ActApplyInfoSvc extends BasicsSvc<ActApplyInfo>{
	/**
	 * 判断流程配置是否正确
	 * @param processcode 流程编码
	 * @return true为配置正确
	 */
	boolean checkActivitiConfig(String processcode);
	/**
	 * 获取流程的第一个环节
	 * @param actid 流程id
	 * @return
	 */
	public ActConfigTache getFirstTache(String actid);
	/**
	 * 新增申请
	 * @param caseid 关联id
	 * @param processcode 流程编码
	 * @param user
	 * @return
	 */
	ActApplyInfo newApply(String caseid, String processcode, User user);
	/**
	 * 根据流程编码获取流程配置信息
	 * @param processcode 流程编码
	 * @return
	 */
	public ActConfigInfo getConfigByProcesscode(String processcode);
	/**
	 * 获取走向环节的id集合，逗号分隔
	 * @param tacheid 当前环节id
	 * @param applyid 申请id
	 * @param user
	 * @param trend_type 走向类别   1前进  2后退
	 * @return
	 */
	public String getTrendTacheids(String tacheid, String applyid, User user, String trend_type);
	/**
	 * 获取走向环节
	 * @param tacheid 当前环节id
	 * @param applyid 申请id
	 * @param user
	 * @param trend_type 走向类别   1前进  2后退
	 * @return
	 */
	public List<ActConfigTache> getTrendTache(String tacheid, String applyid, User user, String trend_type);
	/**
	 * 删除申请
	 * @param applyid 申请id
	 */
	void delApply(String applyid);
	/**
	 * 取消申请
	 * @param applyid 申请id
	 */
	void cancelApply(String applyid);
	/**
	 * 更新流程状态
	 * @param applyid 申请id
	 * @param state 状态
	 */
	void updateApplyStatus(String applyid, String state);
	/**
	 * 删除申请环节
	 * @param applyid 申请id
	 */
	void delApplyTache(String applyid);
	/**
	 * 查询流程申请记录
	 * @param applyid 申请id
	 * @return
	 */
	List<ActApplyRecord> getApplyRecord(String applyid);
	/**
	 * 新增流程申请审批结果
	 * @param options 审批意见
	 * @param record_id s 审批记录id
	 * @param result 审核结果   1同意 2复议 3否决 4取消
	 * @param sign_data 签名数据
	 * @return
	 */
	ActApplyOption addApplyOption(String options, String record_id, String result, String sign_data);
	/**
	 * 查询当前环节审批人 
	 * @param applyid 申请id
	 * @return
	 */
	List<User> getAuditMen(String applyid);
	/**
	 * 查询流程申请环节
	 * @param applyid 申请id
	 * @return
	 */
	List<ActApplyTache> getApplyTache(String applyid);
	/**
	 * 查询环节审批人员
	 * @param tacheid 环节id
	 * @param apply 申请id
	 * @param category 规则人员类别  1审批人员  2移交人员
	 * @return
	 */
	List<User> getApplyTacheUsers(String tacheid, String applyid, String category);
	/**
	 * 查询环节审批人员
	 * @param tacheid 环节id
	 * @param apply 申请id
	 * @param category 规则人员类别  1审批人员  2移交人员
	 * @return
	 */
	String getApplyTacheUserids(String tacheid, String applyid, String category);
	/**
	 * 查询审批记录结果信息
	 * @param recordid 审批记录id
	 * @return
	 */
	List<ActApplyOption> getApplyOptions(String recordid);
	/**
	 * 取消申请记录
	 * @param applyid
	 */
	void cancelApplyRecord(String applyid);
	/**
	 * 流程处理
	 * @param act 流程处理数据
	 * @param r 
	 */
	Result handleActiviti(ActAudit act, Result r) throws Exception;
	/**
	 * 获取审批记录
	 * @param applyid 申请id
	 * @return
	 */
	List<ActApplyRecord> getActivitiRecords(String applyid);
	/**
	 * 更新环节记录结果
	 * @param result
	 * @param id
	 */
	void updateApplyRecordResult(String result, String id);
	/**
	 * 移交处理
	 * @param act 流程处理数据
	 * @param r
	 */
	Result handleTransfer(ActAudit act, Result r) throws Exception;
	
	/**
	 * 根据caseid 查询
	 * @param caseId
	 * @return
	 * @throws Exception
	 * 2018年8月2日-上午8:18:57
	 * YY
	 */
	ActApplyInfo getActApplyInfoBycaseId(String caseId)throws Exception;
	/**
	 * 更新申请结束时间为当前时间
	 * @param applyid
	 */
	void updateApplyEndDate(String applyid);
	/**
	 * 新增并且同时提交申请
	 * @param caseid 关联id
	 * @param processcode 流程编码
	 * @param user
	 * @return
	 */
	Result newApplyAndSubmit(String caseid, String processcode, User user,Result r,String rstate)throws Exception;
}
