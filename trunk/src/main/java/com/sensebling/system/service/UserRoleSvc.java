package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.Role;
import com.sensebling.system.entity.User;
import com.sensebling.system.entity.UserRole;

/**
 * 系统“用户——角色”关系 业务层接口
 * @author ,
 * @date 2014-9-23
 */
public interface UserRoleSvc extends BasicsSvc<UserRole> {
	/**
	 * 更新用户分配的角色信息
	 * @param u 分配的用户
	 * @param roleIds 分配的角色
	 * @param createUserId 操作员id
	 * @return
	 */
	int updateUserRole(User u,String roleIds,String createUserId);
	
	/**
	 * 更新角色分配的用户信息
	 * @param role 分配的角色
	 * @param userIds 分配的用户
	 * @param createUserId 操作员id
	 * @return
	 */
	int updateRoleUser(Role role,String userIds,String createUserId);
	
	
	/**
	 * 删除角色、用户关系
	 * @param role
	 * @param userIds
	 * @return
	 */
	int delRoleUser(String role,String userIds);
	/**
	 * 判断用户是否需要验证信贷系统密码
	 * @param user
	 * @return true代表需要
	 */
	boolean isCheckCreditPass(User user);

}
