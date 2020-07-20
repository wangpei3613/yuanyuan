package com.sensebling.ope.core.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.ope.core.entity.OpeCoreIndusRatio;
import com.sensebling.ope.core.service.OpeCoreIndusRatioSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 行业系数
 * @author
 * 
 */
@Controller
@RequestMapping("/ope/core/indusRatio")
public class OpeCoreIndusRatioCtrl extends BasicsCtrl{

	@Resource 
	private OpeCoreIndusRatioSvc opeCoreIndusRatioSvc;
	/**
	 * 行业gridTree
	 * @return
	 */
	@RequestMapping("/getTreeGrid")
	@ResponseBody
	public List<Map<String,Object>> getTreeGrid(String parentCode){
		QueryParameter qp = new QueryParameter();
		return opeCoreIndusRatioSvc.getTreeGrid(opeCoreIndusRatioSvc.findAll(qp));
	}
	
	@RequestMapping("/save")
	@ResponseBody
	@ModuleAuth({"indusRatio:openAdd","indusRatio:openEdit"})
	@DisposableToken
	public Result save(OpeCoreIndusRatio v) {
		Result r = new Result();
		if(StringUtil.isBlank(v.getId())) {
			//新增
			if(opeCoreIndusRatioSvc.checkColumn("code", v.getCode())) {
				opeCoreIndusRatioSvc.save(v);
				r.success();
			}else {
				r.setError("行业编码重复!");
			}
		}else {
			if(opeCoreIndusRatioSvc.checkColumn("code", v.getCode(), v.getId())) {
				OpeCoreIndusRatio vo = opeCoreIndusRatioSvc.get(v.getId());
				vo.setCode(v.getCode());
				vo.setName(v.getName());
				vo.setIndustryCoe(v.getIndustryCoe());
				vo.setMinDebtRatio(v.getMinDebtRatio());
				vo.setMaxDebtRatio(v.getMaxDebtRatio());
				vo.setParentCode(v.getParentCode());
				vo.setInduinterateratio(v.getInduinterateratio());
				vo.setCashsurpmultip(v.getCashsurpmultip());
				opeCoreIndusRatioSvc.update(vo);
				r.success();
			}else {
				r.setError("行业编码重复!");
			}
		}
		return crudError(r);
	}
	
	/**
	 * 删除行业
	 * @param v
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	@ModuleAuth("indusRatio:remove")
	@DisposableToken
	public Result del(String id) { 
		Result r = new Result();
		QueryParameter qp=new QueryParameter();
		qp.addParamter("parentCode", id);
		if(opeCoreIndusRatioSvc.findAllCount(qp)>0) {
			r.setError("当前行业含有子行业，不能删除");
		}else {
			opeCoreIndusRatioSvc.deleteByIds(id, false);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 进入行业系数配置页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/ope/core/indusRatio/index";
	}
	/**
	 * 进入行业系数编辑页面
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		OpeCoreIndusRatio v = new OpeCoreIndusRatio();
		if(StringUtil.notBlank(id)) {
			v = opeCoreIndusRatioSvc.get(id);
		}else {
			v.setParentCode(getRequestParam("pid"));  
		}
		getRequest().setAttribute("v", v);  
		return "/ope/core/indusRatio/edit";
	}
}
