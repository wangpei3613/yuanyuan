package com.sensebling.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.UserPwdSecure;
import com.sensebling.system.service.UserPwdSecureSvc;
@Service("userPwdSecureSvc")
public class UserPwdSecureSvcImpl extends BasicsSvcImpl<UserPwdSecure> implements UserPwdSecureSvc {
	

	public void updateUserSecure(UserPwdSecure ups) {
		baseDao.update(ups);
	}


	public UserPwdSecure findUserSecureByuserId(String userid) {
		StringBuffer hql=new StringBuffer("from UserPwdSecure t where 1=1 ");		
		if(StringUtil.notBlank(userid)){
			hql.append(" and t.user='"+ userid+"'");
		}
		List<UserPwdSecure> upslist = baseDao.find(hql.toString());
		UserPwdSecure ups = null;
		for(int i=0;i<upslist.size();i++){
			ups=upslist.get(0);
			
		}
		return ups;
	}


	public void saveUserSecure(UserPwdSecure ups) {
		baseDao.save(ups);
	}
}
