package com.sensebling.archive.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.archive.entity.ArchReelTypeRole;
import com.sensebling.archive.service.ArchReelTypeRoleSvc;

@Controller
@RequestMapping("/arch/reel/type/role")
public class ArchReelTypeRoleCtrl extends BasicsCtrl{
	@Resource
	private ArchReelTypeRoleSvc archReelTypeRoleSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/reel/type/role/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archReelTypeRoleSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		ArchReelTypeRole v = new ArchReelTypeRole();
		if(StringUtil.notBlank(id)) {
			v = archReelTypeRoleSvc.get(id);
		}
		getRequest().setAttribute("v", v);
		return "/arch/reel/type/role/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchReelTypeRole v){
		Result r = new Result();
		if(StringUtil.notBlank(v.getId())) {
			archReelTypeRoleSvc.update(v);
		}else {
			archReelTypeRoleSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		archReelTypeRoleSvc.delete(id);
		r.success();
		return crudError(r);
	}
}
