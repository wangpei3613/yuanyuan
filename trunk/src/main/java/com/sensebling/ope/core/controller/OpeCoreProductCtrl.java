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
import com.sensebling.ope.core.entity.OpeCoreProduct;
import com.sensebling.ope.core.service.OpeCoreProductSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 产品
 *
 */
@Controller
@RequestMapping("/ope/core/product")
public class OpeCoreProductCtrl extends BasicsCtrl{
	@Resource
	private OpeCoreProductSvc opeCoreProductSvc;
	/**
	 * 查询产品列表
	 * @return
	 */
	@RequestMapping(value="/getProductPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getProductPager() {
		Result r = new Result();
		r.setData(opeCoreProductSvc.getProductPager(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增产品
	 */
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("productManage:openAdd")
	@DisposableToken
	public Result addProduct(OpeCoreProduct v) {
		Result r = new Result();
		if(opeCoreProductSvc.checkCode(v.getCode(), v.getId())) {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			opeCoreProductSvc.save(v);
			r.success();
		}else {
			r.setError("产品编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改产品
	 */
	@RequestMapping(value="/updateProduct",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("productManage:openEdit")
	@DisposableToken
	public Result updateProduct(OpeCoreProduct v) {
		Result r = new Result();
		if(opeCoreProductSvc.checkCode(v.getCode(), v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			opeCoreProductSvc.update(v);
			r.success();
		}else {
			r.setError("产品编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除产品
	 */
	@RequestMapping(value="/delProduct",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("productManage:remove")
	@DisposableToken
	public Result delProduct(String data) {
		Result r = new Result();
		List<OpeCoreProduct> list = new Gson().fromJson(data, new TypeToken<List<OpeCoreProduct>>(){}.getType());
		for(OpeCoreProduct v:list) {
			r = opeCoreProductSvc.delProduct(v.getId());   
		}
		return crudError(r);
	}
	/**
	 * 产品分配模型
	 */
	@RequestMapping(value="/alloModel",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("productManage:alloModel")
	@DisposableToken
	public Result alloModel(String id, String modelid) {
		return crudError(opeCoreProductSvc.updateModel(id, modelid));
	}
	/**
	 * 获取产品列表  用于字典
	 * @param status 状态
	 */
	@RequestMapping(value="/getProductDict")
	@ResponseBody
	public Result getProductDict(String status, String types) {
		Result r = new Result();
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		QueryParameter qp = new QueryParameter();
		qp.addParamter("status", status);
		qp.addParamter("types", types);
		qp.setSortField("addtime");
		List<OpeCoreProduct> list = opeCoreProductSvc.findAll(qp);
		if(list!=null && list.size()>0) {
			Map<String, Object> map = null;
			for(OpeCoreProduct product:list) {
				map = new HashMap<String, Object>();
				map.put("code", product.getId());
				map.put("name", product.getName());
				map.put("modelid", product.getModelid());
				temp.add(map);
			}
		}
		r.setData(temp);
		r.success();
		return crudError(r);
	}
	
	/**
	 * 查询字典表，用于下拉列表
	 * isSelect:是否显示请选择
	 * @return
	 */
	@RequestMapping(value="/queryProductDict")
	public @ResponseBody List<Map<String,String>> queryProductDict(String status,String isSelect, String types){
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		QueryParameter qp = new QueryParameter();
		qp.addParamter("status", status);
		qp.addParamter("types", types);
		qp.setSortField("addtime");
		List<OpeCoreProduct> list = opeCoreProductSvc.findAll(qp);
		if(StringUtil.notBlank(isSelect)) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("text", "--请选择--");
			map.put("value", "");
			map.put("modelId", "");
			map.put("modelCode", "");
			temp.add(map);
		}
		if(null == list || list.size() == 0) 
			return temp;
		for(OpeCoreProduct v:list) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", v.getName());
			map.put("value", v.getId());
			map.put("modelId", v.getModelid());
			map.put("modelCode", v.getCode());
			temp.add(map);
		}
		return temp;
	}
	/**
	 * 进入产品配置页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/ope/core/product/index";
	}
	/**
	 * 进入产品编辑页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		OpeCoreProduct v = new OpeCoreProduct();
		if(StringUtil.notBlank(id)) {
			v = opeCoreProductSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "/ope/core/product/edit";
	}
	/**
	 * 进入产品配置页面
	 * @return
	 */
	@RequestMapping("/toAlloModel")
	public String toAlloModel() {
		return "/ope/core/product/alloModel";
	}
}
