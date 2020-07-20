package com.sensebling.system.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Result;
import com.sensebling.system.bean.LoginParam;
import com.sensebling.system.entity.User;

/**
 * 
 * @author YY
 *
 */
public interface LoginSvc extends BasicsSvc<User>{
	
	/**
	 * 
	 * @param maps
	 * @return
	 * @throws Exception
	 */
	Result doLogin(Map<String,Object> maps )throws Exception;
	
	/**
	 * 生成二维码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String createQRCode(HttpServletRequest request)throws Exception;

	/**
	 * 判断
	 * @param request
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	String selectQrCode(HttpServletRequest request, String uuid) throws Exception;
	
	/**
	 * 手机端扫描登录
	 * @param paraMap
	 * @throws Exception
	 */
	Result updateQrCode(Map<String, Object> paraMap) throws Exception;

	/**
	 * 检查版本
	 * @param _appid
	 * @param os
	 * @return
	 * 2018年7月11日-下午2:06:49
	 * YY
	 */
	String checkVersion(Map<String,Object> parm)throws Exception;
	/**
	 * 登陆逻辑
	 * @param param 登陆相关参数
	 * @return
	 */
	Result login(LoginParam param);
}
