package com.sensebling.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.entity.ModuleAuth;
/**
 * 菜单权限
 */
import com.sensebling.system.service.ModuleAuthSvc;
@Controller
@RequestMapping("/system/module/auth")
public class ModuleAuthCtrl extends BasicsCtrl{
	@Resource
	private ModuleAuthSvc moduleAuthSvc;
	/**
	 * 进入菜单权限配置页面
	 * @return
	 */
	@RequestMapping("/index")
	@com.sensebling.system.annotation.ModuleAuth("menu:auth")
	public String index() {
		return "/sys/moduleAuth/index";
	}
	/**
	 * 列表查询
	 * @return
	 */
	@RequestMapping("/getPager")
	@ResponseBody
	@com.sensebling.system.annotation.ModuleAuth("menu:auth")
	public Result getPager() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		if(StringUtil.notBlank(qp.getParam("moduleid"))) {
			r.setData(moduleAuthSvc.findPage(qp));   
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 进入菜单权限编辑页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	@com.sensebling.system.annotation.ModuleAuth("menu:auth")
	public String toEditIndex(String id,String moduleid) {
		ModuleAuth v = new ModuleAuth();
		if(StringUtil.notBlank(id)) {
			v = moduleAuthSvc.get(id);
		}else {
			v.setModuleid(getRequestParam("moduleid"));
		}
		getRequest().setAttribute("id", id);  
		getRequest().setAttribute("moduleid", moduleid);  
		getRequest().setAttribute("v", v);  
		return "/sys/moduleAuth/edit";
	}
	
	/**
	 * 菜单权限保存
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@com.sensebling.system.annotation.ModuleAuth("menu:auth")
	@DisposableToken
	public Result save(ModuleAuth v) { 
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moduleid", v.getModuleid());
		map.put("code", v.getCode());
		if(moduleAuthSvc.checkColumns(map, v.getId())) {
			map.remove("code");
			map.put("sort", v.getSort());
			if(moduleAuthSvc.checkColumns(map, v.getId())) {
				if(StringUtil.isBlank(v.getId())) {  
					moduleAuthSvc.save(v);
				}else {
					moduleAuthSvc.update(v);
				}
				r.success();
			}else {
				r.setError("排列顺序重复");
			}
		}else {
			r.setError("编码重复");
		}
		return crudError(r);
	}
	/**
	 * 菜单权限删除
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@com.sensebling.system.annotation.ModuleAuth("menu:auth")
	@DisposableToken
	public Result delete(String id) {
		Result r = new Result();
		moduleAuthSvc.delModuleAuth(id);
		r.success();
		return crudError(r);
	}
	/**
	 * 进入选择图标页面
	 * @return
	 */
	@RequestMapping("/toViewIcons")
	public String toViewIcons() {
		return "/sys/moduleAuth/icons";
	}
}
