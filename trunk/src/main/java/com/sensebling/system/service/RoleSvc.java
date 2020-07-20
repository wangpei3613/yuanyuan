package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.Role;
import com.sensebling.system.entity.User;

/**
 * 系统角色对象 业务层接口
 * @author 
 * @date 2014-9-23
 */
public interface RoleSvc extends BasicsSvc<Role> {
	/**
	 * 判断角色编码是否存在
	 * @param code 角色编码
	 * @param id 排除的id
	 * @return true为不存在
	 */
	public boolean checkCode(String code, String id);
	/**
	 * 删除角色  同时删除相关联表数据
	 * @param id
	 */
	public void del(String id);
	/**
	 * 分配角色菜单权限
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 */
	public void addRoleModules(String ids, String roleid, User u);
	
	/**
	 * 获取所有角色，并标识改用户是否属于该角色
	 * @param userId
	 * @return
	 */
	public List<Role> getUserRole(String userId);
	/**
	 * 获取用户的角色
	 * @param userId
	 * @return
	 */
	public List<Role> getUserRoles(String userId);
	/**
	 * 分配角色APP菜单权限
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 */
	public void addRoleAppModules(String ids, String roleid, User user);
	/**
	 * 分配角色菜单权限
	 * @param ids 菜单ids
	 * @param roleid 角色id
	 * @param auths 菜单权限ids
	 */
	public void addRoleModules(String ids, String roleid, String auths, User user);
	/**
	 * 判断用户是否含有该角色
	 * @param userid 用户id
	 * @param roleno 角色编码
	 * @return
	 */
	public boolean isUserRole(String userid, String roleno);
}
