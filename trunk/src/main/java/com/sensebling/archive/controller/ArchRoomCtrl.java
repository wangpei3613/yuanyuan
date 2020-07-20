package com.sensebling.archive.controller;


import javax.annotation.Resource;

import com.sensebling.archive.entity.ArchRoomRack;
import com.sensebling.archive.service.ArchRoomRackSvc;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.QueryParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.archive.entity.ArchRoom;
import com.sensebling.archive.service.ArchRoomSvc;

@Controller
@RequestMapping("/arch/room")
public class ArchRoomCtrl extends BasicsCtrl{
	@Resource
	private ArchRoomSvc archRoomSvc;
	@Resource
	private ArchRoomRackSvc archRoomRackSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/room/view";
	}
	@RequestMapping("/getList")
	@ResponseBody
	public Result getList(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.setSortField("addtime");
		qp.setSortOrder("desc");
		r.setData(archRoomSvc.findAll(qp));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		ArchRoom v = new ArchRoom();
		if(StringUtil.notBlank(id)) {
			v = archRoomSvc.get(id);
		}
		getRequest().setAttribute("v", v);
		return "/arch/room/editRoom";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchRoom v){
		Result r = new Result();
		if (!archRoomSvc.checkColumn("room_no", v.getRoom_no(), v.getId())) {
			return crudError(r.error("档案室编号重复"));
		}
		if(StringUtil.notBlank(v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archRoomSvc.update(v);
		}else {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archRoomSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		if (!archRoomRackSvc.checkColumn("arch_room_id", id)) {
			r.error("当前档案室含有档案架，不能删除！");
		} else {
			archRoomSvc.delete(id);
			r.success();
		}
		return crudError(r);
	}
}
