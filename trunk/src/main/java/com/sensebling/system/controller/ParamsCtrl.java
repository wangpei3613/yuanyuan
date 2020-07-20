package com.sensebling.system.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.context.RedisManager;
import com.sensebling.system.entity.Params;
import com.sensebling.system.service.ParamsSvc;
/**
 * 系统参数
 *
 */
@Controller
@RequestMapping("/system/params")
public class ParamsCtrl extends BasicsCtrl{
	@Resource
	private ParamsSvc paramsSvc;
	@Autowired
	private RedisManager redisManager;
	/**
	 * 查询系统参数列表
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result list() {
		Result r = new Result();
		r.setData(paramsSvc.findPage(getQueryParameter()));  
		r.success();
		return crudError(r);
	}
	/**
	 * 修改系统参数
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	@ModuleAuth("systemParams:openEdit")
	@DisposableToken
	public Result update(Params v) {
		Result r = new Result();
		Params param = paramsSvc.get(v.getId());
		if(param != null) {
			param.setValue(v.getValue());
			param.setUpdatetime(DateUtil.getStringDate());
			param.setUpdateuser(getUser().getId());   
			paramsSvc.update(param);
			redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.param, param.getId(), param.getValue());
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 进入系统参数列表页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/sys/params/index";
	}
	/**
	 * 进入系统参数编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(String id) {
		Params v = new Params();  
		if(StringUtil.notBlank(id)) {
			v = paramsSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "/sys/params/edit";
	}
}
