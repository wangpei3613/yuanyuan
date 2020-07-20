package com.sensebling.system.service;

import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.ModuleAuth;
import com.sensebling.system.vo.UserModuleAuth;

public interface ModuleAuthSvc extends BasicsSvc<ModuleAuth>{
	/**
	 * 删除菜单权限
	 * @param id
	 */
	void delModuleAuth(String id);
	/**
	 * 查询菜单权限集合
	 * @param type 菜单类别1、pc，2、App
	 * @param roleid 角色id
	 * @return
	 */
	Map<String, List<ModuleAuth>> getModuleAuths(String type, String roleid);
	/**
	 * 查询菜单权限集合
	 * @param type 菜单类别1、pc，2、App
	 * @param roleid 角色id
	 * @return
	 */
	List<ModuleAuth> getModuleAuthsList(String type, String roleid);
	/**
	 * 获取用户权限信息
	 * @param id 用户id
	 * @return
	 */
	UserModuleAuth getUserModuleAuth(String id);
}
