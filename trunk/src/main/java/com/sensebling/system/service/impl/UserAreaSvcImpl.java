package com.sensebling.system.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.UserArea;
import com.sensebling.system.service.UserAreaSvc;

@Service("userAreaSvc")
public class UserAreaSvcImpl extends BasicsSvcImpl<UserArea> implements
		UserAreaSvc {
	
	/**
	 * 更新用户分配的区域视野信息
	 * 方法会先删除用户原来分配的所有部门,然后重新新增
	 * @param userId 分配的用户ID
	 * @param areas 分配的区域
	 * @param createUserId 操作员id
	 * @return 若areas为空则删除之前的分配信息条数,否则返回删除后新增的分配信息条数
	 */
	public int updateUserArea(String userId, String areas,String createUserId) {
		String hql="delete from UserArea where userId='"+userId+"'";
		int i=baseDao.executeHQL(hql);//先删除以前的数据
		if(StringUtil.isBlank(areas))
			return i;
		String nowDate=DateUtil.getStringDate();
		int num=0;
		String[] areaIds=areas.split(",");
		for (String areaId:areaIds) {
			UserArea ua=new UserArea();
			ua.setUserId(userId);
			ua.setAreaId(areaId);
			ua.setCreateDate(nowDate);
			ua.setCreateUser(createUserId);
			this.save(ua);
			num++;
		}
		return num;
	}
}
