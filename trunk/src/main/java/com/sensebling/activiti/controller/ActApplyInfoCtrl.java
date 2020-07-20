package com.sensebling.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.activiti.entity.ActApplyInfo;
import com.sensebling.activiti.entity.ActApplyOption;
import com.sensebling.activiti.entity.ActApplyRecord;
import com.sensebling.activiti.entity.ActApplyTache;
import com.sensebling.activiti.entity.ActApplyTransferRecord;
import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.activiti.entity.ActConfigTache;
import com.sensebling.activiti.service.ActApplyInfoSvc;
import com.sensebling.activiti.service.ActApplyOptionSvc;
import com.sensebling.activiti.service.ActApplyRecordSvc;
import com.sensebling.activiti.service.ActApplyTacheSvc;
import com.sensebling.activiti.service.ActApplyTransferRecordSvc;
import com.sensebling.activiti.service.ActConfigInfoSvc;
import com.sensebling.activiti.service.ActConfigTacheSvc;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.UserSvc;
/**
 * 流程申请
 *
 */
@Controller
@RequestMapping("/act/apply/info")
public class ActApplyInfoCtrl extends BasicsCtrl{
	@Resource
	private ActApplyInfoSvc actApplyInfoSvc;
	@Resource
	private ActConfigInfoSvc actConfigInfoSvc;
	@Resource
	private ActApplyRecordSvc actApplyRecordSvc;
	@Resource
	private ActApplyTacheSvc actApplyTacheSvc;
	@Resource
	private ActApplyOptionSvc actApplyOptionSvc;
	@Resource
	private ActConfigTacheSvc actConfigTacheSvc;
	@Resource
	private UserSvc userSvc;
	@Resource
	private ActApplyTransferRecordSvc actApplyTransferRecordSvc;
	
