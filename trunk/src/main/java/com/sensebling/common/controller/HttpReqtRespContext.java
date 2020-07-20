package com.sensebling.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sensebling.system.entity.User;
import com.sensebling.system.vo.UserModuleAuth;

public class HttpReqtRespContext {
	 
    private static ThreadLocal<HttpServletRequest> request_threadLocal = new ThreadLocal<HttpServletRequest>();
 
    private static ThreadLocal<HttpServletResponse> reponse_threadLocal = new ThreadLocal<HttpServletResponse>();
    
    private static ThreadLocal<User> user_threadLocal = new ThreadLocal<User>();
 
    public static void setRequest(HttpServletRequest request) {
        request_threadLocal.set(request);
    }
 
    public static HttpServletRequest getRequest() {
        return request_threadLocal.get();
    }
 
    public static void removeRequest() {
        request_threadLocal.remove();
    }
 
    public static void setResponse(HttpServletResponse response) {
        reponse_threadLocal.set(response);
    }
 
    public static HttpServletResponse getResponse() {
        return reponse_threadLocal.get();
    }
 
    public static void removeResponse() {
        reponse_threadLocal.remove();
    }
    
    public static HttpSession getSession() {
		return getRequest().getSession();
    }
    
    public static User getUser() {
    	return user_threadLocal.get();
    }
    public static void setUser(User u) {
    	user_threadLocal.set(u);
    }
    public static void removeUser() {
    	user_threadLocal.remove();
    }
    public static UserModuleAuth getUserModuleAuth(){
    	if(getUser() != null) {
    		return getUser().getAuth();
    	}
    	return null;
    }
}

