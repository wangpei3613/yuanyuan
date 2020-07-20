package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Department;
import com.sensebling.system.service.DeptSvc;
@Service("deptSvc")
public class DeptSvcImpl extends BasicsSvcImpl<Department> implements DeptSvc{
	/**
	 * 判断部门code是否存在
	 * @param deptCode
	 * @return
	 */
	public boolean checkCode(String deptCode,String id) {
		StringBuffer sql = new StringBuffer("select 1 from sen_department t where t.deptCode=?");
		List<Object> param = new ArrayList<Object>();
		param.add(deptCode);
		if(StringUtil.notBlank(id)) {
			sql.append(" and t.id != ?");
			param.add(id);
		}
		List<Department> list = this.baseDao.findBySQL(sql.toString(), param);
		if(null == list || list.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 查询用户的部门视野
	 * @param userId
	 * @return
	 */
	public List<Department> getUserDepart(String userId){
		StringBuffer sql = new StringBuffer("select t1.*,decode(t2.id,null,'0','1') checked from sen_department t1 left join sen_user_department t2 on t1.id=t2.departid and t2.userid=? order by t1.orderIndex");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		return this.baseDao.findBySQLEntity(sql.toString(), params,Department.class.getName());
	}
	
	
}
