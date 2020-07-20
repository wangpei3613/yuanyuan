package com.sensebling.system.interceptor;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.util.CookieUtil;
import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.context.TokenManager;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.vo.UserModuleAuth;


public class UserCheckInterceptor implements HandlerInterceptor{
	
	private String promptPage;
	protected DebugOut log=new DebugOut(this.getClass());
	@Autowired
	private TokenManager tokenManager;
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		response.setCharacterEncoding("utf-8");
		
		MDC.put("localip", getHostIp()); 
		MDC.put("ip", StringUtil.getIpAddress(request));
		String requestMethod=request.getMethod();
		String requestURL=request.getRequestURI().replace(request.getContextPath(), "").toString();
		boolean result=false;
		
		if(requestURL.equals("/")) {
    		request.getRequestDispatcher("/master/index").forward(request, response);
    		return false;
		}
		
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		AuthIgnore annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
		ModuleAuth moduleAuth = ((HandlerMethod) handler).getMethodAnnotation(ModuleAuth.class);
		
		//如果有@AuthIgnore注解，则不验证token
        if(annotation != null){
            return true;
        }
        
        String token = CookieUtil.getToken(request);
        User user = tokenManager.getUserInfoByToken(token);
        if(user != null) {
        	HttpReqtRespContext.setUser(user); 
        	tokenManager.refreshUserToken(token);
        	
        	if(moduleAuth != null) {//验证用户菜单权限
				UserModuleAuth userModuleAuth = user.getAuth();
				if(StringUtil.isBlank(request.getParameter("_app")) && !userModuleAuth.checkUserAuth(moduleAuth.value())) {  
					if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
						response.setStatus(460);
					}else {
						request.getRequestDispatcher("/login/noAuth").forward(request, response);
					}
				}else {
					result = true;
				}
			}else {
				result = true;
			}
        	
        }else {
        	ResponseBody responseBody = ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class);
        	if("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) || responseBody!=null){
	            //告诉ajax我是重定向
	            response.setHeader("REDIRECT", "REDIRECT");
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        }else{
        		request.setAttribute("lastPath", requestURL);  
				request.getRequestDispatcher(promptPage).forward(request, response);
	        }
        }
		
		//输出日志
		if("true".equalsIgnoreCase(BasicsFinal.getParamVal("request.url.print")))
			log.msgPrint("访问请求["+requestMethod+"]["+requestURL+"]["+(result?"通过":"不通过")+"]---");
		return result;
	}

	public String getPromptPage() {
		return promptPage;
	}

	public void setPromptPage(String promptPage) {
		this.promptPage = promptPage;
	}
	
	private static String getHostIp(){
		try{
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements()){
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					InetAddress ip = (InetAddress) addresses.nextElement();
					if (ip != null 
							&& ip instanceof Inet4Address
                    		&& !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                    		&& ip.getHostAddress().indexOf(":")==-1){
						return ip.getHostAddress();
					} 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
