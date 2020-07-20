package com.sensebling.archive.controller;


import com.sensebling.archive.entity.ArchDestroy;
import com.sensebling.archive.entity.ArchReel;
import com.sensebling.archive.service.ArchDestroySvc;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.PropertiesCopyUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/arch/destroy")
public class ArchDestroyCtrl extends BasicsCtrl{
	@Resource
	private ArchDestroySvc archDestroySvc;
	@Resource
	private ArchReelSvc archreelsvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/destory/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archDestroySvc.findPageBysql(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id,String arch_reel_id) {
		ArchDestroy v = new ArchDestroy();
		if(StringUtil.notBlank(id)) {
			v = archDestroySvc.get(id);
		}else{
			v.setArch_reel_id(arch_reel_id);
		}
		getRequest().setAttribute("v", v);
		return "/arch/reel/editDestory";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchDestroy archdestroy){
		Result r = new Result();
		ArchReel reel = archreelsvc.get(archdestroy.getArch_reel_id());
		if(DateUtil.getDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getTime()<=DateUtil.getDate(reel.getExpire_date()).getTime()){
			return crudError(r.error("档案未过期,无法销毁"));
		}
		try {
			PropertiesCopyUtil.copyProperties(reel,archdestroy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		archdestroy.setArch_reel_no(reel.getReel_no());
		archdestroy.setDesty_date(DateUtil.getStringDate());
		archdestroy.setDesty_user(getUser().getId());
		archdestroy.setDesty_no(DateUtil.formatDateTimeString(new Date()));
		r.setData(archDestroySvc.xiaoHui(archdestroy));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		archDestroySvc.delete(id);
		r.success();
		return crudError(r);
	}
}
