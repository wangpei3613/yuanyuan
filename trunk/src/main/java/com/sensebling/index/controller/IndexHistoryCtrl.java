package com.sensebling.index.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexHistoryDetail;
import com.sensebling.index.service.IndexHistoryConversionSvc;
import com.sensebling.index.service.IndexHistoryDetailSvc;
import com.sensebling.index.service.IndexHistoryParameterSvc;
import com.sensebling.index.service.IndexHistoryVersionSvc;
@Controller
@RequestMapping("model/index/history") 
public class IndexHistoryCtrl extends BasicsCtrl{
	@Resource
	private IndexHistoryVersionSvc indexHistoryVersionSvc;
	@Resource
	private IndexHistoryConversionSvc indexHistoryConversionSvc;
	@Resource
	private IndexHistoryParameterSvc indexHistoryParameterSvc;
	@Resource
	private IndexHistoryDetailSvc indexHistoryDetailSvc;
	
	/**
	 * 查询指标版本历史列表
	 * @return
	 */
	@RequestMapping(value="/getVersionPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getVersionPager() {
		Result r = new Result();
		r.setData(indexHistoryVersionSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 查询版本历史指标树
	 * @param versionid 版本id
	 * @return
	 */
	@RequestMapping("/getVersionIndexTree")
	@ResponseBody
	public List<Map<String,Object>> getVersionIndexTree(String hisid){
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(StringUtil.notBlank(hisid)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("hisid", hisid);
			qp.setSortField("sort");
			List<IndexHistoryDetail> indexs = indexHistoryDetailSvc.findAll(qp);
			if(indexs!=null && indexs.size()>0) {
				for(IndexHistoryDetail m:indexs) {
					map = JsonUtil.beanToMap(m);
					map.put("iconCls", "1".equals(m.getLevel())?"menumanagetreegrid-parent":"menumanagetreegrid-leaf");
					temp.add(map);
				}
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "indexid", "pid");
	}
	/**
	 * 查询版本历史指标参数列表
	 * @return
	 */
	@RequestMapping(value="/getParameterPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getParameterPager() {
		Result r = new Result();
		r.setData(indexHistoryParameterSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 查询版本历史指标分值转换列表
	 * @return
	 */
	@RequestMapping(value="/getConversionPager",method=RequestMethod.GET)
	@ResponseBody
	public Result getConversionPager() {
		Result r = new Result();
		r.setData(indexHistoryConversionSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 进入版本历史列表页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/model/history/version/index";
	}
	/**
	 * 进入版本历史指标页面
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex() {
		return "/model/history/index/index";
	}
	/**
	 * 进入版本历史指标参数页面
	 * @return
	 */
	@RequestMapping("/toParameter")
	public String toParameter() {
		return "/model/history/parameter/index";
	}
	/**
	 * 进入版本历史指标分值转换页面
	 * @return
	 */
	@RequestMapping("/toConversion")
	public String toConversion() {
		return "/model/history/conversion/index";
	}
}
