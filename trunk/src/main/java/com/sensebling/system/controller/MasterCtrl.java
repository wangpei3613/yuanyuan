package com.sensebling.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.entity.Module;
import com.sensebling.system.entity.UserRole;
import com.sensebling.system.service.LoginLogSvc;
import com.sensebling.system.service.ModuleSvc;
import com.sensebling.system.service.UserRoleSvc;


@Controller
@RequestMapping("/master")
public class MasterCtrl extends BasicsCtrl{
	@Resource
	private UserRoleSvc userRoleSvc;
	@Resource
	private ModuleSvc moduleSvc;
	@Resource
	private LoginLogSvc loginLogSvc;
	/**
	 * 进入系统主页面
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/index")
	public String fwIndexView(ModelMap map,HttpServletRequest request)
	{
		String path=request.getContextPath();
		map.put("path", path);
		String userRoles = "";
		QueryParameter qp = new QueryParameter();
		qp.addParamter("user.id", getUser().getId());
		List<UserRole> list = userRoleSvc.findAll(qp);
		if(list!=null && list.size()>0) {
			for(UserRole ur:list) {
				userRoles += ","+ur.getRole().getRoleName();
			}
			userRoles = userRoles.substring(1);
		}
		map.put("userRoles", userRoles);
		return "/index/index";
	}
	/**
	 * 获取用户系统菜单权限
	 * @return
	 */
	@RequestMapping(value="/menu")
	@ResponseBody
	public List<Map<String,Object>> getMenu(){
		List<Map<String,Object>> maplist = new ArrayList<Map<String,Object>>(); 
		List<Module> list = moduleSvc.getUserModule(getUser().getId());
		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(Module m:list) {
				map = JsonUtil.beanToMap(m);
				map.put("text", m.getModuleName());
				map.put("moduleid", m.getId());
				map.put("id", m.getModuleno());
				maplist.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(maplist, "moduleid", "pid");
	}
	
	@RequestMapping(value="/print")
	public String print(){
		return "/master/print";
	}
	/**
	 * 进入修改密码页面
	 * @return
	 */
	@RequestMapping(value="/toChangePass")
	public String toChangePass() {
		return "/index/changePass";
	}
	@RequestMapping("/disposableTokenError")
	@ResponseBody
	@AuthIgnore
	public Result disposableTokenError() {
		return crudError(new Result().error("页面已过期，请刷新页面后重试"));
	}
}
