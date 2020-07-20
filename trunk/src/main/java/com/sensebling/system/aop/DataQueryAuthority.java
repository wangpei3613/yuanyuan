package com.sensebling.system.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.sensebling.common.controller.HttpReqtRespContext;


/** 
 * aop拦截器 
 * 
 */  
public class DataQueryAuthority implements MethodInterceptor  {	
	/**
	 * AOP拦截器方法
	 */
	public Object invoke(MethodInvocation arg0) throws Throwable{  		  
        try{  
        	String departFilter="";
        	if(null!=HttpReqtRespContext.getRequest().getSession().getAttribute("departFilter")){
        		departFilter=" and deptId in("+HttpReqtRespContext.getRequest().getSession().getAttribute("departFilter")+")";
        	}
        	 // 拦截方法是否是Service接口的findPage分页方法  
            if (arg0.getMethod().getName().equalsIgnoreCase("findPage"))             
            {  
                Object[] args = arg0.getArguments();// 被拦截的参数  
                // 修改被拦截的参数  
                arg0.getArguments()[0] = args[0]+""+departFilter;  
            }  
            return arg0.proceed();// 运行UserService接口的queryPager方法  
  
        } catch (Exception e){  
            throw e;  
        }  
    }  
}
