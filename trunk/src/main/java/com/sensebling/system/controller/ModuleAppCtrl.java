package com.sensebling.system.controller;

import java.util.ArrayList;
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
import com.sensebling.system.entity.ModuleApp;
import com.sensebling.system.service.ModuleAppSvc;
@Controller
@RequestMapping("/system/moduleApp")
public class ModuleAppCtrl extends BasicsCtrl{
	@Resource
	private ModuleAppSvc moduleAppSvc;
	/**
	 * 查询APP菜单树列表 同时用于菜单树选择框
	 * @return
	 */
	@RequestMapping("/getTreeGrid")
	@ResponseBody
	public List<Map<String,Object>> getTreeGrid(String level, String roleid) {
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		List<ModuleApp> list = null;
		if(StringUtil.notBlank(roleid)) {
			list = moduleAppSvc.findByRoleid(roleid);
		}else {
			QueryParameter qp = new QueryParameter();
			qp.setSortField("ordernum");
			if("1".equals(level)) {
				qp.addParamter("level", "1,2", QueryCondition.in);
			}
			list = moduleAppSvc.findAll(qp);
		}
		
		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(ModuleApp m:list) {
				map = JsonUtil.beanToMap(m);
				map.put("iconCls", "3".equals(m.getLevel())?"menumanagetreegrid-leaf":"menumanagetreegrid-parent");
				map.put("text", m.getName());
				if(StringUtil.notBlank(m.getChecked())) {
					map.put("checked", "1".equals(m.getChecked()));
				}
				temp.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}
	/**
	 * 新增or修改APP菜单
	 * @param v
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@DisposableToken
	public Result save(ModuleApp v) { 
		Result r = new Result();
		if(!"1".equals(v.getLevel())) {
			if(moduleAppSvc.checkColumn("code", v.getCode(), v.getId())) {
				if(StringUtil.isBlank(v.getId())) {  
					v.setAddtime(DateUtil.getStringDate());
					v.setAdduser(getUser().getId());
					moduleAppSvc.save(v);
				}else {
					v.setUpdatetime(DateUtil.getStringDate());
					v.setUpdateuser(getUser().getId());
					moduleAppSvc.update(v);
				}
				r.success();
			}else {
				r.setError("菜单编码重复");
			}
		}else {
			r.setError("系统菜单不能修改");
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
	@DisposableToken
	public Result del(String id) { 
		Result r = new Result();
		QueryParameter qp=new QueryParameter();
		qp.addParamter("pid", id);
		if(moduleAppSvc.findAllCount(qp)>0) {
			r.setError("当前菜单含有子菜单，不能删除");
		}else {
			ModuleApp app = moduleAppSvc.get(id);
			if(!"1".equals(app.getLevel())) {
				moduleAppSvc.del(id);
				r.success();
			}else {
				r.setError("系统菜单不能删除");
			}
		}
		return crudError(r);
	}
	/**
	 * 获取首页菜单
	 * @return
	 */
	@RequestMapping("/getIndexMenu")  
	@ResponseBody
	public Result getIndexMenu(String _appid) {
		Result r = new Result();
		if(StringUtil.notBlank(_appid)) {
			List<ModuleApp> menus = moduleAppSvc.getIndexMenu(_appid);
			if(menus!=null && menus.size()>0) {
				r.setData(menus);
				r.success();
			}else {
				r.setError("当前未分配任何菜单，请联系管理员");
			}
		}
		return crudError(r);  
	}
	/**
	 * 获取菜单
	 * @return
	 */
	@RequestMapping("/getMenu")  
	@ResponseBody
	public Result getMenu(String _appid) {
		Result r = new Result();
		if(StringUtil.notBlank(_appid)) {
			List<ModuleApp> menus = moduleAppSvc.getMenu(_appid);
			if(menus!=null && menus.size()>0) {
				List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
				Map<String,Object> map = null;
				for(ModuleApp m:menus) {
					map = JsonUtil.beanToMap(m);
					temp.add(map);
				}
				r.setData(EasyTreeUtil.toTreeGrid(temp, "id", "pid"));
				r.success();
			}else {
				r.setError("当前未分配任何菜单，请联系管理员");
			}
		}
		return crudError(r);  
	}
	/**
	 * 保存用户应用配置
	 * @param ids
	 * @return
	 */
	@RequestMapping("/saveUserIndexModuleApp")  
	@ResponseBody
	@DisposableToken
	public Result saveUserIndexModuleApp(String ids, String _appid) {
		Result r = new Result();
		if(StringUtil.notBlank(ids, _appid)) {
			moduleAppSvc.saveUserIndexModuleApp(ids, _appid);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 进入APP功能菜单管理页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/sys/moduleApp/index";
	}
	/**
	 * 进入APP功能菜单部门编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(String id) {
		ModuleApp module = new ModuleApp();  
		if(StringUtil.notBlank(id)) {
			module = moduleAppSvc.get(id);
		}else {
			String pid = getRequestParam("pid");
			if(StringUtil.notBlank(pid)) {
				ModuleApp v = moduleAppSvc.get(pid);
				if(v != null) {
					module.setPid(pid);   
					module.setLevel(Integer.parseInt(v.getLevel())+1+"");
				}
			}
		}
		getRequest().setAttribute("v", module);  
		return "/sys/moduleApp/edit";
	}
	/**
	 * 进入APP角色菜单分配页面
	 * @return
	 */
	@RequestMapping("/toAllotAppModule")
	public String toAllotAppModule(String id) {
		return "/sys/role/allotAppModule";
	}
}
