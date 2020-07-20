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
import com.sensebling.index.entity.IndexLibrary;
import com.sensebling.index.entity.IndexVersion;
import com.sensebling.index.entity.IndexVersionConversion;
import com.sensebling.index.entity.IndexVersionDetail;
import com.sensebling.index.entity.IndexVersionParameter;
import com.sensebling.index.service.IndexHistoryVersionSvc;
import com.sensebling.index.service.IndexLibrarySvc;
import com.sensebling.index.service.IndexSvc;
import com.sensebling.index.service.IndexVersionConversionSvc;
import com.sensebling.index.service.IndexVersionDetailSvc;
import com.sensebling.index.service.IndexVersionParameterSvc;
import com.sensebling.index.service.IndexVersionSvc;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;

@Controller
@RequestMapping("/model/index/version")
public class IndexVersionCtrl extends BasicsCtrl{
	@Resource
	private IndexVersionSvc indexVersionSvc;
	@Resource
	private IndexVersionDetailSvc indexVersionDetailSvc;
	@Resource
	private IndexVersionParameterSvc indexVersionParameterSvc;
	@Resource
	private IndexVersionConversionSvc indexVersionConversionSvc;
	@Resource
	private IndexLibrarySvc indexLibrarySvc;
	@Resource
	private IndexSvc indexSvc;
	@Resource
	private IndexHistoryVersionSvc indexHistoryVersionSvc;
	/**
	 * 查询指标版本列表
	 * @return
	 */
	@RequestMapping(value="/getVersionPager")
	@ResponseBody
	public Result getVersionPager() {
		Result r = new Result();
		r.setData(indexVersionSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增指标版本
	 */
	@RequestMapping(value="/addVersion",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("indexVersion:openAdd")
	@DisposableToken
	public Result addVersion(IndexVersion v) {
		Result r = new Result();
		if(indexVersionSvc.checkCode(v.getCode(), v.getId())) {
			v.setCreatedate(DateUtil.getStringDate());
			v.setCreateuser(getUser().getId());  
			v.setChanged("1");  
			indexVersionSvc.save(v);
			r.success();
		}else {
			r.setError("参数编码重复");
		}
		return crudError(r);
	}
	/**
	 * 修改指标版本
	 */
	@RequestMapping(value="/updateVersion",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("indexVersion:openEdit")
	@DisposableToken
	public Result updateVersion(IndexVersion v) {
		Result r = new Result();
		if(indexVersionSvc.checkCode(v.getCode(), v.getId())) {
			indexVersionSvc.update(v);
			r.success();
		}else {
			r.setError("参数编码重复");
		}
		return crudError(r);
	}
	/**
	 * 删除指标版本
	 */
	@RequestMapping(value="/delVersion",method=RequestMethod.POST)
	@ResponseBody
	@ModuleAuth("indexVersion:remove")
	@DisposableToken
	public Result delVersion(String data) {
		Result r = new Result();
		List<IndexVersion> list = new Gson().fromJson(data, new TypeToken<List<IndexVersion>>(){}.getType());
		for(IndexVersion v:list) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("versionid", v.getId());
			if(indexHistoryVersionSvc.findAllCount(qp) == 0) {
				indexVersionSvc.delVersion(v.getId()); 
				r.success();
			}else {
				r.setError("当前指标版本已被使用，不能删除");
			}
		}
		return crudError(r);
	}
	/**
	 * 查询版本指标树
	 * @param versionid 版本id
	 * @return
	 */
	@RequestMapping("/getVersionIndexTree")
	@ResponseBody
	public List<Map<String,Object>> getVersionIndexTree(String versionid){
		return indexLibrarySvc.getTreeGrid(indexLibrarySvc.getVersionIndex(versionid));
	}
	/**
	 * 查询版本待选择指标树
	 * @param versionid 版本id
	 * @return
	 */
	@RequestMapping("/getIndexToVersionTree")
	@ResponseBody
	public List<Map<String,Object>> getIndexToVersionTree(String versionid){
		return indexLibrarySvc.getTreeGrid(indexLibrarySvc.getIndexToVersionTree(versionid));
	}
	/**
	 * 新增版本指标
	 * @param ids 指标ids
	 * @param versionid 版本id
	 * @return
	 */
	@RequestMapping("/addVersionIndex")
	@ResponseBody
	@DisposableToken
	public Result addVersionIndex(String ids, String versionid) {
		Result r = new Result();
		if(StringUtil.notBlank(ids) && StringUtil.notBlank(versionid)) {
			indexVersionDetailSvc.addVersionIndex(ids, versionid);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 修改版本指标
	 * @param v
	 * @return
	 */
	@RequestMapping("/updateVersionIndex")
	@ResponseBody
	@DisposableToken
	public Result updateVersionIndex(IndexVersionDetail v) {
		Result r = new Result();
		if(StringUtil.isBlank(v.getContent())) {
			r.setError("指标内容不能为空");
			return crudError(r);
		}
		IndexLibrary index = indexLibrarySvc.get(v.getIndexid());
		IndexVersionDetail detail = indexVersionDetailSvc.get(v.getId());
		if(index!=null && detail!=null) {
			if("1".equals(index.getCategory())) {
				v.setFormula(null);
			}else {
				if(StringUtil.isBlank(v.getFormula())) {
					r.setError("指标公式不能为空");
					return crudError(r);
				}
			}
			detail.setArgument(v.getArgument());
			detail.setContent(v.getContent());
			detail.setFormula(v.getFormula());
			detail.setMaxscore(v.getMaxscore());
			detail.setRemark(v.getRemark());
			detail.setSort(v.getSort());
			detail.setStatus(v.getStatus());
			indexVersionDetailSvc.update(detail); 
			indexVersionSvc.setChanged(detail.getVersionid()); 
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 删除版本指标
	 * @param id
	 * @return
	 */
	@RequestMapping("/delVersionIndex")
	@ResponseBody
	@DisposableToken
	public Result delVersionIndex(String id) {
		Result r = new Result();
		IndexVersionDetail detail = indexVersionDetailSvc.get(id);
		if(detail != null) {
			indexVersionDetailSvc.delVersionIndex(detail.getId());
			indexVersionSvc.setChanged(detail.getVersionid()); 
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 查询版本指标参数列表
	 * @return
	 */
	@RequestMapping(value="/getParameterPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getParameterPager() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		Pager pager = qp.getPager(); 
		if(StringUtil.notBlank(qp.getParam("indexversionid"))) {     
			pager = indexVersionParameterSvc.getParameterPager(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增版本指标参数
	 */
	@RequestMapping(value="/addParameter",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addParameter(IndexVersionParameter v) {
		Result r = new Result();
		if(StringUtil.notBlank(v.getIndexversionid())) { 
			IndexVersionDetail detail = indexVersionDetailSvc.get(v.getIndexversionid());
			if(detail != null) {
				if(indexVersionParameterSvc.checkCode(v.getCode(), v.getIndexversionid())) {
					indexVersionParameterSvc.save(v);
					indexVersionSvc.setChanged(detail.getVersionid()); 
					r.success();
				}else {
					r.setError("参数编码重复");
				}
			}
		}
		return crudError(r);
	}
	/**
	 * 修改版本指标参数
	 */
	@RequestMapping(value="/updateParameter",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result updateParameter(IndexVersionParameter v) {
		Result r = new Result();
		IndexVersionParameter p = indexVersionParameterSvc.get(v.getId());
		IndexVersionDetail detail = indexVersionDetailSvc.get(v.getIndexversionid());
		if(p != null) {
			p.setDatatype(v.getDatatype());
			p.setName(v.getName());
			p.setRemark(v.getRemark());
			p.setSort(v.getSort());
			p.setType(v.getType());
			p.setValue(v.getValue());
			p.setValuetype(v.getValuetype());
			indexVersionParameterSvc.update(p);
			indexVersionSvc.setChanged(detail.getVersionid()); 
			r.success();
		}else {
			if(detail!=null && detail.getIndexid().equals(v.getIndexid())) { 
				v.setId(null);
				indexVersionParameterSvc.save(v);
				indexVersionSvc.setChanged(detail.getVersionid()); 
				r.success();
			}
		}
		return crudError(r);
	}
	/**
	 * 删除版本指标参数
	 */
	@RequestMapping(value="/delParameter",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delParameter(String data) {
		Result r = new Result();
		List<IndexVersionParameter> list = new Gson().fromJson(data, new TypeToken<List<IndexVersionParameter>>(){}.getType());
		for(IndexVersionParameter v:list) {
			IndexVersionParameter p = indexVersionParameterSvc.get(v.getId());
			if(p != null) {
				IndexVersionDetail detail = indexVersionDetailSvc.get(p.getIndexversionid());
				if(detail != null) {
					indexVersionParameterSvc.delete(p);
					indexVersionSvc.setChanged(detail.getVersionid()); 
					r.success();
				}
			}else {
				r.setError("来源于指标库的数据不能删除");
			}
		}
		return crudError(r);
	}
	/**
	 * 查询版本指标分值转换列表
	 * @return
	 */
	@RequestMapping(value="/getConversionPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getConversionPager() {
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		Pager pager = qp.getPager(); 
		if(StringUtil.notBlank(qp.getParam("indexversionid"))) {     
			pager = indexVersionConversionSvc.getConversionPager(qp); 
		}
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	/**
	 * 新增版本指标分值转换
	 */
	@RequestMapping(value="/addConversion",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addConversion(IndexVersionConversion v) {
		Result r = new Result();
		if(StringUtil.notBlank(v.getIndexversionid())) { 
			IndexVersionDetail detail = indexVersionDetailSvc.get(v.getIndexversionid());
			if(detail != null) {
				if(indexVersionConversionSvc.checkCode(v.getCode(), v.getIndexversionid())) {
					indexVersionConversionSvc.save(v);
					indexVersionSvc.setChanged(detail.getVersionid()); 
					r.success();
				}else {
					r.setError("转换编码重复");
				}
			}
		}
		return crudError(r);
	}
	/**
	 * 修改版本指标分值转换
	 */
	@RequestMapping(value="/updateConversion",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result updateConversion(IndexVersionConversion v) {
		Result r = new Result();
		IndexVersionConversion c = indexVersionConversionSvc.get(v.getId());
		IndexVersionDetail detail = indexVersionDetailSvc.get(v.getIndexversionid());
		if(c != null) {
			c.setName(v.getName());
			c.setRemark(v.getRemark());
			c.setSort(v.getSort());
			c.setValue(v.getValue());
			indexVersionConversionSvc.update(c);
			indexVersionSvc.setChanged(detail.getVersionid()); 
			r.success();
		}else {
			if(detail!=null && detail.getIndexid().equals(v.getIndexid())) { 
				v.setId(null);
				indexVersionConversionSvc.save(v);
				indexVersionSvc.setChanged(detail.getVersionid()); 
				r.success();
			}
		}
		return crudError(r);
	}
	/**
	 * 删除版本指标分值转换
	 */
	@RequestMapping(value="/delConversion",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delConversion(String data) {
		Result r = new Result();
		List<IndexVersionConversion> list = new Gson().fromJson(data, new TypeToken<List<IndexVersionConversion>>(){}.getType());
		for(IndexVersionConversion v:list) {
			IndexVersionConversion c = indexVersionConversionSvc.get(v.getId());
			if(c != null) {
				IndexVersionDetail detail = indexVersionDetailSvc.get(c.getIndexversionid());
				if(detail != null) {
					indexVersionConversionSvc.delete(c);
					indexVersionSvc.setChanged(detail.getVersionid()); 
					r.success();
				}
			}else {
				r.setError("来源于指标库的数据不能删除");
			}
		}
		return crudError(r);
	}
	@RequestMapping(value="/calc")
	@ResponseBody
	public Result calc() {
		Result r = new Result();
		String caseid = "";
		String versionid = "d0be0250-aa63-4b75-a3b7-38b8772937ae";
		indexSvc.calcIndex(caseid, versionid, false); 
		//r.setData(indexSvc.getCalcDataTree(caseid, versionid));
		r.success();
		return crudError(r);
	}
	/**
	 * 查询指标计算结果
	 */
	@RequestMapping(value="/getCalcData")
	@ResponseBody
	public Result getCalcData(String caseid) {
		Result r = new Result();
		r.setData(indexSvc.getCalcDataTree(caseid));
		r.success();
		return crudError(r);
	}
	/**
	 * 进入指标版本管理主页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/model/version/version/index";
	}
	/**
	 * 进入指标版本编辑页面
	 * @return
	 */
	@RequestMapping("/toVersionEdit")
	public String toVersionEdit(String id) {
		IndexVersion v = new IndexVersion();
		if(StringUtil.notBlank(id)) {
			v = indexVersionSvc.get(id);
		}
		getRequest().setAttribute("v", v);  
		return "/model/version/version/edit";
	}
	/**
	 * 进入指标版本详情页面
	 * @return
	 */
	@RequestMapping("/toVesionDetail")
	public String toVesionDetail() {
		return "/model/version/index";
	}
	/**
	 * 进入版本指标配置页面
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "/model/version/index/index";
	}
	/**
	 * 进入版本指标选择页面
	 * @return
	 */
	@RequestMapping("/toAddVersionIndex")
	public String toAddVersionIndex() {
		return "/model/version/index/getIndex";
	}
	/**
	 * 进入版本指标编辑页面
	 * @return
	 */
	@RequestMapping("/toIndexEdit")
	public String toIndexEdit(String id) {
		IndexVersionDetail v = indexVersionDetailSvc.get(id);
		getRequest().setAttribute("v", v);  
		return "/model/version/index/edit";
	}
	/**
	 * 进入版本指标参数配置页面
	 * @return
	 */
	@RequestMapping("/toParameter")
	public String toParameter() {
		return "/model/version/parameter/index";
	}
	/**
	 * 进入版本指标参数编辑页面
	 * @return
	 */
	@RequestMapping("/toParameterEdit")
	public String toParameterEdit(String id) {
		return "/model/version/parameter/edit";
	}
	/**
	 * 进入版本指标分值转换配置页面
	 * @return
	 */
	@RequestMapping("/toConversion")
	public String toConversion() {
		return "/model/version/conversion/index";
	}
	/**
	 * 进入版本指标分值转换编辑页面
	 * @return
	 */
	@RequestMapping("/toConversionEdit")
	public String toConversionEdit(String id) {
		return "/model/version/conversion/edit";
	}
	/**
	 * 进入指标版本历史页面
	 * @return
	 */
	@RequestMapping("/toVesionHistory")
	public String toVesionHistory() {
		return "/model/history/index";
	}
	/**
	 * 进入指标数据查看页面
	 * @return
	 */
	@RequestMapping("/toView")
	public String toView(String id) {
		return "/model/version/view";
	}
}
