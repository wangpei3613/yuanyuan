package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.User;


public interface UserSvc extends BasicsSvc<User> {

	/**
	 * 查询用户的帐号是否唯一
	 * @param username
	 * @return
	 */
	public User getUserByName(String username,String id);
	/**
	 * 根据用户名查询用户
	 * @param username 用户名
	 * @return
	 */
	public User getUserByName(String username);
	
	
	/**
	 * 分页查询用户
	 * @param qp
	 * @return
	 */
	public Pager queryUser(QueryParameter qp);
	/**
	 * 查询用户信息
	 * @param userid 用户id，若为空则查询当前登录用户
	 * @return
	 */
	public User getUserInfo(String userid);

	/**
	 * 查询角色所属用户
	 * @param qp
	 * @return
	 */
	public Pager queryRoleUser(QueryParameter qp);
	
	
	/**
	 * 查询部门下的用户角色的用户
	 * @param deptcode
	 * @param roleCode
	 * @return
	 */
	List<User> getRoleUsers(String deptid,String roleCode);
}
