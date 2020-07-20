package com.sensebling.system.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.UserPwdSecure;



public interface UserPwdSecureSvc extends BasicsSvc<UserPwdSecure> {

	public void updateUserSecure(UserPwdSecure ups);
	
	public UserPwdSecure findUserSecureByuserId(String userid);
	
	public void saveUserSecure(UserPwdSecure ups);

}
