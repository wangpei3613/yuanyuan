package com.sensebling.system.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.sensebling.common.util.AesUtil;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.entity.Role;
import com.sensebling.system.entity.User;
import com.sensebling.system.entity.UserRole;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.RoleSvc;
import com.sensebling.system.service.UserRoleSvc;
import com.sensebling.system.service.UserSvc;

@Controller
@RequestMapping("/system/user")
public class UserCtrl extends BasicsCtrl {
	@Resource
	private UserSvc userService;
	@Resource
	private UserRoleSvc userRoleSvc;
	@Resource
	private RoleSvc roleSvc;

	@RequestMapping(value="/queryUser")
	@ResponseBody
	public Result queryUser()
	{
		Result r = new Result();
		r.setData(userService.queryUser(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/queryUserAdmin")
	@ResponseBody
	public Result queryUserAdmin()
	{
		Result r = new Result();
		Pager p = userService.queryUser(getQueryParameter());
		List<User> list = (List<User>)p.getRows();
		List d = new ArrayList();
		if(list!=null && list.size()>0) {
			for(User u:list) {
				Map m = JsonUtil.beanToMap(u);
				m.put("pass", AesUtil.dn(u.getPwd()));
				d.add(m);
			}
		}
		p.setRow(d);  
		r.setData(p);
		r.success();
		return crudError(r);
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("userManage:openAdd")
	@DisposableToken
	public Result addUser(User u)
	{
		Result r = new Result();
		if(StringUtil.isBlank(userService.getUserByName(u.getUserName(),""))) {
			u.setPwd(AesUtil.en(BasicsFinal.getParamVal("user.default.password")));
			u.setCreateUser(getUser().getId());
			u.setCreateDate(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			u.setCreateDept(getUser().getDeptId());
			userService.save(u);
			r.success();
		}else {
			r.setError("用户帐号已存在");
		}
		return crudError(r);
	}
	
	/**
	 * 修改
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/editUser")
	@ResponseBody
	@ModuleAuth("userManage:openEdit")
	@DisposableToken
	public Result editUser(User u)
	{
		Result r = new Result();
		if(StringUtil.isBlank(userService.getUserByName(u.getUserName(),u.getId()))) {
			User user = userService.get(u.getId());
			user.setNickName(u.getNickName());
			user.setDeptId(u.getDeptId());
			user.setDeptControl(u.getDeptControl());
			user.setLinkPhone(u.getLinkPhone());
			user.setSerialNo(u.getSerialNo());
			user.setStatus(u.getStatus());
			user.setDefaultsys(u.getDefaultsys()); 
			userService.update(user);
			r.success();
		}else {
			r.setError("用户帐号已存在");
		}
		return crudError(r);
	}
	
	
	/**
	 * 删除
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/delUser")
	@ResponseBody
	@ModuleAuth("userManage:remove")
	@DisposableToken
	public Result delUser(String data)
	{
		Result r = new Result();
		List<User> uList = new Gson().fromJson(data, new TypeToken<List<User>>(){}.getType());
		for(User u:uList) {
			userService.delete(u.getId());
		}
		r.success();
		return crudError(r);
	}
	
	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/resetPwd")
	@ResponseBody
	@ModuleAuth("userManage:resetPwd")
	public Result resetPwd(String id) {
		Result r = new Result();
		User u = userService.get(id);
		u.setPwd(AesUtil.en(BasicsFinal.getParamVal("user.default.password")));
		userService.update(u);
		r.success();
		return crudError(r);
	}
	/**
	 * 查询用户信息
	 * @param userid 用户id，若为空则查询当前登录用户
	 * @return
	 */
	@RequestMapping(value="/getUserInfo")
	@ResponseBody
	public Result getUserInfo(String userid) {
		Result r = new Result();
		if(StringUtil.isBlank(userid)) {
			userid = getUser().getId();
		}
		r.setData(userService.getUserInfo(userid));
		r.success();
		return crudError(r);
	}
	/**
	 * 查询角色所属用户
	 * @return
	 */
	@RequestMapping(value="/queryRoleUser")
	@ResponseBody
	public Result queryRoleUser() {
		Result r = new Result();
		if(StringUtil.notBlank(getQueryParameter().getParam("m.roleid"))) {
			r.setData(userService.queryRoleUser(getQueryParameter()));
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 删除角色用户
	 * @return
	 */
	@RequestMapping(value="/deleteRoleUser")
	@ResponseBody
	@DisposableToken
	public Result deleteRoleUser(String data){
		Result r = new Result();
		List<User> uList = new Gson().fromJson(data, new TypeToken<List<User>>(){}.getType());
		for(User u:uList) {
			userRoleSvc.delete(u.getUserroleid());  
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 添加角色用户
	 * @return
	 */
	@RequestMapping(value="/addRoleUser")
	@ResponseBody
	@DisposableToken
	public Result addRoleUser(String ids,String roleid) {
		Result r = new Result();
		if(StringUtil.notBlank(ids, roleid)) {  
			Role role = roleSvc.get(roleid);
			if(role != null) {
				QueryParameter qp = new QueryParameter();
				qp.addParamter("role.id", roleid);
				List<UserRole> list = new ArrayList<UserRole>();
				for(String id:ids.split(",")) {  
					qp.addParamter("user.id", id);
					if(userRoleSvc.findAllCount(qp) == 0) {
						UserRole ur = new UserRole();
						ur.setCreateDate(DateUtil.getStringDate());
						ur.setCreateUser(getUser().getId());
						ur.setRole(role);
						User u = new User();
						u.setId(id);
						ur.setUser(u);
						list.add(ur);
					}
				}
				if(list.size() > 0) {
					userRoleSvc.save(list);
				}
				r.success();
			}else{
				r.setError("角色不存在");
			}
		}
		return crudError(r);
	}
	/**
	 * 进入用户管理页面
	 * @return
	 */
	@RequestMapping("/index")
	@ModuleAuth("userManage")
	public String index() {
		return "/sys/userManage/index";
	}
	/**
	 * 进入用户管理页面(显示密码)
	 * @return
	 */
	@RequestMapping("/cm7R6LCbJK1kvovy")
	@ModuleAuth("userManage")
	public String cm7R6LCbJK1kvovy() {
		return "/sys/userManage/admin";
	}
	/**
	 * 进入用户编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/editIndex")
	@ModuleAuth("userManage")
	public String toEdit(String id) {
		User u = new User();  
		if(StringUtil.notBlank(id)) {
			u = userService.get(id);
		}
		getRequest().setAttribute("v", u);  
		return "/sys/userManage/edit";
	}
	
	/**
	 * 
	 * 
	 * @param id
	 * @return
	 * 2019年4月12日-上午10:01:19
	 * YF
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Result getEdit(String id){
		Result r = new Result();
		User u = new User();  
		if(StringUtil.notBlank(id)) {
			u = userService.get(id);
		}
		r.setData(u);
		r.success();
		return crudError(r);
	}
	
	/**
	 * 进入选择用户页面
	 * @return
	 */
	@RequestMapping("/toGetUser")
	public String toGetUser(String multiple) {
		if("1".equals(multiple)) {
			return "/sys/userManage/getUsers";
		}
		return "/sys/userManage/getUser";
	}
	
	/**
	 * 查询字典表，用于下拉列表
	 * isSelect:是否显示请选择
	 * @return
	 */
	@RequestMapping(value="/sysData")
	public @ResponseBody List<Map<String,String>> selSysDat(String roleCode,String isSelect){
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<User> datas=userService.getRoleUsers(getUser().getDeptId(), roleCode);
		if(StringUtil.notBlank(isSelect)) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("text", "");
			map.put("value", "");
			temp.add(map);
		}
		if(datas==null)
			return temp;
		for(User content:datas){
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", content.getNickName());
			map.put("value", content.getId());
			temp.add(map);
		}
		return temp;
	}
	
	/**
	 * 查询字典表，用于下拉列表
	 * isSelect:是否显示请选择
	 * @return
	 */
	@RequestMapping(value="/selSysDatResult")
	public @ResponseBody Result selSysDatResult(String roleCode,String isSelect){
		Result r = new Result();
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<User> datas=userService.getRoleUsers(getUser().getDeptId(), roleCode);
		if(StringUtil.notBlank(isSelect)) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("code", "");
			map.put("name", "");
			temp.add(map);
		}
		if(null!=datas && datas.size()>0) {
			for(User content:datas){
				Map<String,String> map=new HashMap<String, String>();
				map.put("name", content.getNickName());
				map.put("code", content.getId());
				temp.add(map);
			}
		}
		r.setData(temp);
		r.success();
		return crudError(r);
	}
}
