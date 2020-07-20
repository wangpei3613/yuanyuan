package com.sensebling.archive.controller;


import javax.annotation.Resource;

import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.common.util.*;
import com.sensebling.system.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.archive.entity.ArchMenu;
import com.sensebling.archive.service.ArchMenuSvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/arch/menu")
public class ArchMenuCtrl extends BasicsCtrl{
	@Resource
	private ArchMenuSvc archMenuSvc;
	@Resource
	private ArchReelSvc archReelSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/menu/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archMenuSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/getTreeGrid")
	@ResponseBody
	public List<Map<String,Object>> getTreeGrid() {
		QueryParameter qp = new QueryParameter();
		qp.setSortField("ordernumber");
		qp.setSortOrder("asc");
		List<ArchMenu> parentlist = archMenuSvc.findAll(qp);
		List<Map<String,Object>> temp=new ArrayList<>();
		if (parentlist!=null && !parentlist.isEmpty()) {
			parentlist.forEach(m -> {
				Map<String,Object> map = JsonUtil.beanToMap(m);
				map.put("text", m.getMenu_name());
				temp.add(map);
			});
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}

	@RequestMapping("/toEdit")
	public String toEdit(String id, String pid) {
		ArchMenu v = new ArchMenu();
		if(StringUtil.notBlank(id)) {
			v = archMenuSvc.get(id);
		} else {
			v.setPid(pid);
		}
		getRequest().setAttribute("v", v);
		return "/arch/menu/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchMenu v){
		Result r = new Result();
		if (!archMenuSvc.checkColumn("menu_no", v.getMenu_no(), v.getId())) {
			return crudError(r.error("目录编号重复"));
		}
		if(StringUtil.notBlank(v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archMenuSvc.update(v);
		}else {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archMenuSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		if (!archMenuSvc.checkColumn("pid", id)) {
			return crudError(r.error("当前目录含有子菜单，不能删除"));
		}
		if (!archReelSvc.checkColumn("arch_menu_id", id)) {
			return crudError(r.error("当前目录含有档案，不能删除"));
		}
		archMenuSvc.delete(id);
		r.success();
		return crudError(r);
	}
}
