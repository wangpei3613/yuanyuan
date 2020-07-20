package com.sensebling.activiti.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.activiti.entity.ActConfigInfo;
import com.sensebling.activiti.service.ActConfigInfoSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 流程配置主表
 *
 */
@Controller
@RequestMapping("/act/config/info")
public class ActConfigInfoCtrl extends BasicsCtrl{
	@Resource
	private ActConfigInfoSvc actConfigInfoSvc;
	/**
	 * 查询流程配置列表
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody
	@ModuleAuth("workFlow")
	public Result select(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("del", 1, QueryCondition.if_null);
		r.setData(actConfigInfoSvc.findPage(qp));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增流程
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:openAdd")
	@DisposableToken
	public Result addRole(ActConfigInfo config){
		Result r = new Result();
		config.setEnabled("2");  
		config.setCreateDate(DateUtil.getStringDate());
		config.setCreateUser(getUser().getId());  
		actConfigInfoSvc.save(config);
		r.success();
		return crudError(r);
	}
	/**
	 * 修改流程
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:openEdit")
	@DisposableToken
	public Result update(ActConfigInfo config){
		Result r = new Result();
		ActConfigInfo info = actConfigInfoSvc.get(config.getId());
		if(info != null) {
			if(StringUtil.isBlank(info.getDel())) {
				info.setEnabled("2");
				info.setName(config.getName());
				info.setProcesscode(config.getProcesscode());
				info.setRemark(config.getRemark());
				config.setUpdateDate(DateUtil.getStringDate());
				config.setUpdateUser(getUser().getId());  
				actConfigInfoSvc.update(info);
				r.success();
			}else {
				r.setError("当前流程已被删除，不能修改");
			}
		}
		return crudError(r);
	}
	/**
	 * 删除流程
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:remove")
	@DisposableToken
	public Result delRole(String data){
		Result r = new Result();
		List<ActConfigInfo> list = new Gson().fromJson(data, new TypeToken<List<ActConfigInfo>>(){}.getType());
		for(ActConfigInfo config:list) {
			ActConfigInfo info = actConfigInfoSvc.get(config.getId());
			if(info != null) {
				if("1".equals(info.getEnabled())) {
					r.setError("启用的流程不能删除，请先禁用");
					break;
				}else {
					info.setDel("1");
					info.setEnabled("2");
					actConfigInfoSvc.update(info);
					r.success();
				}
			}
		}
		return crudError(r);
	}
	/**
	 * 启用流程
	 */
	@RequestMapping(value="/setEnable",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:setEnable")
	@DisposableToken
	public Result setEnable(String id){
		Result r = new Result();
		ActConfigInfo info = actConfigInfoSvc.get(id);
		if(info != null) {
			if(StringUtil.isBlank(info.getDel())) {  
				actConfigInfoSvc.setEnable(info);
				r.success();
			}else {
				r.setError("当前流程已删除，不能启用");
			}
		}
		return crudError(r);
	}
	/**
	 * 禁用流程
	 */
	@RequestMapping(value="/setDisable",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:setDisable")
	@DisposableToken
	public Result setDisable(String id){
		Result r = new Result();
		ActConfigInfo info = actConfigInfoSvc.get(id);
		if(info != null) {
			info.setEnabled("2");
			actConfigInfoSvc.update(info);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 进入流程设置主页
	 * @return
	 */
	@RequestMapping("/index")
	@ModuleAuth("workFlow")
	public String index() {
		return "activiti/index";
	}
	/**
	 * 进入流程配置页面
	 * @return
	 */
	@RequestMapping("/toList")
	@ModuleAuth("workFlow")
	public String toList(){
		return "activiti/configInfo/index";
	}
	/**
	 * 进入流程配置编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	@ModuleAuth("workFlow")
	public String edit(String id){
		ActConfigInfo v = new ActConfigInfo();
		if(StringUtil.notBlank(id)) {
			v = actConfigInfoSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "activiti/configInfo/edit";
	}
}
