package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.UserDepartment;

public interface UserDeptSvc extends BasicsSvc<UserDepartment> {
	/**
	 * 更新用户分配的部门视野信息
	 * 方法会先删除用户原来分配的所有部门,然后重新新增
	 * @param userId 分配的用户ID
	 * @param departments 分配的部门
	 * @param createUserId 操作员id
	 * @return 若departments为空则删除之前的分配信息条数,否则返回删除后新增的分配信息条数
	 */
	public int updateUserDeprt(String userId, String departments,String createUserId);
}
