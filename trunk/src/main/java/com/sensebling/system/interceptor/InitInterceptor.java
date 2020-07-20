package com.sensebling.system.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.util.DebugOut;
import com.sensebling.system.finals.ErrorInfoFinal;
/**
 * 全局拦截器,拦截所有的访问,可用于在项目临时维护时处理当前的请求
 * @author  
 *
 */
public class InitInterceptor implements HandlerInterceptor{
	private static boolean systemMaintaining=false;
	private static DebugOut logger=new DebugOut(InitInterceptor.class);
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception arg3)
			throws Exception 
	{
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception 
	{
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception 
	{
		HttpReqtRespContext.setRequest(request);
		HttpReqtRespContext.setResponse(response);
		
		if(systemMaintaining)//若系统处于维护状态则转发到系统维护页面 
		{
			if((handler instanceof HandlerMethod)&&((HandlerMethod)handler).getMethodAnnotation(ResponseBody.class)!=null)
			{
				if(request.getRequestURI().contains("/system/manage/finishSystemMaintaining"))
					return true;
				response.reset();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain; charset=utf-8");
				response.getWriter().print(ErrorInfoFinal.$E000.toJson());//系统维护中
			}
			else
				request.getRequestDispatcher("/maintain.jsp").forward(request, response);
		}
		
		
		return !systemMaintaining;
	}
	/**
	 * 开启系统维护状态
	 */
	public static void startMaintain()
	{
		systemMaintaining=true;
		logger.msgPrint("开启系统维护状态...");
	}
	/**
	 * 结束系统维护状态
	 */
	public static void finishMaintain()
	{
		systemMaintaining=false;
		logger.msgPrint("系统维护已完成...");
	}
}
