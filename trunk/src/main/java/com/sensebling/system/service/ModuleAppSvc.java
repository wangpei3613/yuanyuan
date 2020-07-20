package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.ModuleApp;

public interface ModuleAppSvc extends BasicsSvc<ModuleApp>{
	/**
	 * 删除App菜单
	 * @param id
	 */
	void del(String id);
	/**
	 * 根据角色查询分配菜单，把已分配的菜单选中
	 * @param roleid
	 * @return
	 */
	List<ModuleApp> findByRoleid(String roleid);
	/**
	 * 查询登陆用户首页菜单
	 * @param appid 
	 * @return
	 */
	List<ModuleApp> getIndexMenu(String appid);
	/**
	 * 查询登陆用户菜单
	 * @param appid
	 * @return
	 */
	List<ModuleApp> getMenu(String appid);
	/**
	 * 保存用户应用配置
	 * @param ids
	 */
	void saveUserIndexModuleApp(String ids, String appid);

}