	/**
	 * 查看当前环节审批人
	 * @param applyid
	 * @return
	 */
	@RequestMapping(value="/getAuditMen")
	@ResponseBody
	public Result getAuditMen(String applyid) {
		Result r = new Result();
		r.setData(actApplyInfoSvc.getAuditMen(applyid));
		r.success();
		return crudError(r);
	}
	/**
	 * 进入提交申请并获取提交数据
	 * @param applyid
	 * @return
	 */
	@RequestMapping(value="/getApplySubmitData")
	@ResponseBody
	public Result getApplySubmitData(String applyid) {
		Result r = new Result();
		ActApplyInfo apply = actApplyInfoSvc.get(applyid);
		if(apply != null) {
			if(ProtocolConstant.activitiApplyState.state1.equals(apply.getAudit_state()) 
					|| ProtocolConstant.activitiApplyState.state4.equals(apply.getAudit_state())) {
				if(apply.getApplman().equals(getUser().getId())) { 
					List<ActApplyTache> list = actApplyInfoSvc.getApplyTache(applyid);
					if(list!=null && list.size()==1) {
						ActApplyTache applyTache = list.get(0);
						ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
						List<ActConfigTache> nextTache = actApplyInfoSvc.getTrendTache(tache.getId(), applyid, getUser(), "1");
						if(nextTache!=null && nextTache.size()>0) {
							for(ActConfigTache temp:nextTache) {
								if(!"4".equals(temp.getTache_type())) {  
									List<User> users = actApplyInfoSvc.getApplyTacheUsers(temp.getId(), applyid, "1");
									if(users!=null && users.size()>0) {
										temp.setUsers(users);  
									}
								}
							}
							Map<String, Object> data = new HashMap<String, Object>();
							data.put("apply", apply);
							data.put("applyTache", applyTache);
							data.put("tache", tache);
							data.put("nextTache", nextTache);
							data.put("saveurl", "/act/apply/info/applySubmit");
							r.setData(data); 
							r.success();
						}else {
							r.setError("未找到下环节，流程配置有误，请联系管理员");
						}
					}
				}else {
					r.setError("您不是当前处理人，不能操作");
				}
			}else {
				r.setError("只有待提交和复议的申请允许提交");
			}
		}else {
			r.setError("申请不存在，请刷新重试");
		}
		return crudError(r);
	}
	/**
	 * 提交申请
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/applySubmit")
	@ResponseBody
	@DisposableToken
	public Result applySubmit(ActAudit act) throws Exception {
		Result r = new Result();
		act.setAduittype("2");
		actApplyInfoSvc.handleActiviti(act, r);
		return crudError(r);
	}
	/**
	 * 进入审批并获取提交数据
	 * @param applytacheid 申请环节id
	 * @return
	 */
	@RequestMapping(value="/getApplyAuditData")
	@ResponseBody
	public Result getApplyAuditData(String applytacheid) {
		Result r = new Result();
		ActApplyTache applyTache = actApplyTacheSvc.get(applytacheid);
		if(applyTache != null) {
			ActApplyInfo apply = actApplyInfoSvc.get(applyTache.getApplyid());
			if(ProtocolConstant.activitiApplyState.state2.equals(apply.getAudit_state())) {
				ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
				List<ActConfigTache> nextTache = actApplyInfoSvc.getTrendTache(tache.getId(), apply.getId(), getUser(), "1");
				if(nextTache!=null && nextTache.size()>0) {
					for(ActConfigTache temp:nextTache) {
						if(!"4".equals(temp.getTache_type())) {  
							List<User> users = actApplyInfoSvc.getApplyTacheUsers(temp.getId(), apply.getId(), "1");
							if(users!=null && users.size()>0) {
								temp.setUsers(users);  
							}
						}
					}
				}else {
					r.setError("未找到下环节，流程配置有误，请联系管理员");
					return crudError(r);
				}
				List<ActConfigTache> prevTache = actApplyInfoSvc.getTrendTache(tache.getId(), apply.getId(), getUser(), "2");
				if(prevTache!=null && prevTache.size()>0) {
					for(ActConfigTache temp:prevTache) {
						if(!"4".equals(temp.getTache_type())) {  
							List<User> users = actApplyInfoSvc.getApplyTacheUsers(temp.getId(), apply.getId(), "1");
							if(users!=null && users.size()>0) {
								temp.setUsers(users);  
							}
						}
					}
				}
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("apply", apply);
				data.put("applyTache", applyTache);
				data.put("tache", tache);
				data.put("nextTache", nextTache);
				data.put("prevTache", prevTache);
				data.put("saveurl", "/act/apply/info/applyAudit");
				r.setData(data); 
				r.success();
			}else {
				r.setError("只有审批中申请允许审批");
			}
		}else {
			r.setError("审批已被处理，请刷新重试");
		}
		return crudError(r);
	}
	/**
	 * 申请审批
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/applyAudit")
	@ResponseBody
	@DisposableToken
	public Result applyAudit(ActAudit act) throws Exception {
		Result r = new Result();
		actApplyInfoSvc.handleActiviti(act, r);
		return crudError(r);
	}
	/**
	 * 获取审批记录(返回单列表)
	 * @param applyid 申请id
	 * @return
	 */
	@RequestMapping(value="/getActivitiRecords")
	@ResponseBody
	public Result getActivitiRecords(String applyid) {
		Result r = new Result();
		r.setData(actApplyInfoSvc.getActivitiRecords(applyid));
		r.success();
		return crudError(r);
	}
	/**
	 * 获取审批记录(返回列表带审批数据)
	 * @param applyid 申请id
	 * @return
	 */
	@RequestMapping(value="/getActivitiRecords1")
	@ResponseBody
	public Result getActivitiRecords1(String applyid) {
		Result r = new Result();
		List<ActApplyRecord> list = null;
		if(StringUtil.notBlank(applyid)) {
			list = actApplyRecordSvc.getByApplyid(applyid);
			if(list!=null && list.size()>0) {
				List<ActApplyOption> opts = actApplyOptionSvc.getByApplyid(applyid); 
				if(opts!=null && opts.size()>0) {
					Map<String, List<ActApplyOption>> map = new HashMap<String, List<ActApplyOption>>();
					for(ActApplyOption opt:opts) {
						if(!map.containsKey(opt.getRecord_id())) {
							map.put(opt.getRecord_id(), new ArrayList<ActApplyOption>());
						}
						map.get(opt.getRecord_id()).add(opt);
					}
					for(ActApplyRecord record:list) {
						if(map.containsKey(record.getId())) {
							record.setOpts(map.get(record.getId()));  
						}
					}
				}
				List<ActApplyTransferRecord> trs = actApplyTransferRecordSvc.getByApplyid(applyid);
				if(trs!=null && trs.size()>0) {
					Map<String, List<ActApplyTransferRecord>> map = new HashMap<String, List<ActApplyTransferRecord>>();
					for(ActApplyTransferRecord tr:trs) {
						if(!map.containsKey(tr.getRecord_id())) {
							map.put(tr.getRecord_id(), new ArrayList<ActApplyTransferRecord>());
						}
						map.get(tr.getRecord_id()).add(tr);
					}
					for(ActApplyRecord record:list) {
						if(map.containsKey(record.getId())) {
							record.setTrs(map.get(record.getId()));  
						}
					}
				}
			}
		}
		r.setData(list);
		r.success();
		return crudError(r);
	}
	/**
	 * 进入移交并获取移交数据
	 * @param applytacheid 申请环节id
	 * @param transferUserid 移交用户id
	 * @return
	 */
	@RequestMapping(value="/getApplyTransferData")
	@ResponseBody
	public Result getApplyTransferData(String applytacheid, String transferUserid) {
		Result r = new Result();
		ActApplyTache applyTache = actApplyTacheSvc.get(applytacheid);
		if(applyTache != null) {
			if(StringUtil.notBlank(transferUserid) && applyTache.getCurr_user_ids().indexOf(transferUserid) > -1) {
				User transferUser = userSvc.get(transferUserid);
				if(transferUser != null) {
					ActApplyInfo apply = actApplyInfoSvc.get(applyTache.getApplyid());
					ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
					if("1".equals(tache.getIs_transfer())) {
						if("1".equals(tache.getTransfer_people_type())) {
							List<User> users = actApplyInfoSvc.getApplyTacheUsers(tache.getId(), apply.getId(), "2");
							if(users!=null && users.size()>0) {
								tache.setUsers(users);  
							}
						}
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("apply", apply);
						data.put("applyTache", applyTache);
						data.put("tache", tache);
						data.put("saveurl", "/act/apply/info/applyTransfer");
						data.put("transferUser", transferUser);
						r.setData(data); 
						r.success();
					}else {
						r.setError("当前环节不能移交，请刷新重试");
					}
				}else {
					r.setError("数据异常，请刷新重试");
				}
			}else {
				r.setError("数据异常，请刷新重试");
			}
		}else {
			r.setError("审批已被处理，请刷新重试");
		}
		return crudError(r);
	}
	/**
	 * 申请移交
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/applyTransfer")
	@ResponseBody
	@DisposableToken
	public Result applyTransfer(ActAudit act) throws Exception {
		Result r = new Result();
		actApplyInfoSvc.handleTransfer(act, r);
		return crudError(r);
	}
	/**
	 * 进入查看审批记录页面
	 * @return
	 */
	@RequestMapping("/toViewAuditRecord")
	public String toViewAuditRecord() {
		return "activiti/apply/auditRecord";
	}
	/**
	 * 进入审批页面
	 * @return
	 */
	@RequestMapping("/toAudit")
	public String toAudit() {
		return "activiti/apply/audit";
	}
	/**
	 * 进入移交页面
	 * @return
	 */
	@RequestMapping("/toTransfer")
	public String toTransfer() {
		return "activiti/apply/transfer";
	}
	/**
	 * 进入查看当前审批人页面
	 * @return
	 */
	@RequestMapping("/toViewAuditMen")
	public String toViewAuditMen() {
		return "activiti/apply/viewAuditMen";
	}
	/**
	 * 快速审批（一键通过）
	 * @param applytacheids 申请环节ids，多个使用逗号分隔
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/auditQuick")
	@ResponseBody
	@DisposableToken
	public Result auditQuick(String applytacheids) throws Exception {
		Result r = new Result();
		List<Result> data = new ArrayList<Result>();
		if(StringUtil.notBlank(applytacheids)) {
			for(String applytacheid:applytacheids.split(",")) {
				Result temp = new Result();
				ActApplyTache applyTache = actApplyTacheSvc.get(applytacheid);
				if(applyTache != null) {
					ActApplyInfo apply = actApplyInfoSvc.get(applyTache.getApplyid());
					if(ProtocolConstant.activitiApplyState.state2.equals(apply.getAudit_state())) {
						ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
						List<ActConfigTache> nextTacheList = actApplyInfoSvc.getTrendTache(tache.getId(), apply.getId(), getUser(), "1");
						if(nextTacheList!=null && nextTacheList.size()>0) {
							if(nextTacheList.size() == 1) {
								ActConfigTache nextTache = nextTacheList.get(0);
								if("4".equals(nextTache.getTache_type()) || "2".equals(nextTache.getPeople_select_type())) {
									ActAudit act = new ActAudit();
									act.setResult("1");
									act.setNextTache(nextTache.getId());
									act.setOptions("同意"); 
									act.setApplytacheid(applytacheid); 
									actApplyInfoSvc.handleActiviti(act, temp);
								}else {
									temp.setError("快速审批的下环节人员选择方式不能时手动选择"); 
								}
							}else {
								temp.setError("找到多个下环节，快速审批只支持单一下环节");
							}
						} else {
							temp.setError("未找到下环节，流程配置有误");
						}
					}else {
						temp.setError("只有审批中申请允许审批");
					}
				}else {
					temp.setError("审批已被处理");
				}
				temp.setData(applyTache); 
				data.add(temp);
			}
		}
		r.setData(data);
		r.success();
		return r;
	}
	
	/**
	 * 快速审批（一键通过）
	 * @param applytacheids 申请环节ids，多个使用逗号分隔
	 * @param userid 下环节用户
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/auditQuickWithUser")
	@ResponseBody
	@DisposableToken
	public Result auditQuickWithUser(String applytacheids, String userid) throws Exception {
		Result r = new Result();
		List<Result> data = new ArrayList<Result>();
		if(StringUtil.notBlank(applytacheids, userid)) {
			for(String applytacheid:applytacheids.split(",")) {
				Result temp = new Result();
				ActApplyTache applyTache = actApplyTacheSvc.get(applytacheid);
				if(applyTache != null) {
					ActApplyInfo apply = actApplyInfoSvc.get(applyTache.getApplyid());
					if(ProtocolConstant.activitiApplyState.state2.equals(apply.getAudit_state())) {
						ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
						List<ActConfigTache> nextTacheList = actApplyInfoSvc.getTrendTache(tache.getId(), apply.getId(), getUser(), "1");
						if(nextTacheList!=null && nextTacheList.size()>0) {
							if(nextTacheList.size() == 1) {
								ActConfigTache nextTache = nextTacheList.get(0);
								HttpReqtRespContext.getRequest().setAttribute(nextTache.getId(), userid); 
								if("4".equals(nextTache.getTache_type()) || "2".equals(nextTache.getPeople_select_type())) {
									ActAudit act = new ActAudit();
									act.setResult("1");
									act.setNextTache(nextTache.getId());
									act.setOptions("同意"); 
									act.setApplytacheid(applytacheid); 
									actApplyInfoSvc.handleActiviti(act, temp);
								}else {
									temp.setError("快速审批的下环节人员选择方式不能时手动选择"); 
								}
							}else {
								temp.setError("找到多个下环节，快速审批只支持单一下环节");
							}
						} else {
							temp.setError("未找到下环节，流程配置有误");
						}
					}else {
						temp.setError("只有审批中申请允许审批");
					}
				}else {
					temp.setError("审批已被处理");
				}
				temp.setData(applyTache); 
				data.add(temp);
			}
		}
		r.setData(data);
		r.success();
		return r;
	}
}
