package com.sensebling.index.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexConversion;
import com.sensebling.index.entity.IndexLibrary;
import com.sensebling.index.entity.IndexParameter;
import com.sensebling.index.service.IndexConversionSvc;
import com.sensebling.index.service.IndexLibrarySvc;
import com.sensebling.index.service.IndexParameterSvc;
import com.sensebling.index.service.IndexValueDetailSvc;
import com.sensebling.index.service.IndexVersionDetailSvc;
import com.sensebling.index.service.IndexVersionSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
/**
 * 指标库配置
 *
 */
@Controller
@RequestMapping("/model/index/config")
public class IndexLibraryCtrl extends BasicsCtrl{ 
	@Resource
    private IndexLibrarySvc indexLibrarySvc;
	@Resource
	private IndexParameterSvc indexParameterSvc;
	@Resource
	private IndexConversionSvc indexConversionSvc;
	@Resource
	private IndexValueDetailSvc indexValueDetailSvc;
	@Resource
	private IndexVersionSvc indexVersionSvc;
	@Resource
	private IndexVersionDetailSvc indexVersionDetailSvc;
	/**
	 * 查询指标树
	 * @param level 指标等级  1时只查询大类
	 * @return
	 */
	@RequestMapping("/getTreeGrid")
	@ResponseBody
	public List<Map<String,Object>> getTreeGrid(String level){
		QueryParameter qp = new QueryParameter();
		qp.setSortField("sort");
		if("1".equals(level)) {
			qp.addParamter("level", level);
		}
		return indexLibrarySvc.getTreeGrid(indexLibrarySvc.findAll(qp));
	}
	/**
	 * 保存指标
	 * @param v
	 * @return
	 */
	@RequestMapping("/saveLibrary")
	@ResponseBody
	@ModuleAuth({"indexLibrary:openAdd","indexLibrary:openEdit"})
	@DisposableToken
	public Result saveLibrary(IndexLibrary v) {
		Result r = new Result();
		if("2".equals(v.getLevel())) {
			if(StringUtil.isBlank(v.getCategory()) || StringUtil.isBlank(v.getContent())) {
				r.setError("指标类别和内容不能为空");
				return crudError(r);
			}
			if("1".equals(v.getCategory())) {
				v.setFormula(null);
			}else {
				if(StringUtil.isBlank(v.getFormula())) {
					r.setError("指标公式不能为空");
					return crudError(r);
				}
			}
			if("448d9284-ba35-409a-8ce9-5e30039dad3e".equals(v.getPid())) {
				r.setError("请选择子集指标大类");
				return crudError(r);
			}
			if(StringUtil.notBlank(v.getId())) {
				indexLibrarySvc.update(v);
				indexVersionSvc.updateChangedByIndex(v.getId());
				r.success();
			}else {
				v.setCreatedate(DateUtil.getStringDate());
				v.setCreateuser(getUser().getId());  
				v.setCode("INDEX_"+indexLibrarySvc.getNextSeq());
				indexLibrarySvc.save(v);
				r.success();
			}
		}else {
			r.setError("不能操作指标大类");
		}
		return crudError(r);
	}
	/**
	 * 删除指标
	 * @param id
	 * @return
	 */
	@RequestMapping("/delLibrary")
	@ResponseBody
	@ModuleAuth("indexLibrary:remove")
	@DisposableToken
	public Result delLibrary(String id) {
		Result r = new Result();
		IndexLibrary index = indexLibrarySvc.get(id);
		if(index != null) {
			if("2".equals(index.getLevel())) {
				QueryParameter qp = new QueryParameter();
				qp.addParamter("indexid", index.getId());
				if(indexValueDetailSvc.findAllCount(qp) == 0 && indexVersionDetailSvc.findAllCount(qp) == 0) {
					indexLibrarySvc.delLibrary(id);
					r.success();
				}else {
					r.setError("当前指标已被版本使用，不能删除");
				}
			}else {
				r.setError("不能操作指标大类");
			}
		}
		return crudError(r);
	}
	/**
	 * 查询指标参数列表
	 * @return
	 */
	@RequestMapping(value="/getParameterPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getParameterPager() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		Pager pager = qp.getPager(); 
		if(StringUtil.notBlank(qp.getParam("indexid"))) {     
			pager = indexParameterSvc.findPage(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增指标参数
	 */
	@RequestMapping(value="/addParameter",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addParameter(IndexParameter v) {
		Result r = new Result();
		if(indexParameterSvc.checkCode(v.getCode(), v.getIndexid(), v.getId())) {
			indexParameterSvc.save(v);
			indexVersionSvc.updateChangedByIndexParameter(v.getIndexid(), v.getCode());
			r.success();
		}else {
			r.setError("参数编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改指标参数
	 */
	@RequestMapping(value="/updateParameter",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result updateParameter(IndexParameter v) {
		Result r = new Result();
		if(indexParameterSvc.checkCode(v.getCode(), v.getIndexid(), v.getId())) {
			indexParameterSvc.update(v);
			indexVersionSvc.updateChangedByIndexParameter(v.getIndexid(), v.getCode());
			r.success();
		}else {
			r.setError("参数编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除指标参数
	 */
	@RequestMapping(value="/delParameter",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delParameter(String data) {
		Result r = new Result();
		List<IndexParameter> list = new Gson().fromJson(data, new TypeToken<List<IndexParameter>>(){}.getType());
		for(IndexParameter v:list) {
			IndexParameter parameter = indexParameterSvc.get(v.getId());
			if(parameter != null) {
				indexParameterSvc.delete(parameter);  
				indexVersionSvc.updateChangedByIndexParameter(parameter.getIndexid(), parameter.getCode());
				r.success();
			}
		}
		return crudError(r);
	}
	/**
	 * 查询指标分值转换列表
	 * @return
	 */
	@RequestMapping(value="/getConversionPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getConversionPager() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		Pager pager = qp.getPager(); 
		if(StringUtil.notBlank(qp.getParam("indexid"))) {     
			pager = indexConversionSvc.findPage(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增指标分值转换
	 */
	@RequestMapping(value="/addConversion",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addConversion(IndexConversion v) {
		Result r = new Result();
		if(indexConversionSvc.checkCode(v.getCode(), v.getIndexid(), v.getId())) {
			indexConversionSvc.save(v);
			indexVersionSvc.updateChangedByIndexConversion(v.getIndexid(), v.getCode());
			r.success();
		}else {
			r.setError("参数编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改指标分值转换
	 */
	@RequestMapping(value="/updateConversion",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result updateConversion(IndexConversion v) {
		Result r = new Result();
		if(indexConversionSvc.checkCode(v.getCode(), v.getIndexid(), v.getId())) {
			indexConversionSvc.update(v);
			indexVersionSvc.updateChangedByIndexConversion(v.getIndexid(), v.getCode());
			r.success();
		}else {
			r.setError("参数编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除指标分值转换
	 */
	@RequestMapping(value="/delConversion",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delConversion(String data) {
		Result r = new Result();
		List<IndexConversion> list = new Gson().fromJson(data, new TypeToken<List<IndexConversion>>(){}.getType());
		for(IndexConversion v:list) {
			IndexConversion conversion = indexConversionSvc.get(v.getId());
			if(conversion != null) {
				indexConversionSvc.delete(conversion);  
				indexVersionSvc.updateChangedByIndexConversion(conversion.getIndexid(), conversion.getCode());
				r.success();
			}
		}
		return crudError(r);
	}
	/**
	 * 进入指标库管理主页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/model/library/index";
	}
	/**
	 * 进入指标库配置页面
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "/model/library/index/index";
	}
	/**
	 * 进入指标库编辑页面
	 * @return
	 */
	@RequestMapping("/toIndexEdit")
	public String toIndexEdit(String id) {
		IndexLibrary v = new IndexLibrary();
		if(StringUtil.notBlank(id)) {
			v = indexLibrarySvc.get(id);
		}else {
			v.setPid(getRequestParam("pid")); 
			v.setLevel("2");  
		}
		getRequest().setAttribute("v", v);   
		return "/model/library/index/edit";
	}
	/**
	 * 进入指标参数配置页面
	 * @return
	 */
	@RequestMapping("/toParameter")
	public String toParameter() {
		return "/model/library/parameter/index";
	}
	/**
	 * 进入指标参数编辑页面
	 * @return
	 */
	@RequestMapping("/toParameterEdit")
	public String toParameterEdit(String id) {
		IndexParameter v = new IndexParameter();
		if(StringUtil.notBlank(id)) {
			v = indexParameterSvc.get(id);
		}else {
			v.setIndexid(getRequestParam("indexid"));  
		}
		getRequest().setAttribute("v", v);   
		return "/model/library/parameter/edit";
	}
	/**
	 * 进入指标库分值转换配置页面
	 * @return
	 */
	@RequestMapping("/toConversion")
	public String toConversion() {
		return "/model/library/conversion/index";
	}
	/**
	 * 进入指标分值转换编辑页面
	 * @return
	 */
	@RequestMapping("/toConversionEdit")
	public String toConversionEdit(String id) {
		IndexConversion v = new IndexConversion();
		if(StringUtil.notBlank(id)) {
			v = indexConversionSvc.get(id);
		}else {
			v.setIndexid(getRequestParam("indexid"));  
		}
		getRequest().setAttribute("v", v);
		return "/model/library/conversion/edit";
	}
}
