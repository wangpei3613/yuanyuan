package com.sensebling.system.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.Token;

import net.sf.json.JSONObject;
/**
 * Token 拦截器
 * @author Administrator
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
//				Map<String,Object> requestTokenMap = StringUtil.getRequestToken();
				boolean needSaveSession = annotation.save();
				if (needSaveSession) {
//					request.getSession(false).setAttribute("token", );
					
				}
				boolean needRemoveSession = annotation.remove();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						Result results = new Result();
		 				results.setError("信息已经被更新，请刷新页面后重试");
		 				JSONObject jsonObject = JSONObject.fromObject(results);
		 				response.setCharacterEncoding("utf-8");
		 				response.setStatus(450);
		 				response.getWriter().write(jsonObject.toString());
						return false;
					}
					
					request.getSession(false).removeAttribute("token");
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String clinetToken = (String) request.getSession(false).getAttribute("token");
//		String clinetToken = request.getParameter("token");
		
		if (clinetToken == null) {
			return true;
		}
		String clinetToken1 = request.getParameter("token");
		if(!clinetToken.equals(clinetToken1)){
			return true;
		}
//		String str  = StringUtil.getReqToken(clinetToken);
//		if("".equals(str)){
//			return true;
//		}
		StringUtil.removeReqToken(clinetToken);
		return false;
	}
}
