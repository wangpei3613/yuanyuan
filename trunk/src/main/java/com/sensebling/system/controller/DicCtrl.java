package com.sensebling.system.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.context.RedisManager;
import com.sensebling.system.entity.DictionaryContent;
import com.sensebling.system.entity.DictionaryType;
import com.sensebling.system.service.DicContentSvc;
import com.sensebling.system.service.DicTypeSvc;

/**
 * 系统操作相关的controller
 * @author 
 *
 */
@Controller
@RequestMapping("/system/dictionary")
public class DicCtrl extends BasicsCtrl {
	@Resource
	private DicContentSvc dictionaryContentService;
	@Resource
	private DicTypeSvc dictionaryTypeService;
	@Autowired
	private RedisManager redisManager;
	
	@RequestMapping(value="/queryPaper",method=RequestMethod.GET)
	@ResponseBody
	public Result queryPaper()
	{
		Result r = new Result();
		QueryParameter qb =	getQueryParameter();
		Pager pager = dictionaryTypeService.queryPaper(qb);
		r.setData(pager);
		r.success();
		return crudError(r);
	}
	
	/**
	 * 数据字典添加
	 * @param dictionaryType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Result add(DictionaryType dictionaryType)throws Exception{
		Result r = new Result();
		if(dictionaryTypeService.checkCode(dictionaryType.getCode(),null)) {
			dictionaryType.setCreateDate(DateUtil.getStringDate());
			dictionaryType.setCreateUser(getUser().getId());
			dictionaryTypeService.save(dictionaryType);
			redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.dict, dictionaryType.getCode(), this.dictionaryContentService.findByTypeCode(dictionaryType.getId()));
			r.success(); 
		}else {
			r.setError("编码已存在");
		}
		return crudError(r);
	}
	
	/**
	 * 修改
	 * YF
	 * @param dictionaryType
	 * @return
	 * @throws Exception
	 * 2017-12-16-下午04:08:48
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result update(DictionaryType dictionaryType)throws Exception{
		Result r = new Result();
		if(dictionaryTypeService.checkCode(dictionaryType.getCode(),dictionaryType.getId())) {
			dictionaryType.setCreateDate(DateUtil.getStringDate());
			dictionaryType.setCreateUser(getUser().getId());
			dictionaryTypeService.merge(dictionaryType);
			redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.dict, dictionaryType.getCode(), this.dictionaryContentService.findByTypeCode(dictionaryType.getId()));
			r.success();
		}else {
			r.setError("编码已存在");
		}
		return crudError(r);
	}
	
	/**
	 * 删除
	 * YF
	 * @param list
	 * @return
	 * @throws Exception
	 * 2017-12-16-下午04:15:14
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delete(String data)throws Exception{
		Result r = new Result();
		List<DictionaryType> list = new Gson().fromJson(data, new TypeToken<List<DictionaryType>>(){}.getType());
		for(DictionaryType role:list) {
			DictionaryType type = dictionaryTypeService.get(role.getId());
			if(type != null) {
				dictionaryTypeService.delDictType(role.getId());
				redisManager.removeRedisStorage(ProtocolConstant.RedisPrefix.dict, type.getCode());
			}
		}
		r.success(); 
		return crudError(r);
	}
	
	/**
	 * 
	 * 查询数据字典子项
	 * YF
	 * @param typeId
	 * @return
	 * 2017-12-20-下午05:41:03
	 */
	@RequestMapping(value="/queryContent")
	@ResponseBody
	public Result queryContent()
	{
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		if(StringUtil.notBlank(qp.getParam("typeId"))) {
			r.setData(dictionaryContentService.findPage(qp));
		}
		r.success();
		return crudError(r);
	}
	
	/**
	 * 修改数据字典子项
	 * YF
	 * @param dictionaryContent
	 * @return
	 * 2018-1-2-下午04:43:29
	 */
	@RequestMapping(value="/updateDicContent",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result updateDicContent(DictionaryContent dictionaryContent)
	{
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", dictionaryContent.getTypeId());
		map.put("dictionaryCode", dictionaryContent.getDictionaryCode());  
		if(dictionaryContentService.checkColumns(map, dictionaryContent.getId())) {
			DictionaryType type = dictionaryTypeService.get(dictionaryContent.getTypeId());
			dictionaryContent.setDtype(type);
			dictionaryContent.setDtypeCode(type.getCode());
			dictionaryContentService.merge(dictionaryContent);
			redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.dict, type.getCode(), this.dictionaryContentService.findByTypeCode(type.getId()));
			r.success();
		}else {
			r.setError("编码重复");
		}
		return crudError(r);
	}
	
	/**
	 * 
	 * YF
	 * @param dictionaryContent
	 * @return
	 * 2018-1-2-下午05:11:43
	 */
	@RequestMapping(value="/addDicContent",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addDicContent(DictionaryContent dictionaryContent)
	{
		Result r = new Result();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", dictionaryContent.getTypeId());
		map.put("dictionaryCode", dictionaryContent.getDictionaryCode());  
		if(dictionaryContentService.checkColumns(map)) {
			DictionaryType type = dictionaryTypeService.get(dictionaryContent.getTypeId());
			dictionaryContent.setDtype(type);
			dictionaryContent.setDtypeCode(type.getCode());
			dictionaryContent.setCreateDate(DateUtil.getStringDate());
			dictionaryContent.setCreateUser(getUser().getId());
			dictionaryContentService.save(dictionaryContent);
			redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.dict, type.getCode(), this.dictionaryContentService.findByTypeCode(type.getId()));
			r.success();
		}else {
			r.setError("编码重复");
		}
		return crudError(r);
	}
	
	/**
	 * 
	 * YF
	 * @param list
	 * @return
	 * @throws Exception
	 * 2018-1-2-下午05:11:46
	 */
	@RequestMapping(value="/delDicContent",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delDicContent(String data)throws Exception{
		Result r = new Result();
		List<DictionaryContent> list = new Gson().fromJson(data, new TypeToken<List<DictionaryContent>>(){}.getType());
		if(list!=null && list.size()>0) {
			for(DictionaryContent content:list) {
				DictionaryContent dic = dictionaryContentService.get(content.getId());
				if(dic != null) {
					DictionaryType type = dictionaryTypeService.get(dic.getTypeId());
					dictionaryContentService.delete(dic);  
					redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.dict, type.getCode(), this.dictionaryContentService.findByTypeCode(type.getId()));
				}
			}
		}
		r.success(); 
		return crudError(r);
	}
	
	/**
	 * 进入数据字典管理页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/sys/dict/index";
	}
	/**
	 * 进入数据字典编辑页面
	 * @return
	 */
	@RequestMapping("/editType")
	public String editType(String id) {
		DictionaryType type = new DictionaryType();
		if(StringUtil.notBlank(id)) {
			type = dictionaryTypeService.get(id);
		}
		getRequest().setAttribute("v", type);  
		return "/sys/dict/editType";
	}
	/**
	 * 进入数据字典子项编辑页面
	 * @return
	 */
	@RequestMapping("/editContent")
	public String editContent(String id) {
		DictionaryContent content = new DictionaryContent();
		if(StringUtil.notBlank(id)) {
			content = dictionaryContentService.get(id);
		}else {
			content.setTypeId(getRequestParam("typeId"));  
		}
		getRequest().setAttribute("v", content);  
		return "/sys/dict/editContent";
	}
}
