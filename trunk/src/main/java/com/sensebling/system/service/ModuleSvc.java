package com.sensebling.system.service;

import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.Module;

/**
 * 系统 模块对象 业务层接口
 * @author 
 * @date 2014-9-23
 */
public interface ModuleSvc extends BasicsSvc<Module>{
	/**
	 * 获取所有module，系统初始化的时候调用
	 * @return
	 */
	List<Module> findAllModule();
	/**
	 * 根据参数查询对应的菜单信息
	 * @param pid 一级菜单的id
	 * @return
	 */
	public List<Module> findAllModuleByParam(String pid,String sysId);
	
	
	/**
	 * 根据参数查询对应的菜单信息
	 * @param pid 一级菜单的id
	 * @return
	 */
	public List<Map<String,Object>> findAllModuleByRole(String userId);
	
	
	/**
	 * 查询所有角色权限的菜单功能
	 * @param roleId
	 * @author zengzhenbin
	 * @return
	 */
	public List<Map<String,Object>> findAllModule(String roleId,String sysId);
	/**
	 * 判断菜单code是否重复
	 * @param moduleno 菜单code
	 * @param id 排除id
	 * @return 返回true为没有重复
	 */
	boolean checkCode(String moduleno, String id);
	/**
	 * 删除菜单 同时删除相关联表数据
	 * @param id
	 */
	void del(String id);
	/**
	 * 根据角色查询分配菜单，把已分配的菜单选中
	 * @param roleid
	 * @return
	 */
	List<Module> findByRoleid(String roleid);
	/**
	 * 获取用户菜单权限
	 * @param userid
	 * @return
	 */
	List<Module> getUserModule(String userid);
	
	
}
