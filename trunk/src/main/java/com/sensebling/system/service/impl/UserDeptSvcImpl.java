package com.sensebling.system.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.UserDepartment;
import com.sensebling.system.service.UserDeptSvc;
/**
 * 视野控制
 * @author Administrator
 *
 */
@Service("userDeptSvc")
public class UserDeptSvcImpl extends BasicsSvcImpl<UserDepartment> implements
		UserDeptSvc {
	/**
	 * 更新用户分配的部门视野信息
	 * 方法会先删除用户原来分配的所有部门,然后重新新增
	 * @param userId 分配的用户ID
	 * @param departments 分配的部门
	 * @param createUserId 操作员id
	 * @return 若departments为空则删除之前的分配信息条数,否则返回删除后新增的分配信息条数
	 */
	public int updateUserDeprt(String userId, String departments,String createUserId) {
		String hql="delete from UserDepartment where userId='"+userId+"'";
		int i=baseDao.executeHQL(hql);//先删除以前的数据
		if(StringUtil.isBlank(departments))
			return i;
		String nowDate=DateUtil.getStringDate();
		int num=0;
		String[] departIds=departments.split(",");
		for (String departmentId:departIds) {
			UserDepartment ud=new UserDepartment();
			ud.setUserId(userId);
			ud.setDepartId(departmentId);
			ud.setCreateDate(nowDate);
			ud.setCreateUser(createUserId);
			this.save(ud);
			num++;
		}
		return num;
	}
}
