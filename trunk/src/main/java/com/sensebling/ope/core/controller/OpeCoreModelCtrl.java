package com.sensebling.ope.core.controller;

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
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.ope.core.entity.OpeCoreModel;
import com.sensebling.ope.core.service.OpeCoreModelSvc;
import com.sensebling.ope.core.service.OpeCoreProductSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 模型
 *
 */
@Controller
@RequestMapping("/ope/core/model")
public class OpeCoreModelCtrl extends BasicsCtrl{
	@Resource
	private OpeCoreModelSvc opeCoreModelSvc;
	@Resource
	private OpeCoreProductSvc opeCoreProductSvc;
	
	
	/**
	 * 进入模型管理页面
	 * @return
	 */
	@RequestMapping("/queryModelPage")
	public String queryModelPage() {
		return "/ope/core/model/queryModelPage";
	}
	
	/**
	 * 进入修改模型管理页面
	 * @return
	 */
	@RequestMapping("/editModel")
	public String editModel(String id) {
		OpeCoreModel model = new OpeCoreModel();
		if(StringUtil.notBlank(id)) {
			model = opeCoreModelSvc.get(id);
		}
		getRequest().setAttribute("model", model); 
		return "/ope/core/model/editModel";
	}
	
	/**
	 * 进入分配指标版本页面
	 * @return
	 */
	@RequestMapping("/alloVersion")
	public String alloVersion(String id) {
		return "/ope/core/model/alloVersion";
	}
	
	
	/**
	 * 查询模型列表
	 * @return
	 */
	@RequestMapping(value="/getModelPager")
	@ResponseBody
	public Result getModelPager() {
		Result r = new Result();
		r.setData(opeCoreModelSvc.getModelPager(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增模型
	 */
	@RequestMapping(value="/addModel",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addModel(OpeCoreModel v) {
		Result r = new Result();
		if(opeCoreModelSvc.checkCode(v.getCode(), v.getId())) {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			//opeCoreModelSvc.save(v);
			r.success();
		}else {
			r.setError("模型编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改模型
	 */
	@RequestMapping(value="/updateModel",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("modelManage:openEdit")
	@DisposableToken
	public Result updateModel(OpeCoreModel v) {
		Result r = new Result();
		if(opeCoreModelSvc.checkCode(v.getCode(), v.getId())) {
			OpeCoreModel model = opeCoreModelSvc.get(v.getId());
			model.setName(v.getName());
			model.setRemark(v.getRemark());
			model.setStatus(v.getStatus());
			model.setUpdatetime(DateUtil.getStringDate());
			model.setUpdateuser(getUser().getId());
			opeCoreModelSvc.update(model);
			r.success();
		}else {
			r.setError("模型编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除模型
	 */
	@RequestMapping(value="/delModel",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delModel(String data) {
		Result r = new Result();
		List<OpeCoreModel> list = new Gson().fromJson(data, new TypeToken<List<OpeCoreModel>>(){}.getType());
		for(OpeCoreModel v:list) {
			OpeCoreModel model = opeCoreModelSvc.get(v.getId());
			if(model != null) {
				QueryParameter qp = new QueryParameter();
				qp.addParamter("modelid", v.getId());
				if(opeCoreProductSvc.findAllCount(qp) == 0) {
					//opeCoreModelSvc.delete(v);   
					r.success();
				}else {
					r.setError("当前模型已被产品使用，不能删除");
				}
			}
		}
		return crudError(r);
	}
	/**
	 * 模型分配指标版本
	 */
	@RequestMapping(value="/alloVersion",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("modelManage:alloVersion")
	@DisposableToken
	public Result alloVersion(String id, String versionid) {
		return crudError(opeCoreModelSvc.updateVersion(id, versionid));
	}
	/**
	 * 查看模型列表(启用)
	 * @return
	 */
	@RequestMapping(value="/getModelList")
	@ResponseBody
	public Result getModelList() {
		Result r = new Result();
		QueryParameter qp = new QueryParameter();
		qp.addParamter("status", "1");
		qp.setSortField("addtime");
		qp.setSortOrder("desc");
		r.setData(opeCoreModelSvc.findAll(qp));  
		r.success();
		return crudError(r);
	}
	/**
	 * 获取模型列表  用于字典
	 */
	@RequestMapping(value="/getModelDict")
	@ResponseBody
	public Result getModelDict() {
		Result r = new Result();
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		QueryParameter qp = new QueryParameter();
		qp.setSortField("adduser");
		List<OpeCoreModel> list = opeCoreModelSvc.findAll(qp);
		if(list!=null && list.size()>0) {
			Map<String, Object> map = null;
			for(OpeCoreModel model:list) {
				map = new HashMap<String, Object>();
				map.put("code", model.getId());
				map.put("name", model.getName());
				map.put("modelcode", model.getCode());
				temp.add(map);
			}
		}
		r.setData(temp);
		r.success();
		return crudError(r);
	}
	
	/**
	 * 获取模型列表  用于字典
	 */
	@RequestMapping(value="/queryModelDict")
	@ResponseBody
	public List<Map<String,String>> queryModelDict(String isSelect) {
			List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
			QueryParameter qp = new QueryParameter();
			qp.setSortField("adduser");
			List<OpeCoreModel> list = opeCoreModelSvc.findAll(qp);
			if(StringUtil.notBlank(isSelect)) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("text", "--请选择--");
				map.put("value", "");
				map.put("code", "");
				temp.add(map);
			}
			if(null ==list || list.size() == 0) 
				return temp;
			for(OpeCoreModel model:list) {
				Map<String,String> map=new HashMap<String, String>();
				map.put("text", model.getName());
				map.put("value", model.getId());
				map.put("code", model.getCode());
				temp.add(map);
			}
		return temp;
	}
}
