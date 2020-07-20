package com.sensebling.activiti.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.activiti.entity.ActConfigTacheOperator;
import com.sensebling.activiti.service.ActConfigTacheOperatorSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 流程配置环节人员规则表
 *
 */
@Controller
@RequestMapping("/act/config/tache/operator")
public class ActConfigTacheOperatorCtrl extends BasicsCtrl{
	
	@Resource
	private ActConfigTacheOperatorSvc actConfigTacheOperatorSvc;
	/**
	 * 查询流程配置环节人员规则列表
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody
	@ModuleAuth("workFlow")
	public Result select(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		Pager pager = qp.getPager(); 
		if(StringUtil.notBlank(qp.getParam("tacheid")) && StringUtil.notBlank(qp.getParam("category"))) {         
			pager = actConfigTacheOperatorSvc.select(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增流程环节人员规则
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tacheOperator")
	@DisposableToken
	public Result addRole(ActConfigTacheOperator v){
		Result r = new Result();
		if(v.getType() == 1 || v.getType() == 2 || v.getType() == 3) {
			String[] arr = v.getValues().split(",");
			QueryParameter qp = new QueryParameter();
			qp.addParamter("tacheid", v.getTacheid());
			qp.addParamter("type", v.getType());
			qp.addParamter("category", v.getCategory());
			for(String value:arr) {
				qp.addParamter("value", value);
				if(actConfigTacheOperatorSvc.findAllCount(qp) == 0) {
					ActConfigTacheOperator info = new ActConfigTacheOperator();
					info.setCategory(v.getCategory());
					info.setRemark(v.getRemark());
					info.setTacheid(v.getTacheid());
					info.setType(v.getType());
					info.setValue(value);
					actConfigTacheOperatorSvc.save(info);
				}
			}
		}else {
			actConfigTacheOperatorSvc.save(v);
		}
		r.success(); 
		return crudError(r);
	}
	/**
	 * 修改流程环节人员规则
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tacheOperator")
	@DisposableToken
	public Result upRole(ActConfigTacheOperator v){
		Result r = new Result();
		ActConfigTacheOperator info = actConfigTacheOperatorSvc.get(v.getId());
		if(v.getType() == 1 || v.getType() == 2 || v.getType() == 3) {
			r.setError("选取规则为用户、角色、岗位、部门的不允许直接修改，请通过先删除再新建操作实现修改");
		}else {
			info.setRemark(v.getRemark());
			info.setValue(v.getValue());
			actConfigTacheOperatorSvc.update(info);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 删除流程环节人员规则
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tacheOperator")
	@DisposableToken
	public Result delRole(String data){
		Result r = new Result();
		List<ActConfigTacheOperator> list = new Gson().fromJson(data, new TypeToken<List<ActConfigTacheOperator>>(){}.getType());
		for(ActConfigTacheOperator config:list) {
			actConfigTacheOperatorSvc.delete(config.getId());
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 进入环节人员配置页面
	 * @return
	 */
	@RequestMapping("/index")
	@ModuleAuth("workFlow")
	public String index(){
		return "activiti/configTacheOperator/index";
	}
	/**
	 * 进入环节人员配置编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	@ModuleAuth("workFlow")
	public String edit(String id){
		ActConfigTacheOperator v = new ActConfigTacheOperator();
		if(StringUtil.notBlank(id)) {
			v = actConfigTacheOperatorSvc.get(id);
		}else {
			v.setTacheid(getRequestParam("tacheid"));
			v.setCategory(getRequestParam("category"));  
		}
		getRequest().setAttribute("v", v);  
		return "activiti/configTacheOperator/edit";
	}
}
