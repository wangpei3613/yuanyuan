package com.sensebling.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.EmpSvc;
public class BasicsCtrl extends MultiActionController
{
	ObjectMapper objMapper=new ObjectMapper();
	protected DebugOut log=new DebugOut(this.getClass());
	protected boolean isApp = false;
	public String TAG;
	@Resource
	private EmpSvc employeeService;
	/**
	 * 基础控制方法,所有继承BaseController的控制器在访问方法时都先执行此方法,
	 * 此方法主要处理列表查询的参数,部门视野控制,区域视野控制和分页处理
	 * 其中:视野控制的规则[当dc或者ac为空时不做处理,不为空时:从params中取dc或者ac的表示的参数值,当取出的值为空时则放入当前用户的部门视野id或者区域视野id]
	 * @param params 查询参数标准的json字符串,此字符串会转换为查询参数map放入QueryParameter中
	 * @param page 分页-当前页
	 * @param rows 分页-每页记录数
	 * @param sidx 分页-排序字段
	 * @param sord 分页-排序规则
	 * @param dc 视野控制-部门视野的参数字段名(若为空则不使用系统的部门视野控制)
	 * @param ac 视野控制-区域视野的参数字段名(若为空则不使用系统的区域视野控制)
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ModelAttribute
	public void init(String params,String page,String rows,String sidx,String sord,String dc,String ac) throws Exception
	{
		QueryParameter qp=new QueryParameter();
		if(params!=null)
		{
			Map<String, Object>map = objMapper.readValue(params, Map.class);
			for(String key:map.keySet())
			{
				Object val=map.get(key);
				//若查询条件第一个字符为@则作为查询的条件表达式
				if(key.indexOf("@")==0)
				{
					QueryCondition qc=null;
					try{
						qc=QueryCondition.valueOf(String.valueOf(val));
					}catch (Exception e) {
						qc=QueryCondition.equal;
						log.errPrint("查询参数["+key+"]使用的表达式["+val+"]不在表达式枚举的范围内,已作为equal默认值!", null);
					}
					qp.addCondition(key=key.replaceFirst("@", ""),qc);
				}
				else
					qp.addParamter(key, val);
			}
		}
		Pager p=null;
		String pageIndex=page;
		String pageSize=rows;
		String sortField=sidx;
		String sortOrder=sord;
		if(pageIndex!=null&&!pageIndex.equals("0")&&StringUtil.isBlank(pageSize))
			p=new Pager(Integer.parseInt(pageIndex));
		else if(StringUtil.notBlank(pageIndex)&&!pageIndex.equals("0")&&StringUtil.notBlank(pageSize))
			p=new Pager(Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
		if(p!=null)
			qp.setPager(p, sortField, sortOrder);
		else
		{
			qp.setSortField(sortField);
			qp.setSortOrder(sortOrder);
		}
		getRequest().setAttribute("@qp", qp);
		if("1".equals(getRequest().getParameter("_app"))) {
			isApp = true;
		}else {
			isApp = false;
		}
	}
	/**
	 * 获取request对象
	 * @return request
	 */
	protected HttpServletRequest getRequest()
	{
		return HttpReqtRespContext.getRequest();  
	}
	/**
	 * 获取response对象
	 * @return response
	 */
	protected HttpServletResponse getResponse()
	{
		return HttpReqtRespContext.getResponse();
	}
	/**
	 * 获取session对象
	 * @return session
	 */
	protected HttpSession getSession()
	{
		return getRequest().getSession();
	}
	/**
	 * 获取当前登录的用户对象
	 * @return User
	 */
	protected User getUser()
	{
		return HttpReqtRespContext.getUser();
	}
	
	/**
	 * 获取访问请求的QueryParameter
	 * @return QueryParameter
	 */
	protected QueryParameter getQueryParameter()
	{
		return (QueryParameter)getRequest().getAttribute("@qp");
	}
	/**
	 * 获取request请求的参数
	 * @param paramName 参数名称
	 * @return 参数值
	 */
	protected String getRequestParam(String paramName)
	{
		return getRequest().getParameter(paramName);
	}
	/**
	 * 输出文件
	 * @param fileName 响应的文件名称(输出到客户端时显示)
	 * @param filePath 源文件的完整路径
	 */
	protected void renderFile(String fileName,String filePath) throws Exception
	{
		String fileName_old=fileName;
		try
		{
			fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");//正对中文文件名转码
		} catch (UnsupportedEncodingException e)
		{
			fileName="download_file.file";
		}
		HttpServletResponse response=getResponse();
		response.reset();
		response.setContentType("application/octet-stream");//设定相应格式  
		response.setHeader("Content-disposition","attachment;filename="+fileName);//设置下载文件名
		/*读取文件到内存中*/
		File file=new File(filePath);
		InputStream input=new BufferedInputStream(new FileInputStream(file));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		try
		{
			byte[] buffer=new byte[204800];//每200kb读取一次
			/*输出文件*/
			OutputStream toClient=new BufferedOutputStream(response.getOutputStream());
			int bytesRead;
			//读取数据到输出流中
			while (-1 != (bytesRead=input.read(buffer, 0, buffer.length)))
				toClient.write(buffer, 0, bytesRead);
			input.close();//关闭输入流
			toClient.flush();
			toClient.close();
			logger.info("文件下载成功:"+fileName_old);
		}catch (Exception e) {//针对取消下载的异常不处理
			logger.info("文件下载失败["+e.toString()+"]:"+fileName_old);
		}
		return;
	}
	protected Result crudError(Result r) {
		if(!"0".equals(r.getRes())) {
			getResponse().setStatus(450);  
		}
		return r;
	}
	protected Result r(Result r) {
		if(!"0".equals(r.getRes())) {
			getResponse().setStatus(450);  
		}
		return r;
	}
	
}
