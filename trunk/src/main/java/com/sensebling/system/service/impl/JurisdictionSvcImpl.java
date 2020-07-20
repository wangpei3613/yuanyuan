package com.sensebling.system.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.CRUDUtil;
import com.sensebling.system.service.JurisdictionSvc;
import com.sensebling.system.vo.Jurisdiction;

/**
 * 权限控制业务实现
 * @author 
 * @date 2011-9-24
 */
@Service("jurisdictionSvc")
public class JurisdictionSvcImpl extends BasicsSvcImpl<Jurisdiction> implements JurisdictionSvc {
	public List<Jurisdiction> findByUserId(String userId) {
		StringBuffer sql =new StringBuffer(" select t.userId,t.roleId,t.url,t.priority,t.crud,t.moduleId from user_role_module t,sen_module s,sen_module k where t.url is not null and t.userId=? and t.moduleId=s.id and s.pid=k.id ");
		List<Object> obj = new ArrayList<Object>();
		obj.add(userId);
		sql.append(" order by k.ordernumber,s.ordernumber ");
		List<Jurisdiction> jurisdictions = new ArrayList<Jurisdiction>();
		List<Object[]> list = this.baseDao.queryBySql(sql.toString(), obj);
		Map<String,String> map= new HashMap<String, String>();
		for(int i =0 ;i<list.size();i++){
			if(map.get(list.get(i)[5])!=null)
				continue;
			Jurisdiction jdt = new Jurisdiction();
			jdt.setCrud(Integer.parseInt(list.get(i)[4].toString()));
			//jdt.setPriority(Integer.parseInt(list.get(i)[3].toString()) );
			jdt.setUrl(list.get(i)[2].toString());
			jdt.setRoleId(list.get(i)[1].toString());
			jdt.setModuleId(list.get(i)[5].toString());
			jdt.setUserId(list.get(i)[0].toString());
			for(int j=i ; j<list.size() ; j++)
			{
				if(list.get(j)[5].equals(jdt.getModuleId()))
				{
					int[] cruds = {jdt.getCrud(),Integer.parseInt(list.get(j)[4].toString())};
					//把模块相同的权限汇总
					jdt.setCrud(CRUDUtil.getCRUD(cruds));
					//添加汇总的权限信息
				}
			}
			map.put(list.get(i)[5].toString(),"true");
			jurisdictions.add(jdt);
		}
		
		return jurisdictions;
	}

}
