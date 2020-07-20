package com.sensebling.system.controller;

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
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.entity.Role;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.RoleModuleSvc;
import com.sensebling.system.service.RoleSvc;
import com.sensebling.system.service.UserRoleSvc;
/**
 * 角色管理
 * @author  
 *
 */
@Controller
@RequestMapping("/system/role")
public class RoleCtrl extends BasicsCtrl {
	@Resource
	private RoleSvc roleSvc;
	@Resource
	private RoleModuleSvc roleModuleSvc;
	@Resource
	private UserRoleSvc userRoleSvc;
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody
	public Result showList()
	{
		Result r = new Result();
		r.setData(roleSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping(value="/combobox")
	@ResponseBody
	public List<Map<String,String>> getCombobox()
	{
		
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<Role> list= roleSvc.findAll();
		for(Role vo:list)
		{
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", vo.getRoleName());
			map.put("value", vo.getId());
			temp.add(map);
		}
		return temp;
	}
	/**
	 * 新增角色
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("roleManage:openAdd")
	@DisposableToken
	public Result addRole(Role role){
		Result r = new Result();
		if(roleSvc.checkCode(role.getCode(),null)) {
			role.setCreateDate(DateUtil.getStringDate());
			role.setCreateUser(getUser().getId());
			roleSvc.save(role);
			r.success();
		}else {
			r.setError("角色编码已存在");
		}
		return crudError(r);
	}
	/**
	 * 修改角色
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("roleManage:openEdit")
	@DisposableToken
	public Result upRole(Role role){
		Result r = new Result();
		if(roleSvc.checkCode(role.getCode(),role.getId())) {
			role.setUpdateDate(DateUtil.getStringDate());
			role.setUpdateUser(getUser().getId());
			roleSvc.update(role);
			r.success();
		}else {
			r.setError("角色编码已存在");
		}
		return crudError(r);
	}
	/**
	 * 删除角色
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("roleManage:remove")
	@DisposableToken
	public Result delRole(String data){
		Result r = new Result();
		List<Role> list = new Gson().fromJson(data, new TypeToken<List<Role>>(){}.getType());
		for(Role role:list) {
			roleSvc.del(role.getId());
		}
		r.success(); 
		return crudError(r);
	}
	/**
	 * 分配角色菜单权限
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 * @return
	 */
	@RequestMapping(value="/addRoleModules",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addRoleModules(String ids, String roleid){
		Result r = new Result();
		Role role = roleSvc.get(roleid);
		if(role != null) {
			roleSvc.addRoleModules(ids,roleid,getUser());
			r.success();
		}else {
			r.setError("角色不存在");
		}
		return crudError(r);
	}
	/**
	 * 分配角色菜单权限(包括按钮权限)
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 * @return
	 */
	@RequestMapping(value="/addRoleModulesAndAuths",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("roleManage:alloMenu")
	@DisposableToken
	public Result addRoleModulesAndAuths(String ids, String roleid, String auths){
		Result r = new Result();
		Role role = roleSvc.get(roleid);
		if(role != null) {
			roleSvc.addRoleModules(ids,roleid,auths,getUser());
			r.success();
		}else {
			r.setError("角色不存在");
		}
		return crudError(r);
	}
	/**
	 * 分配角色APP菜单权限
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 * @return
	 */
	@RequestMapping(value="/addRoleAppModules",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("roleManage:alloAppMenu")
	@DisposableToken
	public Result addRoleAppModules(String ids, String roleid){
		Result r = new Result();
		Role role = roleSvc.get(roleid);
		if(role != null) {
			roleSvc.addRoleAppModules(ids,roleid,getUser());
			r.success();
		}else {
			r.setError("角色不存在");
		}
		return crudError(r);
	}
	
	
	/**
	 * 获取所有角色
	 * @return
	 */
	@RequestMapping(value="/getUserRole")
	@ResponseBody
	public Result getUserRole(String userId)
	{
		Result r = new Result();
		r.setData(roleSvc.getUserRole(userId));
		r.success();
		return crudError(r);
	}
	
	/**
	 * 分配用户角色
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 * @return
	 */
	@RequestMapping(value="/addUserRole")
	@ResponseBody
	public Result addUserRole(String userId, String roleIds){
		Result r = new Result();
		User u = new User();
		u.setId(userId);
		userRoleSvc.updateUserRole(u, roleIds, getUser().getId());
		r.success();
		return crudError(r);
	}
	/**
	 * 进入角色分配页面
	 * @return
	 */
	@RequestMapping("/toAllotRole")
	public String toAllotRole() {
		return "/sys/userManage/allotRole";
	}
	/**
	 * 进入角色配置主页面
	 * @return
	 */
	@RequestMapping("/index")
	@ModuleAuth("roleManage")
	public String index() {
		return "/sys/role/index";
	}
	/**
	 * 进入角色配置列表页面
	 * @return
	 */
	@RequestMapping("/roleList")
	public String roleList() {
		return "/sys/role/roleList";
	}
	/**
	 * 进入角色用户列表主页面
	 * @return
	 */
	@RequestMapping("/roleUserList")
	public String roleUserList() {
		return "/sys/role/roleUserList";
	}
	/**
	 * 进入角色编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(String id) {
		Role u = new Role();  
		if(StringUtil.notBlank(id)) {
			u = roleSvc.get(id);
		}
		getRequest().setAttribute("v", u);  
		return "/sys/role/edit";
	}
}
