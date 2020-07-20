package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Module;
import com.sensebling.system.entity.ModuleApp;
import com.sensebling.system.entity.ModuleAuth;
import com.sensebling.system.entity.Role;
import com.sensebling.system.entity.RoleModule;
import com.sensebling.system.entity.RoleModuleApp;
import com.sensebling.system.entity.RoleModuleAuth;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.ModuleAppSvc;
import com.sensebling.system.service.ModuleSvc;
import com.sensebling.system.service.RoleModuleAppSvc;
import com.sensebling.system.service.RoleModuleAuthSvc;
import com.sensebling.system.service.RoleModuleSvc;
import com.sensebling.system.service.RoleSvc;


/**
 * 角色
 * @author 
 * @date 2011-9-17
 */
@Service("roleSvc")
@Transactional
public class RoleSvcImpl extends BasicsSvcImpl<Role> implements RoleSvc{
	@Resource
	private RoleModuleSvc roleModuleSvc;
	@Resource
	private ModuleSvc moduleSvc;
	@Resource
	private ModuleAppSvc moduleAppSvc;
	@Resource
	private RoleModuleAppSvc roleModuleAppSvc;
	@Resource
	private RoleModuleAuthSvc roleModuleAuthSvc;

	@Override
	public boolean checkCode(String code, String id) {
		String sql = "select 1 from sen_role a where a.code=?";
		List<Object> param = new ArrayList<Object>();
		param.add(code);
		if(StringUtil.notBlank(id)) {
			sql += "and a.id!=?";
			param.add(id);
		}
		List<Role> list = baseDao.findBySQLEntity(sql, param, Role.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public void del(String id) {
		String sql = "delete from sen_role a where a.id=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_user_role a where a.roleid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module_auth t where t.rolemoduleid in (select a.id from sen_role_module a where a.roleid=?)";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module a where a.roleid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module_app a where a.roleid=?";
		baseDao.executeSQL(sql, id);
	}

	@Override
	public void addRoleModules(String ids, String roleid, User u) {
		String sql = "delete from sen_role_module a where a.roleid=?";
		baseDao.executeSQL(sql, roleid);
		if(StringUtil.notBlank(ids)) {
			List<RoleModule> list = new ArrayList<RoleModule>();
			for(String moduleid:ids.split(",")) {
				Module module = moduleSvc.get(moduleid);
				if(module != null && "2".equals(module.getModuletype())) {
					RoleModule roleModule = new RoleModule();
					roleModule.setCreateDate(DateUtil.getStringDate());
					roleModule.setCreateUser(u.getId());
					roleModule.setModuleid(moduleid);
					roleModule.setRoleid(roleid);
					list.add(roleModule);
				}
			}
			roleModuleSvc.save(list);
		}
	}
	@Override
	public void addRoleModules(String ids, String roleid, String auths, User u) {
		String sql = "delete from sen_role_module_auth a where a.rolemoduleid in (select b.id from sen_role_module b where b.roleid=?)";
		baseDao.executeSQL(sql, roleid);
		sql = "delete from sen_role_module a where a.roleid=?";
		baseDao.executeSQL(sql, roleid);
		if(StringUtil.notBlank(ids)) {
			List<RoleModule> list = new ArrayList<RoleModule>();
			Map<String, Module> map = new HashMap<String, Module>();
			for(String moduleid:ids.split(",")) {
				Module module = moduleSvc.get(moduleid);
				if(module != null && "2".equals(module.getModuletype())) {
					map.put(moduleid, module);
					RoleModule roleModule = new RoleModule();
					roleModule.setCreateDate(DateUtil.getStringDate());
					roleModule.setCreateUser(u.getId());
					roleModule.setModuleid(moduleid);
					roleModule.setRoleid(roleid);
					list.add(roleModule);
				}
			}
			roleModuleSvc.save(list);
			if(StringUtil.notBlank(auths)) {//添加菜单按钮等权限
				List<RoleModuleAuth> listAuth = new ArrayList<RoleModuleAuth>();
				for(RoleModule roleModule:list) {
					List<ModuleAuth> listMA = map.get(roleModule.getModuleid()).getAuths();
					if(listMA!=null && listMA.size()>0) {
						for(ModuleAuth moduleAuth:listMA) {
							if(auths.indexOf(moduleAuth.getId()) > -1) {
								RoleModuleAuth roleModuleAuth = new RoleModuleAuth();
								roleModuleAuth.setAuthid(moduleAuth.getId());
								roleModuleAuth.setRolemoduleid(roleModule.getId());
								listAuth.add(roleModuleAuth);
							}
						}
					}
				}
				roleModuleAuthSvc.save(listAuth);  
			}
		}
	}
	@Override
	public List<Role> getUserRole(String userId){
		StringBuffer sql = new StringBuffer("select t1.*,decode(t2.id,null,'0','1') checked from sen_role t1 left join sen_user_role t2 on t1.id=t2.roleid and t2.userid=?");
		List<Object> param = new ArrayList<Object>();
		param.add(userId);
		return this.baseDao.findBySQLEntity(sql.toString(), param, Role.class.getName());
	}
	@Override
	public List<Role> getUserRoles(String userId){
		StringBuffer sql = new StringBuffer("select t1.* from sen_role t1 join sen_user_role t2 on t1.id=t2.roleid and t2.userid=?");
		List<Object> param = new ArrayList<Object>();
		param.add(userId);
		return this.baseDao.findBySQLEntity(sql.toString(), param, Role.class.getName());
	}
	@Override
	public void addRoleAppModules(String ids, String roleid, User u) {
		String sql = "delete from sen_role_module_app a where a.roleid=?";
		baseDao.executeSQL(sql, roleid);
		if(StringUtil.notBlank(ids)) {
			List<RoleModuleApp> list = new ArrayList<RoleModuleApp>();
			for(String moduleid:ids.split(",")) {
				ModuleApp module = moduleAppSvc.get(moduleid);
				if(module != null && "3".equals(module.getLevel())) {
					RoleModuleApp roleModule = new RoleModuleApp();
					roleModule.setAddtime(DateUtil.getStringDate());
					roleModule.setAdduser(u.getId());
					roleModule.setModuleid(moduleid);
					roleModule.setRoleid(roleid);
					list.add(roleModule);
				}
			}
			roleModuleAppSvc.save(list);
		}
	}

	@Override
	public boolean isUserRole(String userid, String roleno) {
		List<Role> list = this.getUserRoles(userid);
		if(list!=null && list.size()>0) {
			for(Role r:list) {
				if(r.getCode().equals(roleno)) {
					return true;
				}
			}
		}
		return false;
	}
}
