package com.sensebling.core.risk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.core.risk.entity.CoreRisk;
import com.sensebling.core.risk.entity.CoreRiskRule;
import com.sensebling.core.risk.service.CoreRiskRuleSvc;
import com.sensebling.core.risk.service.CoreRiskSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 风险模型
 *
 */
@Controller
@RequestMapping("/sen/core/risk")
public class CoreRiskCtrl extends BasicsCtrl{
	@Resource
	private CoreRiskRuleSvc coreRiskRuleSvc;
	@Resource
	private CoreRiskSvc coreRiskSvc;
	/**
	 * 查询风险模型列表
	 * @return
	 */
	@RequestMapping(value="/queryRiskInfo",method=RequestMethod.GET)
	@ResponseBody
	public Result queryRiskInfo() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("del", 1, QueryCondition.if_null);
		r.setData(coreRiskSvc.findPage(qp));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增风险模型
	 */
	@RequestMapping(value="/addRiskInfo",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("riskManage:openAdd")
	@DisposableToken
	public Result addRiskInfo(CoreRisk v) {
		Result r = new Result();
		v.setAddtime(DateUtil.getStringDate());
		v.setAdduser(getUser().getId());
		v.setStatus("2");  
		coreRiskSvc.save(v);
		r.success();
		return crudError(r);
	}
	/**
	 * 修改风险模型
	 */
	@RequestMapping(value="/editRiskInfo",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("riskManage:openEdit")
	@DisposableToken
	public Result editRiskInfo(CoreRisk v) {
		Result r = new Result();
		CoreRisk risk = coreRiskSvc.get(v.getId());
		if(risk != null && StringUtil.isBlank(risk.getDel())) {  
			risk.setUpdatetime(DateUtil.getStringDate());
			risk.setUpdateuser(getUser().getId());
			risk.setName(v.getName());
			risk.setRemark(v.getRemark());
			coreRiskSvc.update(risk);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 删除风险模型
	 */
	@RequestMapping(value="/delRiskInfo",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("riskManage:remove")
	@DisposableToken
	public Result delRiskInfo(String data) {
		Result r = new Result();
		List<CoreRisk> list = new Gson().fromJson(data, new TypeToken<List<CoreRisk>>(){}.getType());
		for(CoreRisk v:list) {
			CoreRisk risk = coreRiskSvc.get(v.getId());
			if(risk != null) {
				if(!"1".equals(risk.getStatus())) {
					risk.setUpdatetime(DateUtil.getStringDate());
					risk.setUpdateuser(getUser().getId());
					risk.setDel("1"); 
					risk.setStatus("2");
					coreRiskSvc.update(risk);   
					r.success();
				}else {
					r.setError("当前模型已启用，不能删除");
				}
			}
		}
		return crudError(r);
	}
	/**
	 * 查询风险模型规则列表
	 * @return
	 */
	@RequestMapping(value="/queryRiskRule",method=RequestMethod.GET)
	@ResponseBody
	public Result queryRiskRule() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		if(!StringUtil.isBlank(qp.getParam("riskid"))) {
			r.setData(coreRiskRuleSvc.findPage(qp));
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 新增风险模型规则
	 */
	@RequestMapping(value="/addRiskRule",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addRiskRule(CoreRiskRule v) {
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riskid", v.getRiskid());
		map.put("code", v.getCode());
		if(coreRiskRuleSvc.checkColumns(map)) {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			coreRiskRuleSvc.save(v);
			r.success();
		}else {
			r.setError("规则编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改风险模型规则
	 */
	@RequestMapping(value="/editRiskRule",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result editRiskRule(CoreRiskRule v) {
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riskid", v.getRiskid());
		map.put("code", v.getCode());
		if(coreRiskRuleSvc.checkColumns(map, v.getId())) {
			CoreRiskRule rule = coreRiskRuleSvc.get(v.getId());
			if(rule != null) {  
				rule.setUpdatetime(DateUtil.getStringDate());
				rule.setUpdateuser(getUser().getId());
				rule.setName(v.getName());
				rule.setRemark(v.getRemark());
				rule.setCode(v.getCode());
				rule.setContent(v.getContent());
				rule.setRule(v.getRule());
				rule.setSort(v.getSort());
				rule.setStatus(v.getStatus());
				rule.setTitle(v.getTitle());
				rule.setCondition(v.getCondition()); 
				coreRiskRuleSvc.update(rule);
				r.success();
			}
		}else {
			r.setError("规则编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除风险模型规则
	 */
	@RequestMapping(value="/delRiskRule",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delRiskRule(String data) {
		Result r = new Result();
		List<CoreRiskRule> list = new Gson().fromJson(data, new TypeToken<List<CoreRiskRule>>(){}.getType());
		for(CoreRiskRule v:list) {
			coreRiskRuleSvc.delete(v);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 启用风险模型
	 */
	@RequestMapping(value="/setEnable",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("riskManage:setEnable")
	@DisposableToken
	public Result setEnable(String id){
		Result r = new Result();
		CoreRisk risk = coreRiskSvc.get(id);
		if(risk != null) {
			if(StringUtil.isBlank(risk.getDel())) {  
				coreRiskSvc.setEnable(risk);
				r.success();
			}else {
				r.setError("当前模型已删除，不能启用");
			}
		}
		return crudError(r);
	}
	/**
	 * 禁用风险模型
	 */
	@RequestMapping(value="/setDisable",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("riskManage:setDisable")
	@DisposableToken
	public Result setDisable(String id){
		Result r = new Result();
		CoreRisk risk = coreRiskSvc.get(id);
		if(risk != null) {
			risk.setStatus("2");
			coreRiskSvc.update(risk);
			r.success();
		}
		return crudError(r);
	}
	@RequestMapping(value="/test")
	@ResponseBody
	public Result test(){
		return crudError(coreRiskSvc.calcRule("creditApply", null));
	}
	/**
	 * 进入风险模型配置页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/core/risk/index";
	}
	/**
	 * 进入风险模型列表页面
	 * @return
	 */
	@RequestMapping("/toRisk")
	public String toRisk() {
		return "/core/risk/risk";
	}
	/**
	 * 进入风险模型编辑页面
	 * @return
	 */
	@RequestMapping("/toRiskEdit")
	public String toRiskEdit(String id) {
		CoreRisk v = new CoreRisk();
		if(StringUtil.notBlank(id)) {
			v = coreRiskSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "/core/risk/riskEdit";
	}
	/**
	 * 进入风险模型规则页面
	 * @return
	 */
	@RequestMapping("/toRiskRule")
	public String toAlloRule() {
		return "/core/risk/riskRule";
	}
	/**
	 * 进入风险模型规则编辑页面
	 * @return
	 */
	@RequestMapping("/toRiskRuleEdit")
	public String toRiskRuleEdit(String id) {
		CoreRiskRule v = new CoreRiskRule();
		if(StringUtil.notBlank(id)) {
			v = coreRiskRuleSvc.get(id);
		}else {
			v.setRiskid(getRequestParam("riskid"));   
		}
		getRequest().setAttribute("v", v);  
		return "/core/risk/riskRuleEdit";
	}
}
