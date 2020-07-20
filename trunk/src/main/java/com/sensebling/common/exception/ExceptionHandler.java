package com.sensebling.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sensebling.common.exception.UserException.ExcCode;

public class ExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = Logger.getLogger(ExceptionHandler.class);
	private String excMessage = "";

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		System.out.println("异常[路径]"+handler);
		System.out.println("异常[信息]"+ex.getMessage());

		if (ex instanceof HibernateException) {
			ex.printStackTrace();
			logger.error("异常拦截器拦截到异常:" + "<br/>" + "uri为:" + handler + "<br/>"
					+ ex);

			excMessage = UserException.getExcMessage(ExcCode.DataProcessing);
			logger.error("<br/>异常信息：" + excMessage);
			request.setAttribute("msg", excMessage);

		}
		if (ex instanceof NullPointerException) {
			ex.printStackTrace();
			logger.error("异常拦截器拦截到异常:" + "<br/>" + "action的名称为:" + handler
					+ "<br/>" + ex);

			excMessage = UserException.getExcMessage(ExcCode.IllegalData);
			logger.error("<br/>异常信息：" + excMessage);
			request.setAttribute("msg", excMessage);
		}
		if (ex instanceof UserException) {
			ex.printStackTrace();
			logger.error("异常拦截器拦截到异常:" + "<br/>" + "action的名称为:" + handler
					+ "<br/>" + ex);
			excMessage = UserException.getExcMessage(ExcCode.AppError);
			logger.error("<br/>异常信息：" + excMessage);

			request.setAttribute("msg", excMessage);

		}
		if (ex instanceof NumberFormatException) {
			ex.printStackTrace();
			logger.error("异常拦截器拦截到异常:" + "<br/>" + "action的名称为:" + handler
					+ "<br/>" + ex);
			logger.error("<br/>异常信息：" + "数字格式化异常");
			request.setAttribute("msg", "数字格式化异常");

		}
		if(ex instanceof  Exception){
			ex.printStackTrace();
			logger.error("异常拦截器拦截到异常:" + "<br/>" + "action的名称为:" + handler
					+ "<br/>" + ex);
			excMessage = UserException.getExcMessage(ExcCode.AppError);
			logger.error("<br/>异常信息：" + excMessage);
			request.setAttribute("msg", excMessage);
		}

		return new ModelAndView("error");
	}
}
