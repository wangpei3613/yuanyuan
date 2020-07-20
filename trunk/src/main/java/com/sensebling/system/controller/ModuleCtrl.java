package com.sensebling.system.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.entity.Module;
import com.sensebling.system.entity.ModuleAuth;
import com.sensebling.system.service.ModuleAuthSvc;
import com.sensebling.system.service.ModuleSvc;
import com.sensebling.system.service.RoleModuleSvc;
/**
 * 菜单管理
 * @author  
 *
 */
@Controller
@RequestMapping("/system/module")
public class ModuleCtrl extends BasicsCtrl {
	@Resource
	private ModuleSvc moduleSvc;
	@Resource
	private RoleModuleSvc roleModuleSvc;
	@Resource
	private ModuleAuthSvc moduleAuthSvc;
	/**
	 * 新增or修改菜单
	 * @param v
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@com.sensebling.system.annotation.ModuleAuth({"menu:openAdd","menu:openEdit"})
	@DisposableToken
	public Result save(Module v) { 
		Result r = new Result();
		if(moduleSvc.checkCode(v.getModuleno(),v.getId())) {
			if(StringUtil.isBlank(v.getId())) {  
				v.setCreateDate(DateUtil.getStringDate());
				v.setCreateUser(getUser().getId());
				moduleSvc.save(v);
			}else {
				v.setUpdateDate(DateUtil.getStringDate());
				v.setUpdateUser(getUser().getId());
				moduleSvc.update(v);
			}
			r.success();
		}else {
			r.setError("菜单编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除菜单
	 * @param v
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	@com.sensebling.system.annotation.ModuleAuth("menu:remove")
	@DisposableToken
	public Result del(String id) { 
		Result r = new Result();
		QueryParameter qp=new QueryParameter();
		qp.addParamter("pid", id);
		if(moduleSvc.findAllCount(qp)>0) {
			r.setError("当前菜单含有子菜单，不能删除");
		}else {
			moduleSvc.del(id);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 查询菜单树列表 同时用于菜单树选择框
	 * @return
	 */
	@RequestMapping("/getTreeGrid")
	@ResponseBody
	public List<Map<String,Object>> getTreeGrid(String moduletype, String roleid) {
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		List<Module> list = null;
		if(StringUtil.notBlank(roleid)) {
			list = moduleSvc.findByRoleid(roleid);
			Map<String, List<ModuleAuth>> map = moduleAuthSvc.getModuleAuths("1", roleid);
			if(list!=null && list.size()>0) {
				for(Module m:list) {
					if(map.containsKey(m.getId())) {
						m.setAuths(map.get(m.getId()));   
					}
				}
			}
		}else {
			QueryParameter qp = new QueryParameter();
			qp.setSortField("orderNumber");
			if("1".equals(moduletype)) {
				qp.addParamter("moduletype", moduletype);
			}
			list = moduleSvc.findAll(qp);
		}

		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(Module m:list) {
				map = JsonUtil.beanToMap(m);
				map.put("iconCls", "1".equals(m.getModuletype())?"menumanagetreegrid-parent":"menumanagetreegrid-leaf");
				map.put("text", m.getModuleName());
				if(StringUtil.notBlank(m.getChecked())) {
					map.put("checked", "1".equals(m.getChecked()));
				}
				temp.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}
	/**
	 * 进入功能菜单管理页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/sys/module/index";
	}
	/**
	 * 进入功能菜单部门编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(String id) {
		Module module = new Module();  
		if(StringUtil.notBlank(id)) {
			module = moduleSvc.get(id);
		}else {
			module.setPid(getRequestParam("pid"));   
		}
		getRequest().setAttribute("v", module);  
		return "/sys/module/edit";
	}
	/**
	 * 进入角色菜单分配页面
	 * @return
	 */
	@RequestMapping("/toAllotModule")
	public String toAllotModule(String id) {
		return "/sys/role/allotModule";
	}
	/**
	 * 查询系统菜单 for dict
	 * @return
	 */
	@RequestMapping(value="/getSysDict")
	@ResponseBody
	public List<Map<String,String>> getSysDict() {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		QueryParameter qp = new QueryParameter();
		qp.addParamter("pid", 1, QueryCondition.if_null);
		qp.setSortField("orderNumber");  
		List<Module> data = moduleSvc.findAll(qp);
		if(data!=null && data.size()>0) {
			for(Module m:data) {
				map=new HashMap<String, String>();
				map.put("text", m.getModuleName());
				map.put("value", m.getId());  
				list.add(map);
			}
		}
		return list;
	}
}
