package com.sensebling.archive.controller;


import javax.annotation.Resource;

import com.sensebling.archive.entity.ArchMenu;
import com.sensebling.common.util.*;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.archive.entity.ArchReelFiles;
import com.sensebling.archive.service.ArchReelFilesSvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/arch/reel/files")
public class ArchReelFilesCtrl extends BasicsCtrl{
	@Resource
	private ArchReelFilesSvc archReelFilesSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/reel/files/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archReelFilesSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id,String did) {
		ArchReelFiles v = new ArchReelFiles();
		if(StringUtil.notBlank(id)) {
			v = archReelFilesSvc.get(id);
		}else{
			v.setArch_reel_id(did);
		}
		getRequest().setAttribute("v", v);
		return "/arch/reel/editFile";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchReelFiles v){
		Result r = new Result();
		if (!archReelFilesSvc.checkColumn("file_name", v.getFile_name(), v.getId())) {
			return crudError(r.error("文件名称重复"));
		}
		if(StringUtil.notBlank(v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archReelFilesSvc.update(v);
		}else {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archReelFilesSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		archReelFilesSvc.delete(id);
		r.success();
		return crudError(r);
	}

	/**
	 * 新增文件选择父文件下拉框
	 * @return
	 */
	@RequestMapping("/getTreeGrid")
	@ResponseBody
	public List<Map<String,Object>> getTreeGrid(String file_type,String arch_reel_id) {
		QueryParameter qp = new QueryParameter();
		qp.setSortField("serial");
		qp.setSortOrder("asc");
		if(StringUtil.notBlank(file_type)){
			qp.addParamter("file_type",file_type);
		}
		List<Map<String,Object>> temp=new ArrayList<>();
		if(StringUtil.notBlank(arch_reel_id)){
			qp.addParamter("arch_reel_id",arch_reel_id);
			List<ArchReelFiles> list = archReelFilesSvc.findAll(qp);
			if (list!=null && !list.isEmpty()) {
				list.forEach(m -> {
					Map<String,Object> map = JsonUtil.beanToMap(m);
					map.put("text", m.getFile_name());
					temp.add(map);
				});
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}
}
