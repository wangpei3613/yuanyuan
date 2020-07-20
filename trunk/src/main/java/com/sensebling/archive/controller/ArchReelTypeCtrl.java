package com.sensebling.archive.controller;


import javax.annotation.Resource;

import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.common.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.archive.entity.ArchReelType;
import com.sensebling.archive.service.ArchReelTypeSvc;

@Controller
@RequestMapping("/arch/reel/type")
public class ArchReelTypeCtrl extends BasicsCtrl{
	@Resource
	private ArchReelTypeSvc archReelTypeSvc;
	@Resource
	private ArchReelSvc archReelSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/reelType/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archReelTypeSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		ArchReelType v = new ArchReelType();
		if(StringUtil.notBlank(id)) {
			v = archReelTypeSvc.get(id);
		}
		getRequest().setAttribute("v", v);
		return "/arch/reelType/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchReelType v){
		Result r = new Result();
		if (!archReelTypeSvc.checkColumn("code", v.getCode(), v.getId())) {
			return crudError(r.error("类别编码重复"));
		}
		if(StringUtil.notBlank(v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archReelTypeSvc.update(v);
		}else {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archReelTypeSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		if (!archReelSvc.checkColumn("reel_type", id)) {
			return crudError(r.error("当前档案类别已被使用，不能删除"));
		}
		archReelTypeSvc.delete(id);
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toAllotRole")
	public String toAllotRole() {
		return "/arch/reelType/allotRole";
	}
	@RequestMapping("/getArchTypeRole")
	@ResponseBody
	public Result getArchTypeRole(String typeid) {
		Result r = new Result();
		r.setData(archReelTypeSvc.getArchTypeRole(typeid));
		r.success();
		return crudError(r);
	}
	@PostMapping("addArchTypeRole")
	@ResponseBody
	public Result addArchTypeRole(String typeid, String roleIds) {
		Result r = new Result();
		archReelTypeSvc.addArchTypeRole(typeid, roleIds);
		r.success();
		return crudError(r);
	}
}
