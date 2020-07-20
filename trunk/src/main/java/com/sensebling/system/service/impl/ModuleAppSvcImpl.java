package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.system.entity.ModuleApp;
import com.sensebling.system.entity.User;
import com.sensebling.system.entity.UserModuleApp;
import com.sensebling.system.service.ModuleAppSvc;
import com.sensebling.system.service.UserModuleAppSvc;
@Service
public class ModuleAppSvcImpl extends BasicsSvcImpl<ModuleApp> implements ModuleAppSvc{
	@Resource
	private UserModuleAppSvc userModuleAppSvc;
	
	@Override
	public void del(String id) {
		String sql = "delete from sen_module_app a where a.id=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_role_module_app a where a.moduleid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_user_module_app a where a.moduleid=?";
		baseDao.executeSQL(sql, id);
	}

	@Override
	public List<ModuleApp> findByRoleid(String roleid) {
		String sql = "select a.*,decode(b.id,null,'0','1') checked from sen_module_app a left join sen_role_module_app b on b.roleid=? and b.moduleid=a.id order by a.ordernum";
		return baseDao.findBySQLEntity(sql, new Object[] {roleid}, ModuleApp.class.getName());
	}

	@Override
	public List<ModuleApp> getIndexMenu(String appid) {
		User user = HttpReqtRespContext.getUser();
		String sql = "select a.* from sen_module_app a join sen_user_module_app b on b.moduleid=a.id join sen_module_app c on c.id=a.pid join sen_module_app d on d.id=c.pid where b.userid=? and exists (select 1 from sen_module_app t1 join sen_role_module_app t2 on t2.moduleid=t1.id join sen_user_role t3 on t3.roleid=t2.roleid where t3.userid=b.userid and t1.id=a.id) and d.id=? and a.status='1' and c.status='1' and d.status='1' order by b.ordernum";
		List<ModuleApp> menus = baseDao.findBySQLEntity(sql, ModuleApp.class.getName(), user.getId(), appid);
		if(menus==null || menus.size()==0) {
			sql = "select t1.* from sen_module_app t1 join sen_role_module_app t2 on t2.moduleid=t1.id join sen_user_role t3 on t3.roleid=t2.roleid join sen_module_app t4 on t4.id=t1.pid join sen_module_app t5 on t5.id=t4.pid where t3.userid=? and t1.indexshow='1' and t1.status='1' and t4.status='1' and t5.status='1' and t5.id=? order by t4.ordernum,t1.ordernum";
			menus = baseDao.findBySQLEntity(sql, ModuleApp.class.getName(), user.getId(), appid);
		}
		return menus;
	}
	
	@Override
	public List<ModuleApp> getMenu(String appid) {
		String sql = "select m.* from (select distinct a.*,decode(a.level,'3',b.checked,0) checked,decode(a.level,'3',b.userorder,null) userorder from sen_module_app a join (select t1.*,decode(t6.id,null,0,1) checked,t6.ordernum userorder from sen_module_app t1 join sen_role_module_app t2 on t2.moduleid=t1.id join sen_user_role t3 on t3.roleid=t2.roleid join sen_module_app t4 on t4.id=t1.pid join sen_module_app t5 on t5.id=t4.pid left join sen_user_module_app t6 on t6.moduleid=t1.id and t3.userid=t6.userid where t3.userid=? and t1.status='1' and t4.status='1' and t5.status='1' and t5.id=?) b on (b.id=a.id or a.id=b.pid)) m order by m.ordernum";
		return baseDao.findBySQLEntity(sql, ModuleApp.class.getName(), HttpReqtRespContext.getUser().getId(), appid);
	}

	@Override
	public void saveUserIndexModuleApp(String ids,String appid) {
		User user = HttpReqtRespContext.getUser();
		String sql = "delete from sen_user_module_app t where t.userid=? and t.moduleid in (select a.id from sen_module_app a join sen_module_app b on a.pid=b.id where b.pid=?)";
		baseDao.executeSQL(sql, user.getId(), appid);  
		int i = 0;
		List<UserModuleApp> list = new ArrayList<UserModuleApp>();
		for(String id:ids.split(",")) {
			UserModuleApp app = new UserModuleApp();
			app.setAddtime(DateUtil.getStringDate());
			app.setAdduser(user.getId());
			app.setModuleid(id);
			app.setUserid(user.getId());
			app.setOrdernum(++i);
			list.add(app);
		}
		userModuleAppSvc.save(list);
	}

}
