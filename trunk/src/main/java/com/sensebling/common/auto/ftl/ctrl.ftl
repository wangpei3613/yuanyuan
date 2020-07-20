package ${package}.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import ${package}.entity.${entityName};
import ${package}.service.${entityName}Svc;

@Controller
@RequestMapping("${mapping}")
public class ${entityName}Ctrl extends BasicsCtrl{
	@Resource
	private ${entityName}Svc ${propName}Svc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "${mapping}/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(${propName}Svc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		${entityName} v = new ${entityName}();
		if(StringUtil.notBlank(id)) {
			v = ${propName}Svc.get(id);
		}
		getRequest().setAttribute("v", v);
		return "${mapping}/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(${entityName} v){
		Result r = new Result();
		if(StringUtil.notBlank(v.getId())) {
			${propName}Svc.update(v);
		}else {
			${propName}Svc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		${propName}Svc.delete(id);
		r.success();
		return crudError(r);
	}
}
