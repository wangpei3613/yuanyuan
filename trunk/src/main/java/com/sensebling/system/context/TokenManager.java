package com.sensebling.system.context;

import com.sensebling.system.entity.User;

/**
 * token管理接口
 * @author llmke
 *
 */
public interface TokenManager {
	/**
     * 创建token
     * @param user
     * @return 
     */
    String getToken(User user);
    /**
     * 刷新用户
     * @param token
     */
    void refreshUserToken(String token);
    /**
     * 用户退出登陆
     * @param token
     */
    void loginOff(String token);
    /**
     * 获取用户信息
     * @param token
     * @return
     */
    User getUserInfoByToken(String token);
    /**
	 * 设置用户有效期
	 */
	void setUserValidity(String userid, Long time);
}
