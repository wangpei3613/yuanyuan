package com.sensebling.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.common.util.SysDataUtil;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.context.RedisManager;
import com.sensebling.system.entity.Area;
import com.sensebling.system.entity.Department;
import com.sensebling.system.entity.DictionaryContent;
import com.sensebling.system.entity.Employee;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.AreaSvc;
import com.sensebling.system.service.DeptSvc;
import com.sensebling.system.service.EmpSvc;
import com.sensebling.system.service.UserSvc;
/**
 * 获取公共数据工具
 * @author 
 * @time 2014-11-3
 */
@Controller
@RequestMapping("/sys")
public class SysDataCtrl extends BasicsCtrl{
	@Resource
	private UserSvc userService;
	@Resource
	private EmpSvc employeeService;
	@Resource
	private DeptSvc departmentService;
	@Resource
	private AreaSvc areaService;
	@Autowired
	private RedisManager redisManager;
	/**
	 * 公共方法，获取数据字典数据信息<br>
	 * 用于table行编辑事件
	 * @param typeCode 传入的是数据字典类型编码，中间用“,”分开
	 * @return
	 */
	@RequestMapping(value="/sysDataTable")
	public @ResponseBody Map<String,String> selSysDatTab(String typeCode){
		
		Map<String,String> map= new HashMap<String, String>();
		String[] typeCodes = typeCode.split(",");
		for(String type:typeCodes){
			List<DictionaryContent> datas=SysDataUtil.getDictionary(type);
			StringBuffer str = new StringBuffer();
			if (datas!=null && datas.size()>0) {
				for(DictionaryContent content:datas){
					str.append(content.getDictionaryCode()+":"+content.getDictionaryName()+";");
					
				}
			}
			if(str.length() >0){
				str.deleteCharAt(str.length() - 1);
			}
			
			map.put("sel_"+type, str.toString());
		}
		return map;
	}
	
	
	@RequestMapping(value="/getDictionary")
	public @ResponseBody List<DictionaryContent> queryDic(String typeCode){
		return SysDataUtil.getDictionary(typeCode);
	}
	
	@RequestMapping(value="/sysDataForm")
	public @ResponseBody Map<String,List<DictionaryContent>> selSysDatForm(String typeCode){
		
		Map<String,List<DictionaryContent>> map= new HashMap<String, List<DictionaryContent>>();
		String[] typeCodes = typeCode.split(",");
		for(String type:typeCodes){
			List<DictionaryContent> datas=SysDataUtil.getDictionary(type);

			map.put("sel_"+type, datas);
		}
		return map;
	}
	/**
	 * 查询系统所有用户,用于表格的用户id替换
	 * @return
	 */
	@RequestMapping(value="/sysDataUser")
	public @ResponseBody Map<String,String> selSysDatUser(){
		Map<String,String> map= new HashMap<String, String>();
		List<User> list=userService.findAll();
		for(User u:list)
			map.put(u.getId(), u.getUserName());
		return map;
	}
	/**
	 * 查询系统所有员工,用于表格的员工id替换
	 * @return
	 */
	@RequestMapping(value="/sysDataEmployee")
	public @ResponseBody Map<String,String> selSysDatEmp(){
		Map<String,String> map= new HashMap<String, String>();
		List<Employee> list=employeeService.findAll();
		for(Employee u:list)
			map.put(u.getId(), u.getEmpName());
		return map;
	}
	/**
	 * 查询系统所有部门,用于表格的部门id替换
	 * @return
	 */
	@RequestMapping(value="/sysDataDepart")
	public @ResponseBody Map<String,String> selSysDept(){
		Map<String,String> map= new HashMap<String, String>();
		List<Department> list=departmentService.findAll();
		for(Department d:list)
			map.put(d.getId(), d.getFullName());
		return map;
	}
	/**
	 * 查询系统所有部门,用于表格的部门id替换
	 * @return
	 */
	@RequestMapping(value="/sysDataArea")
	public @ResponseBody Map<String,String> selSysArea(){
		Map<String,String> map= new HashMap<String, String>();
		List<Area> list=areaService.findAll();
		for(Area a:list)
			map.put(a.getId(), a.getAreaname());
		return map;
	}
	
