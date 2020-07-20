package com.sensebling.archive.controller;


import javax.annotation.Resource;

import com.sensebling.archive.service.ArchRoomRackBoxSvc;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.PropertiesCopyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.archive.entity.ArchRoomRack;
import com.sensebling.archive.service.ArchRoomRackSvc;

@Controller
@RequestMapping("/arch/room/rack")
public class ArchRoomRackCtrl extends BasicsCtrl{
	@Resource
	private ArchRoomRackSvc archRoomRackSvc;
	@Resource
	private ArchRoomRackBoxSvc archRoomRackBoxSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/room/rack/view";
	}
	@RequestMapping("/getList")
	@ResponseBody
	public Result getList(){
		Result r = new Result();
		if (StringUtil.notBlank(getQueryParameter().getParam("arch_room_id"))) {
			r.setData(archRoomRackSvc.findAll(getQueryParameter()));
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String roomid, String id) {
		ArchRoomRack v = new ArchRoomRack();
		if(StringUtil.notBlank(id)) {
			v = archRoomRackSvc.get(id);
		} else {
			v.setArch_room_id(roomid);
		}
		getRequest().setAttribute("v", v);
		return "/arch/room/editRoomRack";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchRoomRack v) throws Exception{
		Result r = new Result();
		if (!archRoomRackSvc.checkColumn("rack_no", v.getRack_no(), v.getId())) {
			return crudError(r.error("档案架编号重复"));
		}
		if(StringUtil.notBlank(v.getId())) {
			if (v.getRack_layer() <= 0 || v.getRack_layer_column() <= 0) {
				return crudError(r.error("档案室层数或每层盒数需要大于0"));
			}
			ArchRoomRack real = archRoomRackSvc.get(v.getId());
			if (real == null) {
				return crudError(r.error("要修改的档案室不存在"));
			}
			PropertiesCopyUtil.copyPropertiesInclude(v, real, "rack_no,rack_layer,rack_layer_column");
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archRoomRackSvc.update(real);
		}else {
			if (StringUtil.isBlank(v.getArch_room_id())) {
				return crudError(r.error("档案室id为空"));
			}
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archRoomRackSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		if (!archRoomRackBoxSvc.checkColumn("arch_room_rack_id", id)) {
			return crudError(r.error("当前档案架上有档案盒，不能删除"));
		}
		archRoomRackSvc.delete(id);
		r.success();
		return crudError(r);
	}
}
