package com.sensebling.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.system.interceptor.InitInterceptor;
/**
 * 系统全局管理控制器
 * [主页跳转,消息通知,...]
 * @author 
 * @time 2014-12-05
 */
@Controller
@RequestMapping("/system/manage")
public class SysMgrCtrl extends BasicsCtrl{
	
	@RequestMapping(value="/index")
	public String toIndex(Model model){
		return "index";
	}
	/**
	 * 启动系统维护,调用该方法后所有的访问都会被拦截并阻止,重启系统后才能访问系统
	 * 此方法用户客户现场临时维护系统时开启
	 * 只要管理员admin才能调用该方法
	 * @return 1:成功,0:失败
	 * @throws Exception
	 */
	@RequestMapping(value="/startSystemMaintaining")
	@ResponseBody
	public String startSysMaintaining() throws Exception
	{
		if("admin".equals(getUser().getUserName()))
			InitInterceptor.startMaintain();
		else
			return "0";
		return "1";
	}
	/**
	 * 停止系统维护,调用该方法后取消系统的维护状态
	 * 此方法用户客户现场临时维护系统时开启
	 * 只要管理员admin才能调用该方法
	 * @return 1:成功,0:失败
	 * @throws Exception
	 */
	@RequestMapping(value="/finishSystemMaintaining")
	@ResponseBody
	public String stopSysMaintaining() throws Exception
	{
		if("admin".equals(getUser().getUserName()))
			InitInterceptor.finishMaintain();
		else
			return "0";
		return "1";
	}
}
