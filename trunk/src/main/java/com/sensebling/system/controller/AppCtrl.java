package com.sensebling.system.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.system.annotation.AuthIgnore;
@Controller
@RequestMapping("/sen/app")
public class AppCtrl extends BasicsCtrl{
	/**
	 * app请求拦截器
	 * @return
	 */
	@RequestMapping
	@AuthIgnore
	public String appFilter(@RequestParam(required=true) String action) {
		return "forward:"+action;
	}
	@RequestMapping("/noLogin")  
	@ResponseBody
	@AuthIgnore
	public Result noLogin() {
		getResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return new Result();
	}
}
