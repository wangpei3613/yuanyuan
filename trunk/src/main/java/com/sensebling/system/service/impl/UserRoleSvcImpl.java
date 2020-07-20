package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Role;
import com.sensebling.system.entity.User;
import com.sensebling.system.entity.UserRole;
import com.sensebling.system.service.RoleSvc;
import com.sensebling.system.service.UserRoleSvc;
import com.sensebling.system.service.UserSvc;

/**
 * “用户——角色”关系 业务层接现类，
 * @author 
 * @date 2011-9-17
 */
@Service("userRoleSvc")
@Transactional
public class UserRoleSvcImpl extends BasicsSvcImpl<UserRole> implements UserRoleSvc
{
	@Resource
	private RoleSvc roleService;
	
	@Resource
	private UserSvc userService;
	/**
	 * 更新用户分配的角色信息
	 * 方法会先删除用户原来分配的所有角色关系,然后重新新增
	 * @param u 分配的用户
	 * @param roleIds 分配的角色
	 * @param createUserId 操作员id
	 * @return 若roleIds为空则删除之前的分配信息条数,否则返回删除后新增的分配信息条数
	 */
	public int updateUserRole(User u, String roleIds,String createUserId) {
		String hql="delete UserRole t where t.user ='"+u.getId()+"'";
		int i=baseDao.executeHQL(hql);//先删除以前的数据
		if(StringUtil.isBlank(roleIds))
			return i;
		/*根据传入的角色ids in查询出角色对象集合,库中不存在的roleid可以被排除掉*/
		QueryParameter qp=new QueryParameter();
		qp.addParamter("id", roleIds,QueryCondition.in);
		List<Role> roles=roleService.findAll(qp);
		String nowDate=DateUtil.getStringDate();
		int num=0;
		for(Role role:roles)//保存分配信息
		{
			UserRole ur=new UserRole();
			ur.setRole(role);
			ur.setUser(u);
			ur.setCreateDate(nowDate);
			ur.setCreateUser(createUserId);
			this.save(ur);
			num++;
		}
		return num;
	}
	
	public int updateRoleUser(Role role,String userIds,String createUserId){
		int num=0;
		try {
			if(StringUtil.notBlank(userIds)){
				/*根据传入的角色ids in查询出角色对象集合,库中不存在的roleid可以被排除掉*/
				QueryParameter qp=new QueryParameter();
				qp.addParamter("id", userIds,QueryCondition.in);
				List<User> users=userService.findAll(qp);
				String nowDate=DateUtil.getStringDate();
				for(User user:users)//保存分配信息
				{
					UserRole ur=new UserRole();
					ur.setRole(role);
					ur.setUser(user);
					ur.setCreateDate(nowDate);
					ur.setCreateUser(createUserId);
					this.save(ur);
					num++;
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 删除角色、用户关系
	 * @param role
	 * @param userIds
	 * @return
	 */
	public int delRoleUser(String role,String userIds){
		int i=0;
		try {
			String hql="delete UserRole t where t.role.id =? and t.user.id in(?)";
			List<Object> list=new ArrayList<Object>();
			list.add(role);
			list.add(userIds);		
			i=baseDao.executeHQL(hql, list);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return i;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isCheckCreditPass(User user) {
		String sql = "select 1 from sen_role a join sen_user_role b on b.roleid=a.id where b.userid=? and a.status='1' and a.checkcreditpassstate='1'";
		List list = baseDao.queryBySql(sql, user.getId());
		if(list!=null && list.size()>0) {
			return true;
		}
		return false;
	}
}
