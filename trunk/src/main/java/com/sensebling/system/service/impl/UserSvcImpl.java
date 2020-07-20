package com.sensebling.system.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.UserSvc;

@Service("userSvc")
public class UserSvcImpl extends BasicsSvcImpl<User> implements UserSvc {


	public User getUserByName(String userName,String id) {
		StringBuffer sql = new StringBuffer("select * from V_SEN_USERINFO t where t.username=?");
		List<Object> params = new ArrayList<Object>();
		params.add(userName);
		if(StringUtil.notBlank(id)) {
			sql.append(" and t.id != ?");
			params.add(id);
		}
		return this.baseDao.getBySQLEntity(sql.toString(), params);
	}
	public User getUserByName(String userName) {
		String sql = "select * from V_SEN_USERINFO t where t.username=?";
		List<User> list = baseDao.findBySQLEntity(sql, User.class.getName(), userName);
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null; 
	}

	
	
	/**
	 * 分页查询用户
	 * @param qp
	 * @return
	 */
	public Pager queryUser(QueryParameter qp) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.* from v_sen_userinfo t1 where  ");
		sql.append(qp.addDepartViewSql("t1.id", "t1.deptid")+" and ");
		sql.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return this.baseDao.querySQLPageEntity(sql.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), User.class.getName());
	}
	
	@Override
	public User getUserInfo(String userid) {
		String sql = "select a.id,a.nickname,a.deptid,a.username,b.fullname deptName from sen_user a join sen_department b on b.id=a.deptid where a.id=?";
		return baseDao.getBySQLEntity(sql, new Object[] {userid});  
	}



	@Override
	public Pager queryRoleUser(QueryParameter qp) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t1.*, t2.fullname AS deptname, t3.nickname AS createUserName,m.id userroleid FROM sen_user t1 join sen_user_role m on m.userid=t1.id JOIN sen_department t2 ON t1.deptid = t2.id left JOIN sen_user t3 ON t1.createuser = t3.id   where  ");
		sql.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return this.baseDao.querySQLPageEntity(sql.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), User.class.getName());
	}
	
	/**
	 * 查询部门下的用户角色的用户
	 * @param deptcode
	 * @param roleCode
	 * @return
	 */
	public List<User> getRoleUsers(String deptid,String roleCode){
		String sql = "select t3.* from sen_user_role t1 join sen_role t2 on t1.roleid=t2.id join sen_user t3 on t1.userid=t3.id where t3.status='1' and t2.code='"+roleCode+"' and t3.deptid='"+deptid+"'";
	    return this.baseDao.findBySQLEntity(sql);
	}
	
}
