package com.sensebling.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Area;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.AreaSvc;
/**
 * 系统操作相关的controller
 * 区域管理
 * @author 
 * 2014年12月25日
 *  
 */
@Controller
@RequestMapping("/system/area")
public class AreaCtrl extends BasicsCtrl {
	@Resource
	private AreaSvc areaService;
//	@Resource
//	private UserAreaService userAreaService;
//	
	
	/**
	 * 根据jsonType  树形展示地区 或者下拉显示数据
	 */
	@RequestMapping(value="/combobox")
	@ResponseBody
	public List<Map<String, Object>> areaCombobox(String jsonType,String isControl)
	{
		//是否启用系统用户分配的区域权限,在某些特定的业务模块可能需要脱离区域权限的控制标注isControl=0
		//即不启用权限限制
		isControl="0".equals(isControl)?isControl:"1";
		QueryParameter qp=new QueryParameter();
		List<Area> list= areaService.findAll(qp);
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		for(Area vo:list)
		{
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("text", vo.getAreaname());
			map.put("id", vo.getId());
			map.put("value", vo.getId());
			map.put("key", vo.getCode());
			map.put("pid", vo.getParentcode());
			temp.add(map);
		}
		if("combobox_select".equals(jsonType))
		{
			Map<String,Object> m_t=new HashMap<String, Object>();
			m_t.put("text", "--请选择--");
			m_t.put("value", "");
			temp.add(0, m_t);
			return temp;
		}
		else if("combobox_tree".equals(jsonType))
			return EasyTreeUtil.ToEasyTree(temp, "key", "pid");
		else
			return null;
	}
	/**
	 * 新增区域基础信息
	 * @return 1:成功,其他:失败3
	 */
	@RequestMapping(value="/allotRoleArea",produces = {"application/json"},method=RequestMethod.POST)
	@ResponseBody
	public String addArea(String saveJson) throws Exception
	{
		String str="";
		str = "";
		JSONArray json =JsonUtil.jsonEncoding(saveJson); 
		for(int i=0;i<json.length();i++){
			JSONObject obj = json.getJSONObject(i);
			String crud = obj.getString("crud");
			obj.remove("crud");
			Area area =(Area)JsonUtil.jsonToEntity(obj.toString(), new Area().getClass());
			
			//修改
			if("1".equals(crud)){				

				areaService.update(area);
				str="1";
			//新增
			}else if("2".equals(crud)){

				areaService.save(area);
				str="1";
			//删除
			}else if("3".equals(crud)){
				areaService.delete(area.getId());
				str="1";
			}			
		}
		return  str;
	}

	
	/**
	 * 查询关联或不关联角色的用户信息
	 */
	@RequestMapping(value="/selectAreaInfo")
	@ResponseBody
	public Pager getAreaInfo()
	{
		Pager p=null;
		QueryParameter qp=getQueryParameter();
		p = areaService.findPage(qp);
		return p;
	}
	/**
	 * 
	 */
	@RequestMapping(value="/getTacheByAreacode")
	public String getTacheByFlowCode(String parentcode,HttpServletResponse response) throws IOException{
		QueryParameter qp=new QueryParameter();
		qp.addParamter("area.id", parentcode);
		qp.setSortField("id");
		qp.setSortOrder("asc");
		List<Area> list=areaService.findAll(qp);
		StringBuffer str=new StringBuffer();
		str.append("<option value='0' selected>--请选择--</option>");
		for (int r = 0; r < list.size(); r++) {
			Area tacheVo = (Area) list.get(r);
			str.append("<option value='"+ tacheVo.getId() + "'>"+ tacheVo.getParentcode()+ "</option>");
		}
		response.getWriter().print(str.toString());
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/sysDataArea")
	public @ResponseBody Map<String,String> selectSysData(){
		Map<String,String> map= new HashMap<String, String>();
		List<Area> list=areaService.findAll();
		map.put("", "-请选择-");
		for(Area ms:list)
			map.put(ms.getCode(), ms.getAreaname());
		return map;
		
	}

	
	/**
	 * 
	 */
	@RequestMapping(value="/selectList")
	@ResponseBody
	public Pager getList()
	{
		QueryParameter qp=getQueryParameter();
		String parentcode=(String) qp.getParam("parentcode");
		if(StringUtil.isBlank(parentcode)){
			return new Pager();
		}
		//qp.addCondition("createDate", QueryCondition.like_start);
		//qp.addCondition("department.id", QueryCondition.in);
		//qp.addCondition("area.id", QueryCondition.in);
		Pager p = areaService.findPage(qp);
		return p;
	}
	/**
	 * 查询
	 * @param area
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/listContent")
	public @ResponseBody Pager getListContent(Area area,String typeId){
		Map<String,Object[]> map = new HashMap<String, Object[]>();
		map.put("code", new Object[]{BasicsFinal.LIKE,StringUtil.notBlank(area.getCode())?area.getCode().trim():""});
		map.put("areaname", new Object[]{BasicsFinal.LIKE,StringUtil.notBlank(area.getAreaname())?area.getAreaname().trim():""});
		map.put("parentcode", new Object[]{BasicsFinal.EQ,area.getParentcode()});
		
		map.put(BasicsFinal.ORDER_BY, new Object[]{this.getRequest().getParameter("sidx"),this.getRequest().getParameter("sord")});
		return this.areaService.findByCriteria(map, this.getQueryParameter().getPager());
	}
	/**
	 * 判断区域编码是否重复
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/checkRepeat")
	@ResponseBody
	public String checkByCode(String code){
		QueryParameter qp = new QueryParameter();
		qp.addParamter("code", code);
		qp.addCondition("code",QueryCondition.equal);
		List<Area> list = areaService.findAll(qp);
		return list == null ? "0":(list.size()+"");
	}
}
