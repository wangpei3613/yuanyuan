package com.sensebling.activiti.controller;

import java.util.ArrayList;
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
import com.sensebling.activiti.entity.ActConfigTache;
import com.sensebling.activiti.service.ActConfigTacheSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 流程配置环节表
 *
 */
@Controller
@RequestMapping("/act/config/tache")
public class ActConfigTacheCtrl extends BasicsCtrl{
	
	@Resource
	private ActConfigTacheSvc actConfigTacheSvc;
	/**
	 * 查询流程配置环节列表
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
			qp.addParamter("del", 1, QueryCondition.if_null);
			pager = actConfigTacheSvc.select(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增流程环节
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tache")
	@DisposableToken
	public Result addRole(ActConfigTache v){
		Result r = new Result();
		if(actConfigTacheSvc.checkTachecode(v.getTachecode(),v.getActid(),v.getId())) {
			v.setIs_to_people("1");   
			actConfigTacheSvc.save(v);
			r.success();  
		}else {
			r.setError("环节编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改流程环节
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tache")
	@DisposableToken
	public Result upRole(ActConfigTache v){
		Result r = new Result();
		if(actConfigTacheSvc.checkTachecode(v.getTachecode(),v.getActid(),v.getId())) {
			actConfigTacheSvc.update(v);
			r.success();  
		}else {
			r.setError("环节编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除流程环节
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("workFlow:tache")
	@DisposableToken
	public Result delRole(String data){
		Result r = new Result();
		List<ActConfigTache> list = new Gson().fromJson(data, new TypeToken<List<ActConfigTache>>(){}.getType());
		for(ActConfigTache config:list) {
			ActConfigTache info = actConfigTacheSvc.get(config.getId());
			if(info != null) {
				if(actConfigTacheSvc.delBeforeCheck(info.getId())) {
					info.setDel("1");
					actConfigTacheSvc.update(info);
					r.success();
				}else {
					r.setError("环节已被使用，不能删除");
					break;
				}
			}
		}
		return crudError(r);
	}
	@RequestMapping(value="/getTacheDict")
	@ResponseBody
	public List<Map<String, Object>> getTacheDict(String actid){
		List<Map<String, Object>> list =null;
		QueryParameter qp = new QueryParameter();
		qp.addParamter("actid", actid);
		qp.addParamter("del", 1, QueryCondition.if_null);
		qp.setSortField("ordernum"); 
		List<ActConfigTache> datas = actConfigTacheSvc.findAll(qp);
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(datas!=null && datas.size()>0) {
			for(ActConfigTache d:datas) {
				map = new HashMap<String, Object>();
				map.put("code", d.getId());
				map.put("name", d.getName());
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 进入环节配置页面
	 * @return
	 */
	@RequestMapping("/index")
	@ModuleAuth("workFlow")
	public String index(){
		return "activiti/configTache/index";
	}
	/**
	 * 进入环节配置编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	@ModuleAuth("workFlow")
	public String edit(String id){
		ActConfigTache v = new ActConfigTache();
		if(StringUtil.notBlank(id)) {
			v = actConfigTacheSvc.get(id);
		}else {
			v.setActid(getRequestParam("actid"));   
		}
		getRequest().setAttribute("v", v);  
		return "activiti/configTache/edit";
	}
}
