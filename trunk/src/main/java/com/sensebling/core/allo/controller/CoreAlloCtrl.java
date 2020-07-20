package com.sensebling.core.allo.controller;

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
import com.sensebling.core.allo.entity.CoreAllo;
import com.sensebling.core.allo.entity.CoreAlloRule;
import com.sensebling.core.allo.service.CoreAlloRuleSvc;
import com.sensebling.core.allo.service.CoreAlloSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 分配模型
 *
 */
@Controller
@RequestMapping("/sen/core/allo")
public class CoreAlloCtrl extends BasicsCtrl{
	@Resource
	private CoreAlloRuleSvc coreAlloRuleSvc;
	@Resource
	private CoreAlloSvc coreAlloSvc;
	/**
	 * 查询分配模型列表
	 * @return
	 */
	@RequestMapping(value="/queryAlloInfo",method=RequestMethod.GET)
	@ResponseBody
	public Result queryAlloInfo() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("del", 1, QueryCondition.if_null);
		r.setData(coreAlloSvc.findPage(qp));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增分配模型
	 */
	@RequestMapping(value="/addAlloInfo",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("alloManage:openAdd")
	@DisposableToken
	public Result addAlloInfo(CoreAllo v) {
		Result r = new Result();
		v.setAddtime(DateUtil.getStringDate());
		v.setAdduser(getUser().getId());
		v.setStatus("2");  
		coreAlloSvc.save(v);
		r.success();
		return crudError(r);
	}
	/**
	 * 修改分配模型
	 */
	@RequestMapping(value="/editAlloInfo",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("alloManage:openEdit")
	@DisposableToken
	public Result editAlloInfo(CoreAllo v) {
		Result r = new Result();
		CoreAllo allo = coreAlloSvc.get(v.getId());
		if(allo != null && StringUtil.isBlank(allo.getDel())) {  
			allo.setUpdatetime(DateUtil.getStringDate());
			allo.setUpdateuser(getUser().getId());
			allo.setName(v.getName());
			allo.setRemark(v.getRemark());
			coreAlloSvc.update(allo);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 删除分配模型
	 */
	@RequestMapping(value="/delAlloInfo",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("alloManage:remove")
	@DisposableToken
	public Result delAlloInfo(String data) {
		Result r = new Result();
		List<CoreAllo> list = new Gson().fromJson(data, new TypeToken<List<CoreAllo>>(){}.getType());
		for(CoreAllo v:list) {
			CoreAllo allo = coreAlloSvc.get(v.getId());
			if(allo != null) {
				if(!"1".equals(allo.getStatus())) {
					allo.setUpdatetime(DateUtil.getStringDate());
					allo.setUpdateuser(getUser().getId());
					allo.setDel("1"); 
					allo.setStatus("2");
					coreAlloSvc.update(allo);   
					r.success();
				}else {
					r.setError("当前模型已启用，不能删除");
				}
			}
		}
		return crudError(r);
	}
	/**
	 * 查询分配模型规则列表
	 * @return
	 */
	@RequestMapping(value="/queryAlloRule",method=RequestMethod.GET)
	@ResponseBody
	public Result queryAlloRule() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		if(!StringUtil.isBlank(qp.getParam("alloid"))) {
			r.setData(coreAlloRuleSvc.findPage(qp));
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 新增分配模型规则
	 */
	@RequestMapping(value="/addAlloRule",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addAlloRule(CoreAlloRule v) {
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alloid", v.getAlloid());
		map.put("code", v.getCode());
		if(coreAlloRuleSvc.checkColumns(map)) {
			map.remove("code");
			map.put("sort", v.getSort());
			if(coreAlloRuleSvc.checkColumns(map)) {
				v.setAddtime(DateUtil.getStringDate());
				v.setAdduser(getUser().getId());
				coreAlloRuleSvc.save(v);
				r.success();
			}else {
				r.setError("规则优先级重复");
			}
		}else {
			r.setError("规则编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改分配模型规则
	 */
	@RequestMapping(value="/editAlloRule",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result editAlloRule(CoreAlloRule v) {
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alloid", v.getAlloid());
		map.put("code", v.getCode());
		if(coreAlloRuleSvc.checkColumns(map, v.getId())) {
			map.remove("code");
			map.put("sort", v.getSort());
			if(coreAlloRuleSvc.checkColumns(map, v.getId())) {
				CoreAlloRule rule = coreAlloRuleSvc.get(v.getId());
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
					rule.setIsend(v.getIsend());
					rule.setCondition(v.getCondition());
					coreAlloRuleSvc.update(rule);
					r.success();
				}
			}else {
				r.setError("规则优先级重复");
			}
		}else {
			r.setError("规则编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除分配模型规则
	 */
	@RequestMapping(value="/delAlloRule",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delAlloRule(String data) {
		Result r = new Result();
		List<CoreAlloRule> list = new Gson().fromJson(data, new TypeToken<List<CoreAlloRule>>(){}.getType());
		for(CoreAlloRule v:list) {
			coreAlloRuleSvc.delete(v);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 启用分配模型
	 */
	@RequestMapping(value="/setEnable",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("alloManage:setEnable")
	@DisposableToken
	public Result setEnable(String id){
		Result r = new Result();
		CoreAllo allo = coreAlloSvc.get(id);
		if(allo != null) {
			if(StringUtil.isBlank(allo.getDel())) {  
				coreAlloSvc.setEnable(allo);
				r.success();
			}else {
				r.setError("当前模型已删除，不能启用");
			}
		}
		return crudError(r);
	}
	/**
	 * 禁用分配模型
	 */
	@RequestMapping(value="/setDisable",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("alloManage:setDisable")
	@DisposableToken
	public Result setDisable(String id){
		Result r = new Result();
		CoreAllo allo = coreAlloSvc.get(id);
		if(allo != null) {
			allo.setStatus("2");
			coreAlloSvc.update(allo);
			r.success();
		}
		return crudError(r);
	}
	@RequestMapping(value="/test")
	@ResponseBody
	public Result test(){
		return coreAlloSvc.calcRule("investMan", null);  
	}
	/**
	 * 进入分配模型配置页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/core/allo/index";
	}
	/**
	 * 进入分配模型列表页面
	 * @return
	 */
	@RequestMapping("/toAllo")
	public String toAllo() {
		return "/core/allo/allo";
	}
	/**
	 * 进入分配模型编辑页面
	 * @return
	 */
	@RequestMapping("/toAlloEdit")
	public String toAlloEdit(String id) {
		CoreAllo v = new CoreAllo();
		if(StringUtil.notBlank(id)) {
			v = coreAlloSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "/core/allo/alloEdit";
	}
	/**
	 * 进入分配模型规则页面
	 * @return
	 */
	@RequestMapping("/toAlloRule")
	public String toAlloRule() {
		return "/core/allo/alloRule";
	}
	/**
	 * 进入分配模型规则编辑页面
	 * @return
	 */
	@RequestMapping("/toAlloRuleEdit")
	public String toAlloRuleEdit(String id) {
		CoreAlloRule v = new CoreAlloRule();
		if(StringUtil.notBlank(id)) {
			v = coreAlloRuleSvc.get(id);
		}else {
			v.setAlloid(getRequestParam("alloid"));   
		}
		getRequest().setAttribute("v", v);  
		return "/core/allo/alloRuleEdit";
	}
}
