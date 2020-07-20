package com.sensebling.system.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.context.RedisManager;

/**
 * 一次性令牌拦截校验
 */
public class DisposableTokenInterceptor implements HandlerInterceptor{
	@Autowired
	private RedisManager redisManager;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean result = true;
		if(handler instanceof HandlerMethod) {
			DisposableToken token = ((HandlerMethod) handler).getMethodAnnotation(DisposableToken.class);
			if(token != null) {
				String disposableToken = request.getHeader("disposableToken");
				if(StringUtil.notBlank(disposableToken)) {
				    if (redisManager.tryGetDistributedLock(ProtocolConstant.RedisPrefix.disposableTokenLock+":"+disposableToken,disposableToken,60)) {
				        if (redisManager.hasKey(ProtocolConstant.RedisPrefix.disposableToken, disposableToken)) {
			            	redisManager.removeRedisStorage(ProtocolConstant.RedisPrefix.disposableToken, disposableToken);
			            	String uuid = UUID.randomUUID().toString().replace("-", "");
							redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.disposableToken, uuid, "1", ProtocolConstant.disposableTokenTime); 
							response.addHeader("disposableToken", uuid);
				        }else {
					        redisManager.releaseDistributedLock(ProtocolConstant.RedisPrefix.disposableTokenLock+":"+disposableToken,disposableToken);
					        request.getRequestDispatcher("/master/disposableTokenError").forward(request, response);
					        result = false;
				        }
				    }else {
				    	response.setStatus(402);
				    	result = false;
				    }
				}else {
					request.getRequestDispatcher("/master/disposableTokenError").forward(request, response);
					result = false;
				}
			}
		}
		return result;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(handler instanceof HandlerMethod) {
			ResponseBody body = ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class);
			DisposableToken token = ((HandlerMethod) handler).getMethodAnnotation(DisposableToken.class);
			if(body == null) {
				String uuid = UUID.randomUUID().toString().replace("-", "");
				redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.disposableToken, uuid, "1", ProtocolConstant.disposableTokenTime); 
				request.setAttribute("disposableToken", uuid);
			}
			if(token != null) {
				String disposableToken = request.getHeader("disposableToken");
				if(StringUtil.notBlank(disposableToken) && redisManager.hasKey(ProtocolConstant.RedisPrefix.disposableTokenLock, disposableToken)) {
					redisManager.releaseDistributedLock(ProtocolConstant.RedisPrefix.disposableTokenLock+":"+disposableToken,disposableToken);
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
