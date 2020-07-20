package com.sensebling.common.controller;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sensebling.common.service.ExportExcelSvc;
import com.sensebling.common.util.ExportExcelUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
@Controller
@RequestMapping("/excelExport")
public class ExportExcelCtrl extends BasicsCtrl{

	@Resource
	private ExportExcelSvc exportExcelSvc;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export")
    public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
		//文件名
		String excelName = String.valueOf(request.getParameter("excelName"));
		if(StringUtil.isBlank(excelName)) {
			excelName = "导出.xls";
		}else {
			excelName = excelName+".xls";
		}
		
		//文件头部
		JSONArray json =JsonUtil.jsonEncoding(request.getParameter("columns")); 
		List<Map<String,Object>> title = new ArrayList<Map<String,Object>>();
		for(int i=0;i<json.length();i++) {
			JSONObject obj = json.getJSONObject(i);
			if(!(obj.has("hidden")&&obj.getBoolean("hidden")) && !(obj.has("exportHide")&&obj.getBoolean("exportHide"))) {
				Map<String,Object> temp = new HashMap<String, Object>();
				temp.put("title", obj.getString("title"));
				temp.put("boxWidth", String.valueOf(obj.get("boxWidth")));
				if(obj.has("align")) {
					temp.put("align", obj.getString("align"));
				}else {
					temp.put("align", "");
				}
				title.add(temp);
			}
		}
		
		//数据
		List<List<String>> dataList = new ArrayList<List<String>>();
		String serviceName = String.valueOf(request.getParameter("serviceName"));
		String methodName = String.valueOf(request.getParameter("methodName"));
		Object serBean = exportExcelSvc.getBean(serviceName);
		//调用方法：方法名+参数类型
		Method m = serBean.getClass().getMethod(methodName, QueryParameter.class);
		//接受service的方法返回值，
		List objList = (List) m.invoke(serBean, getQueryParameter());
		if(null != objList && objList.size()>0) {
			for(int i=0;i<objList.size();i++) {
				List<String> oList = new ArrayList<String>();
				JSONObject v = JsonUtil.stringToJSON(JsonUtil.entityToJSON(objList.get(i)));
				for(int j=0;j<json.length();j++) {
					JSONObject obj = json.getJSONObject(j);
					if(!(obj.has("hidden")&&obj.getBoolean("hidden")) && !(obj.has("exportHide")&&obj.getBoolean("exportHide"))) {
						String value = "";
						if(v.has(obj.getString("field"))) {
							value = String.valueOf(v.get(obj.getString("field")));
							if(obj.has("dictData")) {
								JSONArray dictData = obj.getJSONArray("dictData");
								if(dictData!=null && dictData.length()>0) {
									for(int k=0;k<dictData.length();k++) {
										if(value.equals(dictData.getJSONObject(k).getString("code"))) {
											value = dictData.getJSONObject(k).getString("name");
										}
									}
								}
							}
						}
						oList.add(value);
					}
				}
				dataList.add(oList);
			}
		}
		HSSFWorkbook wb = ExportExcelUtil.getHSSFWorkbook(excelName, title,dataList, null);
		response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(excelName.getBytes(),"ISO8859-1"));
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
	}
}
