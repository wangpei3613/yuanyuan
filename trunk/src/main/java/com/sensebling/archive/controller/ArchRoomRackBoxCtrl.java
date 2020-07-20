package com.sensebling.archive.controller;


import javax.annotation.Resource;

import com.sensebling.archive.entity.ArchReel;
import com.sensebling.archive.entity.ArchRoomRack;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.archive.service.ArchRoomRackSvc;
import com.sensebling.common.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.archive.entity.ArchRoomRackBox;
import com.sensebling.archive.service.ArchRoomRackBoxSvc;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/arch/room/rack/box")
public class ArchRoomRackBoxCtrl extends BasicsCtrl{
	@Resource
	private ArchRoomRackBoxSvc archRoomRackBoxSvc;
	@Resource
	private ArchRoomRackSvc archRoomRackSvc;
	@Resource
	private ArchReelSvc archReelSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/room/index";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archRoomRackBoxSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id, String rackid, Integer layer, Integer column) {
		ArchRoomRackBox v = new ArchRoomRackBox();
		if(StringUtil.notBlank(id)) {
			v = archRoomRackBoxSvc.get(id);
		} else {
			v.setArch_room_rack_id(rackid);
			v.setLayer(layer);
			v.setColumn(column);
		}
		getRequest().setAttribute("v", v);
		return "/arch/room/editRackBox";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchRoomRackBox v){
		Result r = new Result();
		if (!archRoomRackBoxSvc.checkColumn("box_no", v.getBox_no(), v.getId())) {
			return crudError(r.error("档案室编号重复"));
		}
		ArchRoomRack rack = archRoomRackSvc.get(v.getArch_room_rack_id());
		if (rack == null) {
			return crudError(r.error("档案架不存在"));
		}
		if (v.getBox_num() <= 0) {
			return crudError(r.error("档案盒卷数需大于0"));
		}
		if (rack.getRack_layer() < v.getLayer()) {
			return crudError(r.error("所在层大于档案架层数"));
		}
		if (rack.getRack_layer_column() < v.getColumn()) {
			return crudError(r.error("所在列大于档案架列数"));
		}
		Map<String, Object> map = new HashMap<>(3);
		map.put("arch_room_rack_id", v.getArch_room_rack_id());
		map.put("layer", v.getLayer());
		map.put("column", v.getColumn());
		if (!archRoomRackBoxSvc.checkColumns(map, v.getId())) {
			return crudError(r.error("当前位置上已有其他盒，不能操作"));
		}
		if(StringUtil.notBlank(v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archRoomRackBoxSvc.update(v);
		}else {
			v.setAdduser(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archRoomRackBoxSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		if (!archReelSvc.checkColumn("arch_room_rack_box_id", id)) {
			return crudError(r.error("当前档案盒含有档案，不能删除"));
		}
		archRoomRackBoxSvc.delete(id);
		r.success();
		return crudError(r);
	}
	@RequestMapping("/getListByRackid")
	@ResponseBody
	public Result getListByRackid(String rackid) {
		Result r = new Result();
		r.setData(archRoomRackBoxSvc.getListByRackid(rackid));
		r.success();
		return crudError(r);
	}
}
