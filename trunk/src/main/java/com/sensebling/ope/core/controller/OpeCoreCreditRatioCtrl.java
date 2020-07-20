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
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.ope.core.entity.OpeCoreCreditRatio;
import com.sensebling.ope.core.service.OpeCoreCreditRatioSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 信用等级系数
 * @author
 * 
 */
@Controller
@RequestMapping("/ope/core/creditRatio")
public class OpeCoreCreditRatioCtrl extends BasicsCtrl{

	@Resource
	private OpeCoreCreditRatioSvc opeCoreCreditRatioSvc;
	
	
	@RequestMapping(value="/queryCreditRatio")
	@ResponseBody
	public Result queryCreditRatio()
	{
		Result r = new Result();
		r.setData(opeCoreCreditRatioSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	
	@RequestMapping(value="/addCreditRatio",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth({"creditRatio:openAdd","creditRatio:openEdit"})
	@DisposableToken
	public Result addCreditRatio(OpeCoreCreditRatio v)
	{
		Result r = new Result();
		if(StringUtil.isBlank(v.getId())) {
			//新增
			if(opeCoreCreditRatioSvc.checkColumn("code", v.getCode())) {
				opeCoreCreditRatioSvc.save(v);
				r.success();
			}else {
				r.setError("信用等级已存在");
			}
		}else {
			if(opeCoreCreditRatioSvc.checkColumn("code", v.getCode(), v.getId())) {
				OpeCoreCreditRatio vo = opeCoreCreditRatioSvc.get(v.getId());
				vo.setCode(v.getCode());
				vo.setName(v.getName());
				vo.setIndustryCoe(v.getIndustryCoe());
				opeCoreCreditRatioSvc.update(vo);
				r.success();
			}else {
				r.setError("信用等级已存在");
			}
		}
		return crudError(r);
	}
	
	
	
	/**
	 * 删除
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/delCreditRatio")
	@ResponseBody
	@ModuleAuth("creditRatio:remove")
	@DisposableToken
	public Result delCreditRatio(String data)
	{
		Result r = new Result();
		List<OpeCoreCreditRatio> uList = new Gson().fromJson(data, new TypeToken<List<OpeCoreCreditRatio>>(){}.getType());
		for(OpeCoreCreditRatio v:uList) {
			opeCoreCreditRatioSvc.delete(v.getId());
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 进入信用等级系数配置页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/ope/core/creditRatio/index";
	}
	/**
	 * 进入信用等级系数编辑页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		OpeCoreCreditRatio v = new OpeCoreCreditRatio();
		if(StringUtil.notBlank(id)) {
			v = opeCoreCreditRatioSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "/ope/core/creditRatio/edit";
	}
	
	
	
	@RequestMapping(value="/sysData")
	public @ResponseBody List<Map<String,String>> selSysDat(String isSelect){
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<OpeCoreCreditRatio> datas=opeCoreCreditRatioSvc.findAll();
		if(StringUtil.notBlank(isSelect)) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("text", "");
			map.put("value", "");
			temp.add(map);
		}
		if(datas==null)
			return temp;
		for(OpeCoreCreditRatio v:datas){
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", v.getName());
			map.put("value", v.getCode());
			temp.add(map);
			
		}
		return temp;
	}
	
}
