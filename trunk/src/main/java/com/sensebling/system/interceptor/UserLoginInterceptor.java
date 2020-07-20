package com.sensebling.system.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sensebling.common.util.DebugOut;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.listener.SessionManagerListener;
import com.sensebling.system.vo.UserOnlineInfo;

public class UserLoginInterceptor implements HandlerInterceptor{
	private static DebugOut logger=new DebugOut(UserLoginInterceptor.class);
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception arg3)
			throws Exception 
	{
		User loginUser=(User)request.getSession().getAttribute("user");
		if(loginUser==null)
			return;
		UserOnlineInfo uoi=SessionManagerListener.queryOnlineUser(loginUser.getId());
		String loginIP=request.getRemoteAddr();
		if(uoi==null)
			uoi=new UserOnlineInfo();
		if("true".equals(BasicsFinal.getParamVal("system.login.single")))//如果为单点登录
		{
			//获取所有此用户登录的session信息
			List<UserOnlineInfo.SessionInfo> list=uoi.getSessions();
			//使之前登录的所有session失效
			for(UserOnlineInfo.SessionInfo si:list)
				if(!request.getSession().getId().equals(si.getLoginSession().getId()))
					si.getLoginSession().invalidate();
		}
		uoi.setLoginUser(loginUser);
		uoi.addSession(request.getSession(), loginIP);
		SessionManagerListener.addOnlineUser(uoi);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception 
	{
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception 
	{
		logger.msgPrint("用户请求登录[账号:"+request.getParameter("username")+"]");
		return true;
	}

}
