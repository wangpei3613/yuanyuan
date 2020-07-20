package com.sensebling.system.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.entity.AppVersion;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.AppVersionSvc;

/**
 * app上传管理
 * @author YY
 * 2018年7月10日-上午11:00:15
 */
@Controller
@RequestMapping("/system/appversion")
public class AppVersionCtrl extends BasicsCtrl{

	@Resource
	private AppVersionSvc appVersionSvc;
	
	/**
	 * 打开页面
	 * @param lastPath
	 * @param model
	 * @return
	 * 2018年7月10日-上午11:15:46
	 * YY
	 */
	@RequestMapping(value="/index")
	public String toIndex(String lastPath,Model model){
		return "/sys/appVersion/index";
	}
	
	@RequestMapping(value="/toAdd")
	public String toAdd(String id){
		id = StringUtil.sNull(id);
		AppVersion d = new AppVersion();
		if(!"".equals(id)){
			 d = appVersionSvc.get(id);
		}
		getRequest().setAttribute("d", d);
		return "/sys/appVersion/edit";
	}
	
	/**
	 * 查询
	 * @return
	 * 2018年7月10日-上午11:24:25
	 * YY
	 */
	@RequestMapping(value="/select")
	@ResponseBody
	public Result select()
	{
		Result r = new Result();
		r.setData(appVersionSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	
	@RequestMapping(value="/openAdd")
	@ResponseBody
	@ModuleAuth({"appVersion:openAdd","appVersion:openEdit"})
	@DisposableToken
	public Result addAppVersion(AppVersion d) throws Exception
	{
		Result r = new Result();
		appVersionSvc.addAppVersion(d,getUser());
		r.success();
		return crudError(r);
	}
	
	/**
	 * 删除
	 * @param u
	 * @return
	 */
	@RequestMapping(value="/delAppVersion")
	@ResponseBody
	@ModuleAuth("appVersion:remove")
	@DisposableToken
	public Result delUser(String data)
	{
		Result r = new Result();
		appVersionSvc.delete(data);
		r.success();
		return crudError(r);
	}
	
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Result upload(@RequestParam(value = "file", required = true) CommonsMultipartFile file,String filenam,
		 HttpServletRequest request) throws Exception {
		System.out.println("fileName--->" + file.getOriginalFilename());
		Result r = new Result();
		FileOutputStream os = null;
		InputStream in=null;
		if (!file.isEmpty()) {
			try {
				String path = BasicsFinal.getParamVal("appuploadPath");
				File file1 = new File(path);
				StringUtil.deleteFile(file1);
				if(!file1.exists())
				{
					file1.mkdirs();
				}
				
				os = new FileOutputStream(path+"/tz"+filenam+".apk");
				in = file.getInputStream();
				byte[] buffer=new byte[1024];
				int b = 0;
				while ((b = in.read(buffer)) != -1) {
					os.write(buffer, 0, b);
				}
				os.flush();
				os.close();
				in.close();
				r.success();
			} catch (Exception e) {
				DebugOut.logInfo(TAG, e);   
			}
			finally{
				if(null!=os){
					os.close();
				}
				if(null!= in){
					in.close();
				}
			}
		}
		return  crudError(r);
	}
	
	/**
	 * 
	 * @return
	 * 2018年7月12日-下午7:48:33
	 * YY
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryNewVersion")
	@ResponseBody
	public Result queryNewVersion() throws Exception
	{
		Result r = new Result();
		AppVersion app = appVersionSvc.getListByTime(1);
		r.setData(app);
		r.success();
		return crudError(r);
	}
}
