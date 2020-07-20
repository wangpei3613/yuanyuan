package com.sensebling.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.system.service.OperaCreditLogSvc;

/**
 * 修改记录
 * @author YY
 *
 */
@Controller
@RequestMapping("/system/opeRecord")
public class OperaCreditLogCtrl extends BasicsCtrl {
	
	
	@Resource
	private OperaCreditLogSvc operaCreditLogSvc;
	
	@RequestMapping(value="/queryAll")
	@ResponseBody
	public Result queryAll() throws Exception
	{
		Result r = new Result();
		r.setData(operaCreditLogSvc.queryAll(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/index")
	public String index() {
		return "/ope/record/index";
	}
}
