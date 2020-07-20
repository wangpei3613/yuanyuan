package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.system.entity.Module;
import com.sensebling.system.entity.ModuleAuth;
import com.sensebling.system.service.ModuleAuthSvc;
import com.sensebling.system.vo.UserModuleAuth;
@Service
public class ModuleAuthSvcImpl extends BasicsSvcImpl<ModuleAuth> implements ModuleAuthSvc{

	@Override
	public void delModuleAuth(String id) {
		String sql = "delete from sen_module_auth a where a.id=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module_auth t where t.authid=?";
		baseDao.executeSQL(sql, id);
	}

	@Override
	public Map<String, List<ModuleAuth>> getModuleAuths(String type, String roleid) {
		List<ModuleAuth> list = getModuleAuthsList(type, roleid);
		Map<String, List<ModuleAuth>> map = new HashMap<String, List<ModuleAuth>>();
		if(list!=null && list.size()>0) {
			for(ModuleAuth v:list) {
				if(!map.containsKey(v.getModuleid())) {
					map.put(v.getModuleid(), new ArrayList<ModuleAuth>());
				}
				map.get(v.getModuleid()).add(v);
			}
		}
		return map;
	}

	@Override
	public List<ModuleAuth> getModuleAuthsList(String type, String roleid) {
		if("1".equals(type)) {
			String sql = "select a.*,decode(c.authid,null,'0','1') checked from sen_module_auth a join sen_module b on b.id=a.moduleid left join (select t2.authid from sen_role_module t1 join sen_role_module_auth t2 on t2.rolemoduleid=t1.id where t1.roleid=?) c on c.authid=a.id order by a.moduleid,a.sort";
			return baseDao.findBySQLEntity(sql, ModuleAuth.class.getName(),roleid);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserModuleAuth getUserModuleAuth(String id) {
		UserModuleAuth o = new UserModuleAuth();
		String sql = "select distinct c.* from sen_user_role a join sen_role_module b on b.roleid=a.roleid join sen_module c on c.id=b.moduleid where a.userid=? and c.isuse='1' order by c.ordernumber";
		List listModule = baseDao.findBySQLEntity(sql, Module.class.getName(), id);
		Set<String> set = new HashSet<String>();
		Map<String, List<ModuleAuth>> mapAuths = new HashMap<String, List<ModuleAuth>>();
		Set<String> authFunctionCode = new HashSet<String>();
		if(listModule!=null && listModule.size()>0) {
			sql = "select distinct d.* from sen_user_role a join sen_role_module b on b.roleid=a.roleid join sen_role_module_auth c on c.rolemoduleid=b.id join sen_module_auth d on d.id=c.authid where a.userid=? and d.status='1' order by d.sort";
			List<ModuleAuth> listModuleAuth = baseDao.findBySQLEntity(sql, ModuleAuth.class.getName(), id);
			Map<String, Module> map = new HashMap<String, Module>();
			for(Module module:(List<Module>)listModule) {
				map.put(module.getId(), module);
				set.add(module.getModuleno());
			}
			if(listModuleAuth!=null && listModuleAuth.size()>0) {
				for(ModuleAuth moduleAuth:listModuleAuth) {
					if(map.containsKey(moduleAuth.getModuleid())) {
						set.add(map.get(moduleAuth.getModuleid()).getModuleno()+":"+moduleAuth.getCode());
						if("1".equals(moduleAuth.getType())) {//针对按钮权限
							if(!mapAuths.containsKey(moduleAuth.getModuleid())) {
								mapAuths.put(moduleAuth.getModuleid(), new ArrayList<ModuleAuth>());
							}
							mapAuths.get(moduleAuth.getModuleid()).add(moduleAuth);
						}else {
							authFunctionCode.add(map.get(moduleAuth.getModuleid()).getModuleno()+":"+moduleAuth.getCode());
						}
					}
				}
			}
		}
		o.setAuthCode(set);  
		o.setModuleAuths(mapAuths); 
		o.setAuthFunctionCode(authFunctionCode);  
		return o;
	}

}
