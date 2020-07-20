package com.sensebling.system.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
/**
 * 用户session过滤器,检查当前session是否存在用户
 * @author  
 *
 */
public class UserCheckFilter implements Filter {

	private Map<String, Boolean> exceptPath=new HashMap<String, Boolean>();
	protected DebugOut log=new DebugOut(this.getClass());
	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		String requestPath=request.getRequestURI().replaceFirst(request.getContextPath(), "");
		if(requestPath.equals("/"))//访问项目根路径时判断用户存在就直接进入系统管理界面,否则转发到登录页面
		{
			if((request.getSession().getAttribute("user") instanceof User)&&"1".equals(((User)request.getSession().getAttribute("user")).getStatus()))
				request.getRequestDispatcher("/system/manage/index").forward(request, arg1);
			else
				request.getRequestDispatcher("/login/request").forward(request, arg1);
				
			return;
		}
		//若用户指定访问index.jsp,login.jsp,login_popup.jso则让浏览器跳转到系统根路径
		if(requestPath.equals("/index.jsp")||requestPath.equals("/login.jsp")||requestPath.equals("/login_popup.jsp"))
		{
			((HttpServletResponse) arg1).sendRedirect(request.getContextPath()+"/");
			return;
		}
		boolean result=false;
		if(exceptPath.get(requestPath)!=null&&exceptPath.get(requestPath))//判断是否是不拦截的路径
			result=true;
		//判断session中是否存在user,并且对象为User类型,并且用户对象状态为生效时方可继续
		else if((request.getSession().getAttribute("user") instanceof User)&&"1".equals(((User)request.getSession().getAttribute("user")).getStatus()))
			result=true;
		if(result)
			arg2.doFilter(arg0, arg1);
		else
			request.getRequestDispatcher("/login/request?lastPath=@forward").forward(request, arg1);
		
		if("true".equalsIgnoreCase(BasicsFinal.getParamVal("request.url.print")))
			log.msgPrint("访问请求["+request.getMethod()+"]["+requestPath+"]["+(result?"通过":"不通过")+"]---");
	}
	public void init(FilterConfig arg0) throws ServletException {

		String exceptPathStr=arg0.getInitParameter("exceptPath");
		if(StringUtil.notBlank(exceptPathStr))
		{
			for(String ep:exceptPathStr.split(";"))
			{
				exceptPath.put(ep, true);
			}
		}

	}

}
