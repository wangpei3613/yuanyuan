package com.sensebling.ope.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.ope.core.entity.OpeCorePerIncome;
import com.sensebling.ope.core.service.OpeCorePerIncomeSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 各支行当地人均收入
 * @author
 * 
 */
@Controller
@RequestMapping("/ope/core/perIncome")
public class OpeCorePerIncomeCtrl extends BasicsCtrl {

	@Resource
	private OpeCorePerIncomeSvc opeCorePerIncomeSvc;
	


	/**
	 * 人均收入查询grid树和下拉树
	 */
	@RequestMapping("/queryPerIncome")
	@ResponseBody
	public List<Map<String,Object>> queryPerIncome(String multSelect){
	
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		List<OpeCorePerIncome> list =  opeCorePerIncomeSvc.queryPerIncome(getQueryParameter());
		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(OpeCorePerIncome f:list) {
				map = JsonUtil.beanToMap(f);
				map.put("text", f.getOrgName());
				if(StringUtil.notBlank(multSelect) && "true".equals(multSelect)) {
					map.put("checked", false);
				}
				temp.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "orgCode", "orgPartCode");
	}
	
	

	/**
	 * 人均收入信息保存
	 * @return
	 */
	@RequestMapping(value="/savePerIncome")
	@ResponseBody
	@ModuleAuth("perIncome:update")
	@DisposableToken
	public Result savePerIncome(OpeCorePerIncome p)
	{
		Result r = new Result();
		//先删除后插入
		opeCorePerIncomeSvc.doSavePerIncome(p);
		r.success();
		return crudError(r);
	}
	/**
	 * 进入当地人均配置页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/ope/core/perIncome/index";
	}
	/**
	 * 进入当地人均编辑页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		return "/ope/core/perIncome/edit";
	}
	
}
