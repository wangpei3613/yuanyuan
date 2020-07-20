package com.sensebling.activiti.service.impl;

import com.sensebling.activiti.entity.*;
import com.sensebling.activiti.service.*;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.*;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.UserSvc;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.Transient;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class ActApplyInfoSvcImpl extends BasicsSvcImpl<ActApplyInfo> implements ActApplyInfoSvc, ApplicationContextAware{
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
	private static Logger logger = Logger.getLogger(ActApplyInfoSvcImpl.class);
	private ApplicationContext applicationContext;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
	
	@Override
	public boolean checkActivitiConfig(String processcode) {
		ActConfigInfo config = getConfigByProcesscode(processcode);
		if(config != null) {
			if(getFirstTache(config.getId()) != null) {
				return true;
			}
		}
		return false;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public ActConfigTache getFirstTache(String actid) {
		String sql = "select b.* from sen_act_config_tache_trend a join sen_act_config_tache b on b.id=a.curr_tache_id where a.actid=? and a.trend_type='1' and a.state='1' order by a.ordernum fetch first 1 rows only";
		List list = baseDao.findBySQLEntity(sql, new Object[] {actid}, ActConfigTache.class.getName());
		if(list!=null && list.size()>0) {
			return (ActConfigTache)list.get(0);
		}
		return null;
	}
	@Override
	public ActConfigInfo getConfigByProcesscode(String processcode) {
		if(StringUtil.notBlank(processcode)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("processcode", processcode);
			qp.addParamter("enabled", "1");
			List<ActConfigInfo> list = actConfigInfoSvc.findAll(qp);
			if(list!=null && list.size()==1) {
				return list.get(0);
			}
		}
		return null;
	}
	@Override
	public ActApplyInfo newApply(String caseid, String processcode, User user) {
		ActConfigInfo config = getConfigByProcesscode(processcode);
		if(config != null) {
			ActConfigTache tache = getFirstTache(config.getId());
			if(tache != null) {
				ActApplyInfo apply = new ActApplyInfo();
				apply.setActid(config.getId());
				apply.setAppl_date(DateUtil.getStringDate());
				apply.setApplman(user.getId());
				apply.setAudit_state(ProtocolConstant.activitiApplyState.state1);
				apply.setCaseid(caseid); 
				String applyid = (String)save(apply);
				ActApplyRecord record = new ActApplyRecord();
				record.setApplyid(applyid);
				record.setAuditing_result(ProtocolConstant.activitiRecordState.state1); 
				record.setStart_time(apply.getAppl_date());
				record.setTacheid(tache.getId());
				record.setPrescription(this.calcPrescription(tache, apply.getId()));
				record.setOver_time(this.calcOver_time(record.getPrescription(), record.getStart_time()));
				String recordid = (String)actApplyRecordSvc.save(record);
				ActApplyTache applyTache = new ActApplyTache();
				applyTache.setApplyid(applyid);
				applyTache.setCurr_tache_id(tache.getId());
				applyTache.setCurr_user_ids(user.getId());
				applyTache.setRecord_id(recordid);
				applyTache.setSent_time(apply.getAppl_date()); 
				applyTache.setSent_usercode(user.getId());  
				applyTache.setNext_tache_id(this.getTrendTacheids(tache.getId(), apply.getId(), user, "1"));
				actApplyTacheSvc.save(applyTache);
				return apply;
			}
		}
		return null;
	}
	@Override
	public String getTrendTacheids(String tacheid, String applyid, User user, String trend_type) {
		List<ActConfigTache> list = getTrendTache(tacheid, applyid, user, trend_type);
		if(list!=null && list.size()>0) {
			String str = "";
			for(ActConfigTache tache:list) {
				str += "," + tache.getId();
			}
			return str.substring(1);
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ActConfigTache> getTrendTache(String tacheid, String applyid, User user, String trend_type){
		String sql = "select b.*,a.branch_condition from sen_act_config_tache_trend a join sen_act_config_tache b on b.id=a.next_tache_id where a.curr_tache_id=? and a.state='1' and a.trend_type=? order by a.ordernum";
		List list = baseDao.findBySQLEntity(sql, new Object[] {tacheid, trend_type}, ActConfigTache.class.getName());
		if(list!=null && list.size()>0) {
			List<ActConfigTache> taches = new ArrayList<ActConfigTache>();
			List<String> temp = new ArrayList<String>();
			for(ActConfigTache tache:(List<ActConfigTache>)list) {  
				if(temp.indexOf(tache.getId()) == -1) {
					String condition = tache.getBranch_condition();
					if(StringUtil.notBlank(condition)) {
						condition = condition.replace("?", "'"+applyid+"'");
						List<Object[]> obj = baseDao.queryBySql(condition);
						if(obj!=null && obj.size()>0) {
							temp.add(tache.getId());
							taches.add(tache);
						}
					}else {
						temp.add(tache.getId());
						taches.add(tache);
					}
				}
			}
			return taches;
		}
		return null;
	}
	/**
	 * 根据时效计算超时时间
	 * @param prescription 时效
	 * @param start_time 开始时间
	 * @return
	 */
	private String calcOver_time(Double prescription, String start_time) {
		try {
			if(prescription != null && StringUtil.notBlank(start_time)){
				return DateUtil.formatDate(DateUtil.getDate(start_time,"yyyy-MM-dd HH:mm:ss").getTime() + Math.round(prescription*60*60*1000),"yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	/**
	 * 计算环节时效
	 * @param tache 环节消息
	 * @param applyid 申请id
	 * @return
	 */
	private Double calcPrescription(ActConfigTache tache, String applyid) {
		try {
			String prescription = tache.getPrescription();
			if(StringUtil.notBlank(prescription)) {
				try {
					return Double.parseDouble(prescription.trim());
				} catch (NumberFormatException e) {
					prescription = prescription.replace("?", "'"+applyid+"'");
					List<Object[]> obj = baseDao.queryBySql(prescription);
					if(obj!=null && obj.size()>0) {
						prescription = String.valueOf(obj.get(0)).trim();
						if(StringUtil.notBlank(prescription)){
							return Double.parseDouble(prescription);  
						}
					}
				}
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	@Override
	public void delApply(String applyid) {
		String sql = "delete from sen_act_apply_tache a where a.applyid=?";
		baseDao.executeSQL(sql, applyid);
		sql = "delete from sen_act_apply_option b where b.record_id in (select a.id from sen_act_apply_record a where a.applyid=?)";
		baseDao.executeSQL(sql, applyid);
		sql = "delete from sen_act_apply_record a where a.applyid=?";
		baseDao.executeSQL(sql, applyid);
		sql = "delete from sen_act_apply_info a where a.id=?";
		baseDao.executeSQL(sql, applyid);
	}
	@Override
	public void cancelApply(String applyid) {
		updateApplyStatus(applyid, ProtocolConstant.activitiApplyState.state6);
		updateApplyEndDate(applyid);  
		delApplyTache(applyid);
		List<ActApplyRecord> list = getApplyRecord(applyid);
		if(list!=null && list.size()>0) {
			for(ActApplyRecord record:list) {
				if(ProtocolConstant.activitiRecordState.state1.equals(record.getAuditing_result())) {
					record.setAuditing_result(ProtocolConstant.activitiRecordState.state5);
					record.setEnd_date(DateUtil.getStringDate());
					actApplyRecordSvc.update(record);
					addApplyOption(null, record.getId(), ProtocolConstant.activitiOptionResult.state4, null);
				}
			}
		}
	}
	@Override
	public ActApplyOption addApplyOption(String options, String record_id, String result, String sign_data) {
		ActApplyOption option = new ActApplyOption();
		option.setAdd_time(DateUtil.getStringDate());
		option.setAdd_user(HttpReqtRespContext.getUser().getId());
		option.setOptions(options);
		option.setRecord_id(record_id);
		option.setResult(result);
		option.setSign_path(FileOperateUtil.saveSignImg(sign_data));  
		actApplyOptionSvc.save(option);
		return option;
	}
	@Override
	public void delApplyTache(String applyid) {
		String sql = "delete from sen_act_apply_tache a where a.applyid=?";
		baseDao.executeSQL(sql, applyid);
	}
	@Override
	public void updateApplyStatus(String applyid, String state) {
		String sql = "update sen_act_apply_info a set a.audit_state=? where a.id=?";
		baseDao.executeSQL(sql, state, applyid);
	}
	@Override
	public void updateApplyEndDate(String applyid) {
		String sql = "update sen_act_apply_info a set a.end_date=? where a.id=?";
		baseDao.executeSQL(sql, DateUtil.getStringDate(), applyid);
	}

	@Override
	public Result newApplyAndSubmit(String caseid, String processcode, User user,Result r,String rstate) throws Exception{
		ActConfigInfo config = getConfigByProcesscode(processcode);
		if(config != null) {
			ActConfigTache actconfigtache = getFirstTache(config.getId());
			if (actconfigtache != null) {
				ActApplyInfo apply = new ActApplyInfo();
				apply.setActid(config.getId());
				apply.setAppl_date(DateUtil.getStringDate());
				apply.setApplman(user.getId());
				apply.setAudit_state(ProtocolConstant.activitiApplyState.state1);
				apply.setCaseid(caseid);
				apply.setJson(rstate);
				String applyid = (String) save(apply);
				ActApplyRecord applyRecord = new ActApplyRecord();
				applyRecord.setApplyid(applyid);
				applyRecord.setAuditing_result(ProtocolConstant.activitiRecordState.state1);
				applyRecord.setStart_time(apply.getAppl_date());
				applyRecord.setTacheid(actconfigtache.getId());
				applyRecord.setPrescription(this.calcPrescription(actconfigtache, apply.getId()));
				applyRecord.setOver_time(this.calcOver_time(applyRecord.getPrescription(), applyRecord.getStart_time()));
				String recordid = (String) actApplyRecordSvc.save(applyRecord);
				ActApplyTache applyTache = new ActApplyTache();
				applyTache.setApplyid(applyid);
				applyTache.setCurr_tache_id(actconfigtache.getId());
				applyTache.setCurr_user_ids(user.getId());
				applyTache.setRecord_id(recordid);
				applyTache.setSent_time(apply.getAppl_date());
				applyTache.setSent_usercode(user.getId());
				applyTache.setNext_tache_id(this.getTrendTacheids(actconfigtache.getId(), apply.getId(), user, "1"));
				actApplyTacheSvc.save(applyTache);
				//提交
				ActAudit act = new ActAudit();
				ActApplyInfo info = new ActApplyInfo();
				info.setActid(getConfigByProcesscode(ProtocolConstant.activitiProcessCode.BORROW).getId());
				info.setApplman(user.getId());
				info.setAppl_date(DateUtil.getStringDate());
				info.setAudit_state("2");
				info.setCaseid(caseid);
				act.setApply(info);
				act.setApplytacheid(getFirstTache(info.getActid()).getId());
				act.setAduittype("2");
				act.setResult("1");
				act.setNextTacheid(applyTache.getNext_tache_id());
				act.setNextTache(applyTache.getNext_tache_id());
				String result = act.getResult();
				/***************数据校验start********************/
				if(applyTache == null) {
					r.setError("审批不存在，请刷新重试");
					return r;
				}
				ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
				if(apply == null || applyRecord == null || tache == null) {
					return r;
				}
				act.setTache(tache);
				act.setApply(apply);
				act.setApplyRecord(applyRecord);
				act.setApplyTache(applyTache);
				if("1".equals(tache.getNeedsignature()) && StringUtil.isBlank(act.getSign_data())) {
					r.setError("请先签名");
					return r;
				}
				if(!"1".equals(tache.getNeedsignature())) {
					act.setSign_data(null);
				}
				if("1".equals(result) && StringUtil.isBlank(act.getNextTache())) {
					return r;
				}
				/**************数据校验end*********************/


				/**************权限校验start*******************/
				if("1".equals(act.getAduittype())) {//审批
					if(!ProtocolConstant.activitiApplyState.state2.equals(apply.getAudit_state())) {
						r.setError("流程数据异常，请刷新重试");
						return r;
					}
				}else {//提交
					if(!ProtocolConstant.activitiApplyState.state1.equals(apply.getAudit_state()) &&
							!ProtocolConstant.activitiApplyState.state4.equals(apply.getAudit_state())) {
						r.setError("流程数据异常，请刷新重试");
						return r;
					}
				}
				if(!ProtocolConstant.activitiRecordState.state1.equals(applyRecord.getAuditing_result())) {//审批状态
					r.setError("流程数据异常，请刷新重试");
					return r;
				}
				if("".equals(StringUtil.sNull(user.getId()))) {
					user = (User)r.getData();
				}
				if(applyTache.getCurr_user_ids().indexOf(user.getId()) == -1) {
					r.setError("您没有权限审批");
					return r;
				}
				int sign_all = applyTache.getCurr_user_ids().split(",").length,//会签总人数
						sign_do = 0,//会签已审人数
						sign_reject = 0,//会签否决人数
						sign_ok = 0;//会签通过人数
				boolean isSign = "3".equals(tache.getTache_type());//是否是会签环节
				if(isSign) {//会签判断是否已审批   并且计算些会签数据
					List<ActApplyOption> options = getApplyOptions(applyRecord.getId());
					if(options != null && options.size()>0) {
						for(ActApplyOption option:options) {
							sign_do++;
							if(option.getAdd_user().equals(user.getId())) {
								r.setError("您已经审批，请刷新重试");
								return r;
							}
							if(ProtocolConstant.activitiOptionResult.state1.equals(option.getResult())) {
								sign_ok++;
							}else if(ProtocolConstant.activitiOptionResult.state3.equals(option.getResult())) {
								sign_reject++;
							}
						}
					}
				}
				//提交前回调处理
				if(StringUtil.notBlank(tache.getSaveurl())) {
					try {
						String bean = tache.getSaveurl().trim().split("\\.")[0].trim();
						String method = tache.getSaveurl().trim().split("\\.")[1].trim();
						Object obj = applicationContext.getBean(bean);
						Method m = obj.getClass().getMethod(method, ActAudit.class);
						Result temp = (Result)m.invoke(obj, act);
						if(!temp.isSuccess()) {
							r.setError(temp.getError());
							return r;
						}
					} catch (Exception e) {
						logger.error("提交前回调配置有误",e);
						r.setError("提交前回调配置有误");
						return r;
					}
				}
				//处理系统审批结果
				String calcResult = act.getCalcResult();
				if(StringUtil.notBlank(calcResult) && ("1".equals(calcResult)||"2".equals(calcResult)||"3".equals(calcResult))) {
					result = calcResult;
				}
				if("2".equals(result) && !"1".equals(tache.getSign_return())) {//不允许复议
					r.setError("当前环节不允许复议，请刷新重试");
					return r;
				}
				/**************权限校验end*********************/

				/**************审批结果处理start*******************/
				boolean isReject = false;//是否是否决
				sign_do++;
				List<ActConfigTache> trendTaches = null;//下环节
				if(isSign) {//会签
					if("1".equals(result)) {
						sign_ok++;
					}else if("3".equals(result)) {
						sign_reject++;
					}
					//判断会签结果处理
					boolean signIsEnd = true;//会签环节是否结束
					boolean rateIsReject = (sign_reject-tache.getSign_offset())*100.0/(sign_all+Math.abs(tache.getSign_offset())) > (100-tache.getSign_ratio());
					boolean rateIsOk = (sign_ok+tache.getSign_offset())*100.0/(sign_all+Math.abs(tache.getSign_offset())) >= tache.getSign_ratio();
					if("1".equals(tache.getSign_type())) {//需要全员审批
						if(sign_all > sign_do) {//当前未全员审批
							signIsEnd = false;
						}else {
							if(rateIsReject) {//否决
								isReject = true;
							}else if(rateIsOk){//通过
								result = "1";
							}else{//既不满足否决比例，也不满足通过比例则复议
								result = "2";
							}
						}
					}else {//不需要全员审批
						if("2".equals(result)) {//不需要全员审批若选择复议则直接复议

						}else if(rateIsReject) {//否决
							isReject = true;
						}else if(rateIsOk){//通过
							result = "1";
						}else {
							signIsEnd = false;
						}
					}
					if(!signIsEnd) {//会签未结束的一个过程，直接添加一条审批记录即可
						addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
						act.setHandlerResult("0");//会签未结束的一个环节审批完成
						handleRedirect(act, r);
						return r;
					}
				}else {
					if("3".equals(result)) {//审批用户选择否决
						isReject = true;
					}
				}
				if(!isReject) {//不是否决
					trendTaches = getTrendTache(tache.getId(), apply.getId(), user, result);
					boolean hasTrendTaches = (trendTaches!=null && trendTaches.size()>0);//有下环节或复议环节
					if("1".equals(result) && !hasTrendTaches) {//前进且没有下环节时
						r.setError("未找到下环节，流程配置异常，请联系管理员");
						return r;
					}
					boolean isPass = false;//是否没有下环节
					ActConfigTache nextTache = null;
					if(hasTrendTaches) {
						if(isSign || (StringUtil.notBlank(calcResult) && !calcResult.equals(act.getResult()))) {//会签时或使用系统审批结果时系统随机取下环节
							nextTache = trendTaches.get(new Random().nextInt(trendTaches.size()));
						}else {//非会签时判断选择环节是否正确
							boolean isRight = false;//环节选择正确
							for(ActConfigTache trendTache:trendTaches) {
								if((trendTache.getId().equals(act.getNextTache()) && "1".equals(result)) ||
										(trendTache.getId().equals(act.getPrevTache()) && "2".equals(result))) {
									isRight = true;
									nextTache = trendTache;
									break;
								}
							}
							if(!isRight) {
								r.setError("环节选择有误，请刷新重试");
								return r;
							}
						}
						act.setNextTacheid(nextTache.getId());
						if("4".equals(nextTache.getTache_type())) {
							if("1".equals(result)) {
								isPass = true;
							}else {
								r.setError("不能复议到结束环节，配置异常，请联系管理员");
								return r;
							}
						}else {
							String userids = getApplyTacheUserids(nextTache.getId(), apply.getId(), "1");
							if(StringUtil.notBlank(userids)) {
								if(isSign) {
									act.setNextUserid(userids);
									if(!"3".equals(nextTache.getTache_type())) {
										act.setNextUserid(userids.split(",")[new Random().nextInt(userids.split(",").length)]);
									}
								}else {
									if("1".equals(nextTache.getPeople_select_type())) {//手动选择用户
										String selectedUserids = HttpReqtRespContext.getRequest().getParameter(nextTache.getId());
										if(StringUtil.isBlank(selectedUserids)) {
											selectedUserids = StringUtil.ValueOf(HttpReqtRespContext.getRequest().getAttribute(nextTache.getId()));
										}
										if(StringUtil.notBlank(selectedUserids)) {
											if(StringUtil.arrInArr(userids.split(","), selectedUserids.split(","))) {
												if(!"3".equals(nextTache.getTache_type()) && selectedUserids.split(",").length>1) {
													r.setError("只能选择一个用户");
													return r;
												}
												act.setNextUserid(selectedUserids);
											}else {
												r.setError("选择审批用户有误，请刷新重试");
												return r;
											}
										}else {
											r.setError("请选择审批用户");
											return r;
										}
									}else {
										act.setNextUserid(userids);
										if(!"3".equals(nextTache.getTache_type())) {
											act.setNextUserid(userids.split(",")[new Random().nextInt(userids.split(",").length)]);
										}
									}
								}
							}else {
								r.setError("环节("+nextTache.getName()+")未找到有权限审批人员，请联系管理员");
								return r;
							}
						}
					}else {//复议没有下环节时复议到初始环节
						nextTache = getFirstTache(apply.getActid());
						act.setNextTacheid(nextTache.getId());
						act.setNextUserid(apply.getApplman());
						isPass = true;
					}
					if(isPass) {
						if("1".equals(result)) {
							updateApplyRecordResult(ProtocolConstant.activitiRecordState.state2, applyRecord.getId());
							addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
							delApplyTache(apply.getId());
							updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state3);
							updateApplyEndDate(apply.getId());
							act.setHandlerResult("3");//审批通过
							handleRedirect(act, r);
							return r;
						}else {
							updateApplyRecordResult(ProtocolConstant.activitiRecordState.state3, applyRecord.getId());
							addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
							updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state4);
							delApplyTache(apply.getId());
							act.setNextApplyTache(addTrendApply(nextTache, applyTache, apply, act));
							act.setHandlerResult("4");//审批复议
							handleRedirect(act, r);
							return r;
						}
					}else {
						updateApplyRecordResult((Integer.parseInt(result)+1)+"", applyRecord.getId());
						addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
						delApplyTache(apply.getId());
						act.setNextApplyTache(addTrendApply(nextTache, applyTache, apply, act));
						if("2".equals(act.getAduittype())) {
							apply.setAudit_state(ProtocolConstant.activitiApplyState.state2);
							this.baseDao.update(apply);
						}
						act.setHandlerResult(result);//1前进  2后退
						handleRedirect(act, r);
						return r;
					}
				}else {//否决
					updateApplyRecordResult(ProtocolConstant.activitiRecordState.state4, applyRecord.getId());
					addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
					delApplyTache(apply.getId());
					updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state5);
					updateApplyEndDate(apply.getId());
					cancelApplyRecord(apply.getId());
					act.setHandlerResult("5");//审批否决
					handleRedirect(act, r);
					return r;
				}
				/**************审批结果处理end*********************/
			}
		}
				return r;
	}

	@Override
	public List<ActApplyRecord> getApplyRecord(String applyid){
		if(StringUtil.notBlank(applyid)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("applyid", applyid);
			qp.setSortField("start_time");
			qp.setSortOrder("desc");  
			return actApplyRecordSvc.findAll(qp);
		}
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<User> getAuditMen(String applyid) {
		String sql = "select distinct b.* from sen_act_apply_tache a,v_sen_userinfo b where a.applyid=? and LOCATE(','||b.id||',',','||a.curr_user_ids||',')>0 and not exists (select 1 from sen_act_apply_option d where d.add_user=b.id and d.record_id=a.record_id)";
		List list = baseDao.findBySQLEntity(sql, User.class.getName(), applyid);
		return list;
	}
	@Override
	public List<ActApplyTache> getApplyTache(String applyid) {
		if(StringUtil.notBlank(applyid)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("applyid", applyid);
			return actApplyTacheSvc.findAll(qp);
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> getApplyTacheUsers(String tacheid, String applyid, String category) {
		String str = getApplyTacheUserids(tacheid, applyid, category);
		if(StringUtil.notBlank(str)) {
			String sql = "select a.*,b.fullName deptName from sen_user a join sen_department b on b.id=a.deptId where a.id in ("+StringUtil.change_in(str)+")";  
			List list = baseDao.findBySQLEntity(sql, new Object[] {}, User.class.getName());
			return (List<User>)list;   
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getApplyTacheUserids(String tacheid, String applyid, String category) {
		Set<String> set = new HashSet<String>();
		String sql = "SELECT decode(a.type,1,s1.id,2,s2.userid,3,s3.id,a.value) value,a.type FROM sen_act_config_tache_operator a LEFT JOIN sen_user s1 ON s1.id=a.value AND a.type=1 left join sen_user_role s2 on s2.roleid=a.value and a.type=2 left join sen_user s3 on s3.deptid=a.value and a.type=3 WHERE a.category=? AND a.tacheid=?";
		List list = baseDao.findBySQLEntity(sql, new Object[] {category, tacheid}, ActConfigTacheOperator.class.getName());
		if(list!=null && list.size()>0) {
			for(ActConfigTacheOperator v:(List<ActConfigTacheOperator>)list) {  
				if(1==v.getType() || 2==v.getType() || 3==v.getType()) {//用户、角色、部门
					if(StringUtil.notBlank(v.getValue())) {
						set.add(v.getValue().trim());
					}
				}else if(4==v.getType() || 5==v.getType()) {//sql、函数
					sql = v.getValue().replace("?", "'"+applyid+"'");
					if(5 == v.getType()) {
						sql = "select "+v.getValue()+"('"+applyid+"') from sysibm.sysdummy1";
					}
					List<Object[]> temp = baseDao.queryBySql(sql);
					if(temp!=null && temp.size()>0) {
						for(int i=0;i<temp.size();i++){
							String str = String.valueOf(temp.get(i)).trim();
							if(StringUtil.notBlank(str)) {
								String[] arr = str.split(",");
								for(String s:arr) {
									if(StringUtil.notBlank(s)) {
										set.add(s.trim());
									}
								}
							}
						}
					}
				}else if(6 == v.getType()) {//存储
					sql = "{call "+v.getValue()+"(?,?)}";
					String str = baseDao.callProcedure(sql, applyid);
					if(StringUtil.notBlank(str)) {
						String[] arr = str.split(",");
						for(String s:arr) {
							if(StringUtil.notBlank(s)) {
								set.add(s.trim());
							}
						}
					}
				}
			}
		}
		if(set.size() > 0) {
			String temp = "";
			for(String str:set) {
				temp += ","+str;
			}
			return temp.substring(1);
		}
		return null;
	}
	@Override
	public List<ActApplyOption> getApplyOptions(String recordid) {
		if(StringUtil.notBlank(recordid)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("record_id", recordid);
			return actApplyOptionSvc.findAll(qp);
		}
		return null;
	}
	@Override
	public void cancelApplyRecord(String applyid) {
		String sql = "update sen_act_apply_record a set a.auditing_result=?,a.end_date=? where a.applyid=? and a.auditing_result=?";
		baseDao.executeSQL(sql, ProtocolConstant.activitiRecordState.state5, DateUtil.getStringDate(), applyid, ProtocolConstant.activitiRecordState.state1);
	}
	/**
	 * 流程前进或后退
	 * @param trendTaches
	 * @param applyTache
	 * @param applyRecord
	 * @param apply
	 * @param optionSel_userids
	 */
	private ActApplyTache addTrendApply(ActConfigTache nextTache, ActApplyTache applyTache, ActApplyInfo apply, ActAudit act) {
		User user = HttpReqtRespContext.getUser();
		ActApplyRecord record = new ActApplyRecord();
		record.setApplyid(apply.getId());
		record.setAuditing_result(ProtocolConstant.activitiRecordState.state1); 
		record.setStart_time(DateUtil.getStringDate());
		record.setTacheid(nextTache.getId());
		record.setPrescription(this.calcPrescription(nextTache, apply.getId()));
		record.setOver_time(this.calcOver_time(record.getPrescription(), record.getStart_time()));
		String recordid = (String)actApplyRecordSvc.save(record);
		ActApplyTache at = new ActApplyTache();
		at.setApplyid(apply.getId());
		at.setCurr_tache_id(nextTache.getId());
		at.setCurr_user_ids(act.getNextUserid());
		at.setRecord_id(recordid);
		at.setSent_time(DateUtil.getStringDate()); 
		at.setSent_usercode(user.getId());  
		at.setNext_tache_id(this.getTrendTacheids(nextTache.getId(), apply.getId(), user, "1"));
		at.setBack_tache_id(this.getTrendTacheids(nextTache.getId(), apply.getId(), user, "2"));  
		at.setPre_tache_id(applyTache.getCurr_tache_id());  
		actApplyTacheSvc.save(at);
		return at;
	}
	@Override
	@Transient
	public Result handleActiviti(ActAudit act, Result r) throws Exception{
		synchronized(act.getApplytacheid()) {
			String result = act.getResult();
			/***************数据校验start********************/
			ActApplyTache applyTache = actApplyTacheSvc.get(act.getApplytacheid());
			if(applyTache == null) {
				r.setError("审批不存在，请刷新重试");
				return r;
			}
			ActApplyInfo apply = get(applyTache.getApplyid());
			ActApplyRecord applyRecord = actApplyRecordSvc.get(applyTache.getRecord_id());
			ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
			if(apply == null || applyRecord == null || tache == null) {
				return r;
			}
			act.setTache(tache);  
			act.setApply(apply);
			act.setApplyRecord(applyRecord);
			act.setApplyTache(applyTache);  
			if("1".equals(tache.getNeedsignature()) && StringUtil.isBlank(act.getSign_data())) { 
				r.setError("请先签名");
				return r;
			}
			if(!"1".equals(tache.getNeedsignature())) {
				act.setSign_data(null);  
			}
			if("1".equals(result) && StringUtil.isBlank(act.getNextTache())) {  
				return r;
			}
			/**************数据校验end*********************/
			
			
			/**************权限校验start*******************/
			if("1".equals(act.getAduittype())) {//审批
				if(!ProtocolConstant.activitiApplyState.state2.equals(apply.getAudit_state())) {
					r.setError("流程数据异常，请刷新重试");
					return r;
				}
			}else {//提交
				if(!ProtocolConstant.activitiApplyState.state1.equals(apply.getAudit_state()) &&
						!ProtocolConstant.activitiApplyState.state4.equals(apply.getAudit_state())) {
					r.setError("流程数据异常，请刷新重试");
					return r;
				}
			}
			if(!ProtocolConstant.activitiRecordState.state1.equals(applyRecord.getAuditing_result())) {//审批状态
				r.setError("流程数据异常，请刷新重试");
				return r;
			}
			User user = HttpReqtRespContext.getUser(); 
			if("".equals(StringUtil.sNull(user.getId()))) {
				user = (User)r.getData();
			}
			if(applyTache.getCurr_user_ids().indexOf(user.getId()) == -1) {
				r.setError("您没有权限审批");
				return r;
			}
			int sign_all = applyTache.getCurr_user_ids().split(",").length,//会签总人数
				sign_do = 0,//会签已审人数
				sign_reject = 0,//会签否决人数
				sign_ok = 0;//会签通过人数
			boolean isSign = "3".equals(tache.getTache_type());//是否是会签环节
			if(isSign) {//会签判断是否已审批   并且计算些会签数据
				List<ActApplyOption> options = getApplyOptions(applyRecord.getId());
				if(options != null && options.size()>0) {
					for(ActApplyOption option:options) {
						sign_do++;
						if(option.getAdd_user().equals(user.getId())) {
							r.setError("您已经审批，请刷新重试");
							return r;
						}
						if(ProtocolConstant.activitiOptionResult.state1.equals(option.getResult())) {
							sign_ok++;
						}else if(ProtocolConstant.activitiOptionResult.state3.equals(option.getResult())) {
							sign_reject++;
						}
					}
				}
			}
			//提交前回调处理
			if(StringUtil.notBlank(tache.getSaveurl())) {
				try {
					String bean = tache.getSaveurl().trim().split("\\.")[0].trim();
					String method = tache.getSaveurl().trim().split("\\.")[1].trim();
					Object obj = applicationContext.getBean(bean);
					Method m = obj.getClass().getMethod(method, ActAudit.class);
					Result temp = (Result)m.invoke(obj, act);
					if(!temp.isSuccess()) {
						r.setError(temp.getError());
						return r;
					}
				} catch (Exception e) {
					logger.error("提交前回调配置有误",e);  
					r.setError("提交前回调配置有误");
					return r;
				}
			}
			//处理系统审批结果
			String calcResult = act.getCalcResult();
			if(StringUtil.notBlank(calcResult) && ("1".equals(calcResult)||"2".equals(calcResult)||"3".equals(calcResult))) {
				result = calcResult;
			}
			if("2".equals(result) && !"1".equals(tache.getSign_return())) {//不允许复议
				r.setError("当前环节不允许复议，请刷新重试");
				return r;
			}
			/**************权限校验end*********************/
			
			/**************审批结果处理start*******************/
			boolean isReject = false;//是否是否决
			sign_do++;
			List<ActConfigTache> trendTaches = null;//下环节
			if(isSign) {//会签
				if("1".equals(result)) {
					sign_ok++;
				}else if("3".equals(result)) {
					sign_reject++;
				}
				//判断会签结果处理
				boolean signIsEnd = true;//会签环节是否结束
				boolean rateIsReject = (sign_reject-tache.getSign_offset())*100.0/(sign_all+Math.abs(tache.getSign_offset())) > (100-tache.getSign_ratio());
				boolean rateIsOk = (sign_ok+tache.getSign_offset())*100.0/(sign_all+Math.abs(tache.getSign_offset())) >= tache.getSign_ratio();
				if("1".equals(tache.getSign_type())) {//需要全员审批
					if(sign_all > sign_do) {//当前未全员审批
						signIsEnd = false;
					}else {
						if(rateIsReject) {//否决
							isReject = true;
						}else if(rateIsOk){//通过
							result = "1";
						}else{//既不满足否决比例，也不满足通过比例则复议
							result = "2";
						}
					}
				}else {//不需要全员审批
					if("2".equals(result)) {//不需要全员审批若选择复议则直接复议
						
					}else if(rateIsReject) {//否决
						isReject = true;
					}else if(rateIsOk){//通过
						result = "1";
					}else {
						signIsEnd = false;
					}
				}
				/*
				//以下为上面判断的简写，由于不好理解暂不用
				if("1".equals(tache.getSign_type()) && sign_all > sign_do) {//需要全员审批 但当前未全员审批
					signIsEnd = false;
				}else {
					if(rateIsReject) {//否决
						isReject = true;
					}else if(rateIsOk){//通过
						result = "1";
					}else if(sign_all == sign_do || "2".equals(result)){//既不满足否决比例，也不满足通过比例且已全员会签或会签复议，则复议
						result = "2";
					}else {
						signIsEnd = false;
					}
				}
				*/
				if(!signIsEnd) {//会签未结束的一个过程，直接添加一条审批记录即可
					addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
					act.setHandlerResult("0");//会签未结束的一个环节审批完成
					handleRedirect(act, r);
					return r;
				}
			}else {
				if("3".equals(result)) {//审批用户选择否决
					isReject = true;
				}
			}
			if(!isReject) {//不是否决
				trendTaches = getTrendTache(tache.getId(), apply.getId(), user, result);
				boolean hasTrendTaches = (trendTaches!=null && trendTaches.size()>0);//有下环节或复议环节
				if("1".equals(result) && !hasTrendTaches) {//前进且没有下环节时
					r.setError("未找到下环节，流程配置异常，请联系管理员");
					return r;
				}
				boolean isPass = false;//是否没有下环节
				ActConfigTache nextTache = null;
				if(hasTrendTaches) {
					if(isSign || (StringUtil.notBlank(calcResult) && !calcResult.equals(act.getResult()))) {//会签时或使用系统审批结果时系统随机取下环节
						nextTache = trendTaches.get(new Random().nextInt(trendTaches.size()));
					}else {//非会签时判断选择环节是否正确
						boolean isRight = false;//环节选择正确
						for(ActConfigTache trendTache:trendTaches) {
							if((trendTache.getId().equals(act.getNextTache()) && "1".equals(result)) || 
									(trendTache.getId().equals(act.getPrevTache()) && "2".equals(result))) {
								isRight = true;
								nextTache = trendTache;
								break;
							}
						}
						if(!isRight) {
							r.setError("环节选择有误，请刷新重试");
							return r;
						}
					}
					act.setNextTacheid(nextTache.getId());
					if("4".equals(nextTache.getTache_type())) {  
						if("1".equals(result)) {
							isPass = true;
						}else {
							r.setError("不能复议到结束环节，配置异常，请联系管理员");
							return r;
						}
					}else {
						String userids = getApplyTacheUserids(nextTache.getId(), apply.getId(), "1");
						if(StringUtil.notBlank(userids)) {
							if(isSign) {
								act.setNextUserid(userids);
								if(!"3".equals(nextTache.getTache_type())) {
									act.setNextUserid(userids.split(",")[new Random().nextInt(userids.split(",").length)]);
								}
							}else {
								if("1".equals(nextTache.getPeople_select_type())) {//手动选择用户
									String selectedUserids = HttpReqtRespContext.getRequest().getParameter(nextTache.getId());
									if(StringUtil.isBlank(selectedUserids)) {
										selectedUserids = StringUtil.ValueOf(HttpReqtRespContext.getRequest().getAttribute(nextTache.getId()));
									}
									if(StringUtil.notBlank(selectedUserids)) {
										if(StringUtil.arrInArr(userids.split(","), selectedUserids.split(","))) {
											if(!"3".equals(nextTache.getTache_type()) && selectedUserids.split(",").length>1) {
												r.setError("只能选择一个用户");
												return r;
											}
											act.setNextUserid(selectedUserids);  
										}else {
											r.setError("选择审批用户有误，请刷新重试");
											return r;
										}
									}else {
										r.setError("请选择审批用户");
										return r;
									}
								}else {
									act.setNextUserid(userids);
									if(!"3".equals(nextTache.getTache_type())) {
										act.setNextUserid(userids.split(",")[new Random().nextInt(userids.split(",").length)]);
									}
								}
							}
						}else {
							r.setError("环节("+nextTache.getName()+")未找到有权限审批人员，请联系管理员");
							return r;
						}
					}
				}else {//复议没有下环节时复议到初始环节
					nextTache = getFirstTache(apply.getActid());
					act.setNextTacheid(nextTache.getId());
					act.setNextUserid(apply.getApplman());  
					isPass = true;
				}
				if(isPass) {
					if("1".equals(result)) {
						updateApplyRecordResult(ProtocolConstant.activitiRecordState.state2, applyRecord.getId());
						addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
						delApplyTache(apply.getId());
						updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state3); 
						updateApplyEndDate(apply.getId());  
						act.setHandlerResult("3");//审批通过
						handleRedirect(act, r);
						return r;
					}else {
						updateApplyRecordResult(ProtocolConstant.activitiRecordState.state3, applyRecord.getId());
						addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
						updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state4); 
						delApplyTache(apply.getId());
						act.setNextApplyTache(addTrendApply(nextTache, applyTache, apply, act));
						act.setHandlerResult("4");//审批复议
						handleRedirect(act, r);
						return r;
					}
				}else {
					updateApplyRecordResult((Integer.parseInt(result)+1)+"", applyRecord.getId());
					addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
					delApplyTache(apply.getId());
					act.setNextApplyTache(addTrendApply(nextTache, applyTache, apply, act));
					if("2".equals(act.getAduittype())) {
						updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state2);
					}
					act.setHandlerResult(result);//1前进  2后退
					handleRedirect(act, r);
					return r;
				}
			}else {//否决
				updateApplyRecordResult(ProtocolConstant.activitiRecordState.state4, applyRecord.getId());
				addApplyOption(act.getOptions(), applyRecord.getId(), act.getResult(), act.getSign_data());
				delApplyTache(apply.getId());
				updateApplyStatus(apply.getId(), ProtocolConstant.activitiApplyState.state5); 
				updateApplyEndDate(apply.getId());  
				cancelApplyRecord(apply.getId());
				act.setHandlerResult("5");//审批否决
				handleRedirect(act, r);
				return r;
			}
			/**************审批结果处理end*********************/
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ActApplyRecord> getActivitiRecords(String applyid) {
		String sql = "select * from v_act_records a where a.applyid=?";
		List list = baseDao.findBySQLEntity(sql, new Object[] {applyid}, ActApplyRecord.class.getName());
		return (List<ActApplyRecord>)list;
	}

	/**
	 * 提交后回调处理
	 * @param act
	 * @throws Exception 
	 */
	private void handleRedirect(ActAudit act, Result r) throws Exception {
		if(StringUtil.notBlank(act.getTache().getRedirect())) {
			try {
				String bean = act.getTache().getRedirect().trim().split("\\.")[0].trim();
				String method = act.getTache().getRedirect().trim().split("\\.")[1].trim();
				Object obj = applicationContext.getBean(bean);
				Method m = obj.getClass().getMethod(method, ActAudit.class);
				m.invoke(obj, act);
				r.success();
			} catch (Exception e) {  
				r.setError("提交后回调配置或处理有误"); 
				throw e;   
			}
		}else {
			r.success();
		}
	}

	@Override
	public void updateApplyRecordResult(String result, String id) {
		String sql = "update sen_act_apply_record set auditing_result=?,end_date=? where id=?";
		baseDao.executeSQL(sql, result, DateUtil.getStringDate(), id);
	}
	@Override
	@Transient
	public Result handleTransfer(ActAudit act, Result r) throws Exception{
		/***************数据校验start********************/
		ActApplyTache applyTache = actApplyTacheSvc.get(act.getApplytacheid());
		if(applyTache == null) {
			r.setError("审批不存在，请刷新重试");
			return r;
		}
		ActApplyInfo apply = get(applyTache.getApplyid());
		ActApplyRecord applyRecord = actApplyRecordSvc.get(applyTache.getRecord_id());
		ActConfigTache tache = actConfigTacheSvc.get(applyTache.getCurr_tache_id());
		if(apply == null || applyRecord == null || tache == null) {
			return r;
		}
		act.setTache(tache);  
		act.setApply(apply);
		act.setApplyRecord(applyRecord);
		act.setApplyTache(applyTache);  
		if("1".equals(tache.getTransfer_sign()) && StringUtil.isBlank(act.getSign_data())) { 
			r.setError("请先签名");
			return r;
		}
		if(!"1".equals(tache.getTransfer_sign())) {
			act.setSign_data(null);  
		}
		if(StringUtil.isBlank(act.getTransferUserid()) || StringUtil.isBlank(act.getTransferReceiveid())) {
			r.setError("数据异常，请刷新重试");
			return r;
		}
		/**************数据校验end*********************/
		
		
		/**************权限校验start*******************/
		if(!"1".equals(tache.getIs_transfer())) {
			r.setError("当前环节不允许移交，请刷新重试");
			return r;
		}
		User user = HttpReqtRespContext.getUser(); 
		if(applyTache.getCurr_user_ids().indexOf(act.getTransferUserid()) == -1) {
			r.setError("数据异常，请刷新重试");
			return r;
		}
		if(act.getTransferUserid().equals(act.getTransferReceiveid())) {
			r.setError("接收人员不能和移交人员相同！");
			return r;
		}
		if(applyTache.getCurr_user_ids().indexOf(act.getTransferReceiveid()) > -1) {
			r.setError("不能移交给当前处理人员！");
			return r;
		}
		boolean isSign = "3".equals(tache.getTache_type());//是否是会签环节
		if(isSign) {//会签判断是否已审批
			List<ActApplyOption> options = getApplyOptions(applyRecord.getId());
			if(options != null && options.size()>0) {
				for(ActApplyOption option:options) {
					if(option.getAdd_user().equals(act.getTransferUserid())) {
						r.setError("数据异常，请刷新重试");
						return r;
					}
				}
			}
		}
		String userids = getApplyTacheUserids(tache.getId(), apply.getId(), "2");//获取有权限移交人员
		if(StringUtil.notBlank(userids)) {
			String temp = "";
			for(String str:userids.split(",")) {
				if(!str.equals(act.getTransferUserid()) && applyTache.getCurr_user_ids().indexOf(str) == -1) {
					temp += ","+str;
				}
			}
			if(StringUtil.isBlank(temp)) {
				r.setError("未找到接收人员，请联系管理员");
				return r;
			}else {
				userids = temp.substring(1);
			}
		}else {
			r.setError("未找到接收人员，请联系管理员");
			return r;
		}
		if("1".equals(tache.getTransfer_people_type())) {//手动选择
			if(userids.indexOf(act.getTransferReceiveid()) == -1 || userSvc.get(act.getTransferReceiveid()) == null) {
				r.setError("数据异常，请刷新重试");
				return r;
			}
		}else {
			act.setTransferReceiveid(userids.split(",")[new Random().nextInt(userids.split(",").length)]);  
		}
		//移交前回调处理
		if(StringUtil.notBlank(tache.getTransfer_saveurl())) {
			try {
				String bean = tache.getTransfer_saveurl().trim().split("\\.")[0].trim();
				String method = tache.getTransfer_saveurl().trim().split("\\.")[1].trim();
				Object obj = applicationContext.getBean(bean);
				Method m = obj.getClass().getMethod(method, ActAudit.class);
				Result temp = (Result)m.invoke(obj, act);
				if(!temp.isSuccess()) {
					r.setError(temp.getError());
					return r;
				}
			} catch (Exception e) {
				logger.error("移交前回调配置有误",e);  
				r.setError("移交前回调配置有误");
				return r;
			}
		}
		/**************权限校验end*********************/
		
		/**************移交结果处理start*******************/
		applyTache.setCurr_user_ids(applyTache.getCurr_user_ids().replace(act.getTransferUserid(), act.getTransferReceiveid()));
		actApplyTacheSvc.update(applyTache);
		if(ProtocolConstant.activitiRecordState.state1.equals(apply.getAudit_state()) || 
				ProtocolConstant.activitiRecordState.state4.equals(apply.getAudit_state())) {//待提交或复议状态时
			apply.setApplman(act.getTransferReceiveid());
			update(apply); 
		}
		if(StringUtil.notBlank(applyRecord.getOver_time()) && applyRecord.getPrescription()!=null) {
			applyRecord.setOver_time(calcOver_time(applyRecord.getPrescription(), DateUtil.getStringDate())); 
			actApplyRecordSvc.update(applyRecord);  
		}
		ActApplyTransferRecord transferRecord = new ActApplyTransferRecord();
		transferRecord.setAdd_time(DateUtil.getStringDate());
		transferRecord.setAdd_user(user.getId());
		transferRecord.setOptions(act.getOptions());
		transferRecord.setRecord_id(applyTache.getRecord_id());
		transferRecord.setTransfer_userid(act.getTransferUserid());
		transferRecord.setReceive_userid(act.getTransferReceiveid());
		if("1".equals(tache.getTransfer_sign())) {  
			transferRecord.setSign_path(FileOperateUtil.saveSignImg(act.getSign_data())); 
		}
		actApplyTransferRecordSvc.save(transferRecord);
		if(StringUtil.notBlank(act.getTache().getTransfer_redirect())) {
			try {
				String bean = act.getTache().getTransfer_redirect().trim().split("\\.")[0].trim();
				String method = act.getTache().getTransfer_redirect().trim().split("\\.")[1].trim();
				Object obj = applicationContext.getBean(bean);
				Method m = obj.getClass().getMethod(method, ActAudit.class);
				m.invoke(obj, act);
				r.success();
			} catch (Exception e) {  
				r.setError("移交后回调配置或处理有误"); 
				throw e;   
			}
		}else {
			r.success();
		}
		/**************移交结果处理end*********************/
		return r;
	}

	/**
	 * 根据caseid 查询
	 * @param caseId
	 * @return
	 * @throws Exception
	 * 2018年8月2日-上午8:18:57
	 * YY
	 */
	@Override
	public ActApplyInfo getActApplyInfoBycaseId(String caseId) throws Exception {
		caseId = StringUtil.sNull(caseId);
		if("".equals(caseId))throw new MyServiceException("参数传递有误");
		String sql = "select * from sen_act_apply_info where caseid=?";
		List<Object> param = new ArrayList<Object>();
		param.add(caseId);
		List<ActApplyInfo> list =	(List<ActApplyInfo>) baseDao.findBySQLEntity(sql, param);
		if(null!=list&&!list.isEmpty())
		return list.get(0);
		return null;
	}
}
