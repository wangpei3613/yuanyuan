package com.sensebling.system.exception;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.Result;
import com.sensebling.ope.sync.util.HtmlUtil;
/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@SuppressWarnings("unchecked")
    @ExceptionHandler(value = Exception.class)
    public String errorHandler(Exception ex) throws Exception{
		log.info("进入全局系统处理：");
		HttpServletRequest request = HttpReqtRespContext.getRequest();
		HttpServletResponse response = HttpReqtRespContext.getResponse();
		String requestURL = request.getRequestURI().replace(request.getContextPath(), "").toString();
		log.error("异常请求路径为：{}", requestURL);  
		
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<String> enu = request.getParameterNames();
		while(enu.hasMoreElements()){
			String paraName = enu.nextElement();
			params.put(paraName, request.getParameter(paraName));
		} 
		log.error("异常请求参数为：{}", JsonUtil.entityToJSON(params));
		log.error(HtmlUtil.getExceptionInfo(ex));  
		
		if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			Result r = new Result();
			r.setError(HtmlUtil.getExceptionInfo(ex));  
			response.setStatus(450); 
			response.getWriter().write(JsonUtil.entityToJSON(r));  
		}else {
			request.setAttribute("errInfo", HtmlUtil.getExceptionInfo(ex));
			return "/index/sysError";
		}
        return null;
    }
}
