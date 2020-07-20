package com.sensebling.system.service;


import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.Department;

public interface DeptSvc extends BasicsSvc<Department> {
	/**
	 * 判断部门code是否存在
	 * @param deptCode
	 * @return
	 */
	public boolean checkCode(String deptCode,String id);
	
	/**
	 * 查询用户的部门视野
	 * @param userId
	 * @return
	 */
	public List<Department> getUserDepart(String userId);
	
	
}
