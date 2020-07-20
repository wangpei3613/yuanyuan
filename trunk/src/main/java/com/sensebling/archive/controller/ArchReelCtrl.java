package com.sensebling.archive.controller;


import com.sensebling.archive.entity.ArchReel;
import com.sensebling.archive.entity.ArchReelType;
import com.sensebling.archive.service.ArchReelFilesSvc;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.archive.service.ArchReelTypeSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/arch/reel")
public class ArchReelCtrl extends BasicsCtrl{
	@Resource
	private ArchReelSvc archReelSvc;
	@Resource
	private ArchReelTypeSvc archReelTypeSvc;
	@Resource
	private ArchReelFilesSvc archReelFilesSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/reel/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		if(StringUtil.notBlank(getQueryParameter().getParam("arch_menu_id"))){
			r.setData(archReelSvc.findPageBysql(getQueryParameter(),getUser().getId()));
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id,String arch_menu_id) {
		ArchReel v = new ArchReel();
		if(StringUtil.notBlank(id)) {
			v = archReelSvc.get(id);
		}else{
			v.setArch_menu_id(arch_menu_id);
			v.setReel_status("1");
		}
		getRequest().setAttribute("v", v);
		return "/arch/reel/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchReel v){
		Result r = new Result();
		if (!archReelSvc.checkColumn("reel_no", v.getReel_no(), v.getId())) {
			return crudError(r.error("目录编号重复"));
		}
		if(StringUtil.notBlank(v.getId())) {
			if(StringUtil.notBlank(v.getFile_date())){
				return crudError(r.error("该档案已归档，无法修改"));
			}
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archReelSvc.update(v);
		}else {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archReelSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		ArchReel v = archReelSvc.get(id);
		if(StringUtil.notBlank(v.getFile_date())){
			return crudError(r.error("该档案已归档，无法修改"));
		}
		if (!archReelFilesSvc.checkColumn("arch_reel_id", id)) {
			return crudError(r.error("当前档案下面有文件，不能删除"));
		}
		archReelSvc.delete(id);
		r.success();
		return crudError(r);
	}

	/**
	 * 查询档案类型表，用于下拉列表
	 * isSelect:是否显示请选择
	 * @return
	 */
	@RequestMapping(value="/getDalx_list")
	public @ResponseBody
	List<Map<String,String>> getDalx_list(){
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<ArchReelType> datas=archReelTypeSvc.findAll();
		if(datas==null)
			return temp;
		for(ArchReelType content:datas){
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", content.getName());
			map.put("value", content.getId());
			temp.add(map);

		}
		return temp;
	}

	/**
	 * 归档
	 * @param id
	 * @return
	 */
	@RequestMapping("/guiDang")
	@ResponseBody
	public Result guiDang(String id) {
		Result r = new Result();
		if (StringUtil.notBlank(id)) {
			ArchReel v = archReelSvc.get(id);
			v.setFile_date(DateUtil.getStringDate());
			v.setReel_status("2");
			archReelSvc.update(v);
		}
		r.success();
		return crudError(r);
	}
	/**
	 * 上架
	 * @param id
	 * @return
	 */
	@RequestMapping("/upShelf")
	@ResponseBody
	public Result upShelf(String id,String boxid) {
		Result r = new Result();
		ArchReel v = archReelSvc.get(id);
		if(StringUtil.isBlank(v.getFile_date())){
			return crudError(r.error("该档案未归档，无法上架"));
		}
		r.setData(archReelSvc.shangJia(id,boxid));
		r.success();
		return crudError(r);
	}
	/**
	 * 下架
	 * @param id
	 * @return
	 */
	@RequestMapping("/downShelf")
	@ResponseBody
	public Result downShelf(String id) {
		Result r = new Result();
		ArchReel v = archReelSvc.get(id);
		if("4".equals(v.getReel_status())){
			return crudError(r.error("档案已下架"));
		}
		if("5".equals(v.getReel_status())){
			return crudError(r.error("档案已借阅"));
		}
		if("6".equals(v.getReel_status())){
			return crudError(r.error("档案已过期"));
		}
		if(!"3".equals(v.getReel_status())){
			return crudError(r.error("请选择上架的档案"));
		}
		r.setData(archReelSvc.xiaJia(id));
		r.success();
		return crudError(r);
	}
	/**
	 * 进入选择档案盒的页面
	 */
	@RequestMapping("/toBoxView")
	public String toBoxView() {
		return "/arch/reel/boxView";
	}
	/**
	 * 进入档案到期时间延期的页面
	 */
	@RequestMapping("/toDelayView")
	public String toDelayView(String id) {
		ArchReel v = new ArchReel();
		v = archReelSvc.get(id);
		getRequest().setAttribute("v",v);
		return "/arch/reel/editDelay";
	}
	@RequestMapping("/delay")
	@ResponseBody
	public Result delay(ArchReel v){
		Result r = new Result();
		if(StringUtil.notBlank(v.getId())) {
			ArchReel archreel = archReelSvc.get(v.getId());
			if(DateUtil.getDate(archreel.getExpire_date()).getTime()>=DateUtil.getDate(v.getExpire_date()).getTime()){
				return crudError(r.error("请选择比当前到期时间晚的时间"));
			}
			archreel.setExpire_date(v.getExpire_date());
			archreel.setUpdatetime(DateUtil.getStringDate());
			archreel.setUpdateuser(getUser().getId());
			archReelSvc.update(archreel);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/getPagerPart")
	@ResponseBody
	public Result getPagerPart(){
		Result r = new Result();
		if(StringUtil.notBlank(getQueryParameter().getParam("arch_menu_id"))){
			r.setData(archReelSvc.findPartPageBysql(getQueryParameter(),getUser().getId()));
		}
		r.success();
		return crudError(r);
	}
}