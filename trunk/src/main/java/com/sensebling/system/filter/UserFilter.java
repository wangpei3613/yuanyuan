package com.sensebling.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class UserFilter implements Filter{

	@Override 
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		String requestPath=request.getRequestURI().replaceFirst(request.getContextPath(), "");
		if(requestPath.equals("/")){//访问项目根路径时判断用户存在就直接进入系统管理界面,否则转发到登录页面
			((HttpServletResponse) arg1).sendRedirect(request.getContextPath()+"/master/index");
			//RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/view/master/index.jsp");
			//dispatcher.forward(arg0, arg1);
		}else{
			chain.doFilter(arg0, arg1);
		}
	}

	@Override
	public void destroy() {
	}

}
