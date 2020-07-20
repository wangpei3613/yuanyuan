package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Module;
import com.sensebling.system.service.ModuleSvc;

/**
 * 模块信息业务层实现类，
 * @author 
 * @date 2012-9-17
 */
@Service("moduleSvc")
@Transactional
public class ModuleSvcImpl extends BasicsSvcImpl<Module> implements ModuleSvc {
	
	public List<Module> findAllModule() {
		return this.baseDao.findAllOpen();
	}
	/**
	 * 根据参数查询对应的菜单信息
	 * @param pid 一级菜单的id
	 * @return
	 */
	public List<Module> findAllModuleByParam(String pid,String sysId){
		StringBuffer hql=new StringBuffer("from Module where 1=1");
		List<Object> list=new ArrayList<Object>();
		if(!StringUtil.isBlank(pid)){
			hql.append(" and pid=?");
			list.add(pid);
		}
//		if(!StringUtil.isBlank(sysId)){
//			hql.append(" and sysId=? and (pid is null or pid='' or pid='null')");
//			list.add(sysId);
//		}
		hql.append(" order by ordernumber");
		return this.baseDao.find(hql.toString(), list);
	}
	
	/**
	 * 根据角色查询功能菜单
	 */
	public List<Map<String,Object>> findAllModuleByRole(String userId) {
		StringBuffer str=new StringBuffer();		
		str.append("select m.*,r.crud from sen_module m");
		str.append(" inner join sen_role_module r on r.moduleid=m.id");
		str.append(" inner join sen_user_role u on r.roleid=u.roleid");
		str.append(" where u.userid=? and u.roleid='1'");
		return baseDao.queryBySql_listMap(str.toString(),new Object[]{userId});
	}
	
	/**
	 * 查询所有角色权限的菜单功能
	 * @param roleId
	 * @author zengzhenbin
	 * @return
	 */
	public List<Map<String,Object>> findAllModule(String roleId,String sysId){
		List<Map<String,Object>> result=null;
		StringBuffer str=new StringBuffer();			
		str.append(" SELECT * FROM (");
		str.append(" SELECT M.*,R.ROLEID,R.CRUD FROM sen_MODULE M INNER JOIN sen_ROLE_MODULE R ON R.MODULEID=M.ID WHERE R.ROLEID='"+roleId+"'");
		str.append(" UNION ALL ");
		str.append(" SELECT M.*,'' ROLEID,0 CRUD FROM sen_MODULE M WHERE M.ID NOT IN(");
		str.append("SELECT M.ID FROM sen_MODULE M INNER JOIN sen_ROLE_MODULE R ON R.MODULEID=M.ID WHERE R.ROLEID='"+roleId+"'");
		str.append(") ");
//		str.append(") t WHERE SYSID='"+sysId+"' ORDER BY PID,ORDERNUMBER");
		str.append(") t  ORDER BY PID,ORDERNUMBER");
		result=baseDao.queryBySql_listMap(str.toString());
		return result;
	}
	@Override
	public boolean checkCode(String moduleno, String id) {
		String sql = "select 1 from sen_module a where a.moduleno=? ";
		List<Object> param = new ArrayList<Object>();
		param.add(moduleno);
		if(StringUtil.notBlank(id)) {
			sql += " and a.id!=?";
			param.add(id);
		}
		List<Module> list = baseDao.findBySQLEntity(sql, param, Module.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}
	@Override
	public void del(String id) {
		String sql = "delete from sen_module a where a.id=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module_auth t where t.rolemoduleid in (select a.id from sen_role_module a where a.moduleid=?)";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module a where a.moduleid=?";
		baseDao.executeSQL(sql, id);
	}
	@Override
	public List<Module> findByRoleid(String roleid) {
		String sql = "select a.*,decode(b.id,null,'0','1') checked from sen_module a left join sen_role_module b on b.roleid=? and b.moduleid=a.id order by a.ordernumber";
		return baseDao.findBySQLEntity(sql, new Object[] {roleid}, Module.class.getName());  
	}
	@Override
	public List<Module> getUserModule(String userid) {
		String sql = "WITH modules( id, moduleno, modulename, moduletype, pid, controller, viewport, isuse, ordernumber, url,icons, weburl,homepage,defaultsys,syscode) AS( SELECT a.id, a.moduleno, a.modulename, a.moduletype, a.pid, a.controller, a.viewport, a.isuse, a.ordernumber, a.url, a.icons, a.weburl, a.homepage,a.defaultsys,a.syscode FROM ( SELECT a3.id, a3.moduleno, a3.modulename, a3.moduletype, a3.pid, a3.controller, a3.viewport, a3.isuse, a3.ordernumber, a3.url, a3.icons, a3.weburl, a3.homepage,a4.defaultsys,a3.syscode FROM sen_role_module a1 JOIN sen_user_role a2 ON a1.roleid=a2.roleid JOIN sen_module a3 ON a3.id=a1.moduleid join sen_user a4 on a4.id=a2.userid WHERE a2.userid=?) a UNION ALL SELECT b.id, b.moduleno, b.modulename, b.moduletype, b.pid, b.controller, b.viewport, b.isuse, b.ordernumber, b.url, b.icons, b.weburl, b.homepage,c.defaultsys,b.syscode FROM sen_module b, modules c WHERE c.pid=b.id) SELECT DISTINCT * FROM modules WHERE isuse='1' ORDER BY ordernumber";
		return baseDao.findBySQLEntity(sql, new Object[] {userid}, Module.class.getName());  
	}
	
	
}