	/**
	 * 查询字典表，用于下拉列表
	 * isSelect:是否显示请选择
	 * @return
	 */
	@RequestMapping(value="/sysData")
	public @ResponseBody List<Map<String,String>> selSysDat(String type,String isSelect){
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<DictionaryContent> datas=SysDataUtil.getDictionary(type);
		if(StringUtil.notBlank(isSelect)) {
			Map<String,String> map = new HashMap<String, String>();
			map.put("text", "");
			map.put("value", "");
			temp.add(map);
		}
		if(datas==null)
			return temp;
		for(DictionaryContent content:datas){
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", content.getDictionaryName());
			map.put("value", content.getDictionaryCode());
			temp.add(map);
			
		}
		return temp;
	}
	/**
	 * 返回值使用
	 * @param str
	 * @return
	 */
	@RequestMapping(value="/back")
	@ResponseBody
	public String getBack(String str){
		return str;
	}
	@RequestMapping(value="/export")
	public String export(){
		getRequest().setAttribute("html", getRequestParam("html"));
		getRequest().setAttribute("style", getRequestParam("style"));
		getRequest().setAttribute("name", getRequestParam("name"));
		getRequest().setAttribute("type", getRequestParam("type"));
		//getResponse().setHeader("X-XSS-Protection", "0");
		//getResponse().setHeader("Content-Security-Policy", "default-src");
		return "syhouse/common/export/export";   
	}
	@RequestMapping(value="/getExtDictionary")
	@ResponseBody
	public Result getExtDictionary(String typeCode){
		Result r = new Result();
		List<DictionaryContent> datas=SysDataUtil.getDictionary(typeCode);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		if(datas!=null && datas.size()>0) {
			for(DictionaryContent d:datas) {
				map = new HashMap<String, Object>();
				map.put("code", d.getDictionaryCode());
				map.put("name", d.getDictionaryName());
				list.add(map);
			}
		}
		r.setData(list);
		r.success();
		return crudError(r);
	}
	/**
	 * 查询数据字典 给app使用
	 * @param dictCodes
	 * @return
	 */
	@RequestMapping(value="/getDictApp")
	@ResponseBody
	public Result getDictApp(String dictCodes){
		Result r = new Result();
		Map<String, List<Map<String, Object>>> dictData = new HashMap<String, List<Map<String, Object>>>();
		if(StringUtil.notBlank(dictCodes)) {
			List<Map<String, Object>> list = null;
			Map<String, Object> map = null;
			for(String typeCode:dictCodes.split(",")) {
				List<DictionaryContent> datas=SysDataUtil.getDictionary(typeCode);
				map = new HashMap<String, Object>();
				list = new ArrayList<Map<String, Object>>();
				if(datas!=null && datas.size()>0) {
					for(DictionaryContent d:datas) {
						map = new HashMap<String, Object>();
						map.put("code", d.getDictionaryCode());
						map.put("name", d.getDictionaryName());
						list.add(map);
					}
				}
				dictData.put(typeCode, list);
			}
		}
		r.setData(dictData);
		r.success();
		return crudError(r);
	}
	/**
	 * 转发返回专用请求
	 * @param r
	 * @return
	 */
	@RequestMapping("/crud")  
	@ResponseBody
	public Result crud(Result result) {
		return crudError(result);
	}
	@RequestMapping("/getSystemConfigParam")  
	@ResponseBody
	public String getSystemConfigParam(String code) {
		return BasicsFinal.getParamVal(code);
	}
	
	/**
	 * 获得请求token
	 * 
	 * @return
	 * 2019年4月12日-上午9:32:55
	 * YF
	 */
	@RequestMapping(value="/getToken")
	@ResponseBody
	public Result getToken(HttpServletRequest request){
		Result r = new Result();
		String token = StringUtil.creatToken();
		request.getSession(false).setAttribute("token", token);
		r.setData(token);
		r.success();
		return crudError(r);
	}
	/**
	 * 获取一次性令牌
	 * @return
	 */
	@RequestMapping("/getDisposableToken")
	@ResponseBody
	@AuthIgnore
	public Result getDisposableToken(){
		Result r = new Result();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.disposableToken, uuid, "1", ProtocolConstant.disposableTokenTime); 
		r.setData(uuid);
		r.success();
		return crudError(r);
	}
	
}
