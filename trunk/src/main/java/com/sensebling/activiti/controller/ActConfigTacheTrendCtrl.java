package com.sensebling.activiti.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.activiti.entity.ActConfigTacheTrend;
import com.sensebling.activiti.service.ActConfigTacheTrendSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 流程配置环节走向
 *
 */
@Controller
@RequestMapping("/act/config/tache/trend")
public class ActConfigTacheTrendCtrl extends BasicsCtrl{
	
	@Resource
	private ActConfigTacheTrendSvc actConfigTacheTrendSvc;
	/**
	 * 查询流程配置环节走向列表
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody
	@ModuleAuth("workFlow")
	public Result select(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		Pager pager = qp.getPager(); 
		if(StringUtil.notBlank(qp.getParam("actid"))) {     
			pager = actConfigTacheTrendSvc.select(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增流程环节走向
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tacheTrend")
	@DisposableToken
	public Result addRole(ActConfigTacheTrend v){
		Result r = new Result();
		actConfigTacheTrendSvc.save(v);
		r.success();  
		return crudError(r);
	}
	/**
	 * 修改流程环节走向
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tacheTrend")
	@DisposableToken
	public Result upRole(ActConfigTacheTrend v){
		Result r = new Result();
		actConfigTacheTrendSvc.update(v);
		r.success();
		return crudError(r);
	}
	/**
	 * 删除流程环节走向
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tacheTrend")
	@DisposableToken
	public Result delRole(String data){
		Result r = new Result();
		List<ActConfigTacheTrend> list = new Gson().fromJson(data, new TypeToken<List<ActConfigTacheTrend>>(){}.getType());
		for(ActConfigTacheTrend config:list) {
			actConfigTacheTrendSvc.delete(config);
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 进入环节走向配置页面
	 * @return
	 */
	@RequestMapping("/index")
	@ModuleAuth("workFlow")
	public String index(){
		return "activiti/configTacheTrend/index";
	}
	/**
	 * 进入环节走向配置编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	@ModuleAuth("workFlow")
	public String edit(String id){
		ActConfigTacheTrend v = new ActConfigTacheTrend();
		if(StringUtil.notBlank(id)) {
			v = actConfigTacheTrendSvc.get(id);
		}else {
			v.setActid(getRequestParam("actid"));  
		}
		getRequest().setAttribute("v", v);  
		return "activiti/configTacheTrend/edit";
	}
}
