package com.sensebling.core.attach.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.SFTPUtils;
import com.sensebling.common.util.StringUtil;
import com.sensebling.core.attach.entity.CoreAttach;
import com.sensebling.core.attach.service.CoreAttachSvc;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/sen/core/attach")
public class CoreAttachCtrl extends BasicsCtrl{
	@Resource
	private CoreAttachSvc coreAttachSvc;
	/**
	 * KindEditor上传附件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAttachKindEditor")
	@ResponseBody
	@AuthIgnore
	public Object uploadAttachKindEditor(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//文件保存目录路径
		String savePath = BasicsFinal.getParamVal("file.upload.path") + "/"; 
		// 文件保存目录URL
		String saveUrl = BasicsFinal.getParamVal("file.virtual.route") + "/";
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		//最大文件大小
		long maxSize = 10*1024*1024;
		response.setContentType("text/html; charset=UTF-8");
		if (!ServletFileUpload.isMultipartContent(request)) {
		    return getError("请选择文件。");
		}
		// 检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
		    return getError("上传目录不存在。");
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
		    return getError("上传目录没有写权限。");
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
		    dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
		    return getError("目录名不正确。");
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
		    saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
		    dirFile.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> item = multipartRequest.getFileNames();
		while (item.hasNext()) {
		    String fileName = (String) item.next();
		    MultipartFile file = multipartRequest.getFile(fileName);
		    // 检查文件大小
		    if (file.getSize() > maxSize) {
		        return getError("上传文件大小超过限制。");
		    }
		    // 检查扩展名
		    String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
		    if (!Arrays. asList(extMap.get(dirName).split(",")).contains(fileExt)) {
		        return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
		    }
		    String newFileName = UUID.randomUUID() + "." + fileExt;
		    try {
		    		File uploadedFile = new File(savePath, newFileName);
		    		file.transferTo(uploadedFile);
		    } catch (Exception e) {
		        return getError("上传文件失败。");
		    }
		    Map<String, Object> msg = new HashMap<String, Object>();
		    msg.put("error", 0);
		    msg.put("url", saveUrl + newFileName);
		    return JsonUtil.entityToJSON(msg);
		}
        return null;
	}
	private Map<String, Object> getError(String message) {
         Map<String, Object> msg = new HashMap<String, Object>();
         msg.put("error", 1);
		 msg.put("message", message);
         return msg;
     }
	/**
	 * 查询附件列表
	 * @param dicttype
	 * @param applyid
	 * @return
	 */
	@RequestMapping("/getAttach")
	@ResponseBody
	public Result getAttach(String dicttype, String applyid, String dictcode) {
		Result r = new Result();
		if(StringUtil.notBlank(applyid)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("dicttype", dicttype);
			qp.addParamter("applyid", applyid);
			qp.addParamter("dictcode", dictcode);
			qp.setSortField("addtime");  
			r.setData(coreAttachSvc.findAll(qp));
		}
		r.success();
		return crudError(r); 
	}
	
	/**
	 * 从文件服务器获取文件 sftp
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getImg")
	@AuthIgnore
	public String getImg(String path,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ServletOutputStream out = null;  
		try {
			if(StringUtil.notBlank(path)) {
				if(path.contains(".html")) {
					response.setHeader("content-type", "text/html;charset=gbk");
				}
				path = BasicsFinal.getParamVal("file.upload.sftppath") + path; 
				OutputStream os = SFTPUtils.downloadFiles(path);
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
		        baos = (ByteArrayOutputStream)os;
				out = response.getOutputStream();
				out.write(baos.toByteArray());  
				out.flush();
			}
		} catch (Exception e) {
			throw e;
		}finally{
            try {
				if(null!=out){
					out.close();
				}
			} catch (IOException e) {
				throw e;
			}
        }
		return null;
	}
	
	
	/**
	 * 上传附件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAttach")
	@ResponseBody
	@AuthIgnore
	public Object uploadAttach(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//文件保存目录路径
		String savePath = BasicsFinal.getParamVal("file.upload.path") + "/"; 
		// 文件保存目录URL
		String saveUrl = "/";
		//是否是正式环境
		boolean isReal = "true".equalsIgnoreCase(BasicsFinal.getParamVal("environment.isformal"));
		if(isReal) {
			savePath = BasicsFinal.getParamVal("file.upload.sftppath") +"/";
		}
		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		//最大文件大小
		long maxSize = 10*1024*1024;
		response.setContentType("text/html; charset=UTF-8");
		if (!ServletFileUpload.isMultipartContent(request)) {
		    return getError("请选择文件。");
		}
		if(!isReal) {  
			// 检查目录
		    File uploadDir = new File(savePath);
		    if (!uploadDir.isDirectory()) {
		        return getError("上传目录不存在。");
		    }
		    // 检查目录写权限
		    if (!uploadDir.canWrite()) {
		        return getError("上传目录没有写权限。");
		    }
		}
		String dirName = request.getParameter("dir");
		if (dirName == null) {
		    dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
		    return getError("目录名不正确。");
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		if(!isReal) {  
				File saveDirFile = new File(savePath);
		    if (!saveDirFile.exists()) {
		        saveDirFile.mkdirs();
		    }
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		if(!isReal) {  
				File dirFile = new File(savePath);
		    if (!dirFile.exists()) {
		        dirFile.mkdirs();
		    }
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> item = multipartRequest.getFileNames();
		while (item.hasNext()) {
		    String fileName = (String) item.next();
		    MultipartFile file = multipartRequest.getFile(fileName);
		    // 检查文件大小
		    if (file.getSize() > maxSize) {
		        return getError("上传文件大小超过限制。");
		    }
		    // 检查扩展名
		    String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
		    if (!Arrays. asList(extMap.get(dirName).split(",")).contains(fileExt)) {
		        return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
		    }
		    String newFileName = UUID.randomUUID() + "." + fileExt;
		    File uploadedFile;
		    try {
		    		if(isReal){
		        		SFTPUtils.uploadInptStrems(savePath, newFileName, file.getInputStream());
		    		}else {
		    			uploadedFile = new File(savePath, newFileName);
		        		file.transferTo(uploadedFile);
		    		}
		    } catch (Exception e) {
		    		DebugOut.logInfo(TAG, e);   
		        return getError("上传文件失败。");
		    }
		    CoreAttach attach = new CoreAttach();
		    attach.setAddtime(DateUtil.getStringDate());
		    User user = getUser();
		    if(null==user)
		    attach.setAdduser("");
		    else 
		    	attach.setAdduser(user.getId());
		    String id=UUID.randomUUID().toString();
		    attach.setClintname(fileName);
		    attach.setFilepath(saveUrl + newFileName);
		    attach.setFilesize(StringUtil.formatDouble_str(file.getSize()/1024d));
		    attach.setFiletype(fileExt);
		    attach.setServername(newFileName);
		    attach.setId(id);
		    coreAttachSvc.save(attach);

		    Map<String, Object> msg = new HashMap<String, Object>();
		    msg.put("error", 0);
		    if(isReal){
		        	String serviceName = request.getServerName();
		        	int port = request.getServerPort();
		        	String urls =  "http://"+serviceName+":"+port+request.getContextPath()+"/sen/core/attach/getImg?path="+ saveUrl+ newFileName;
		        	msg.put("url",urls);
		    }else{
		    		msg.put("url", BasicsFinal.getParamVal("file.virtual.route")+ saveUrl + newFileName);
		    }
		    msg.put("attach", attach);
		    return JsonUtil.entityToJSON(msg);  
		}
        return null;
	}
	/**
	 * APP上传附件
	 */
	@RequestMapping("/uploadAttachApp")
	@ResponseBody
	public Result uploadAttachApp(String filename, String base64, String coordinates, String address) {
		Result r = new Result();
		if(StringUtil.notBlank(filename, base64)) {
			//是否是正式环境
	        boolean isReal = "true".equalsIgnoreCase(BasicsFinal.getParamVal("environment.isformal"));
			String savePath = BasicsFinal.getParamVal("file.upload.path") + "/" + "image"; 
			if(isReal) {
				savePath = BasicsFinal.getParamVal("file.upload.sftppath") + "/" + "image"; 
			}
			String serverPath = "/" + "image";
			String date = DateUtil.getStringDateShort();
			savePath += "/" + date + "/";
			serverPath += "/" + date + "/";
			if(!isReal) {
				File temp = new File(savePath);
				if(!temp.exists()) {
					temp.mkdirs();
				}
			}
			String fileExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
			String newFileName = UUID.randomUUID() + "." + fileExt;
			BASE64Decoder decoder = new BASE64Decoder();
			int start = 22;
			if(fileExt.equals("jpg")) {
				start = 23;
			}
			try {
				byte[] b = decoder.decodeBuffer(base64.substring(start));    
				for(int i=0;i<b.length;++i){  
				    if(b[i]<0){
				        b[i]+=256;  
				    }  
				}  
				if(isReal) {
					SFTPUtils.uploadInptStrems(savePath, newFileName, new ByteArrayInputStream(b));
				}else {
					File file = new File(savePath + "/" + newFileName);
					OutputStream out = new FileOutputStream(file);      
					out.write(b);  
					out.flush();  
					out.close();
				}
				
				//数据入库
				CoreAttach attach = new CoreAttach();
	            attach.setAddtime(DateUtil.getStringDate());
	            attach.setAdduser(getUser().getId());
	            attach.setClintname(filename);
	            attach.setFilepath(serverPath + newFileName);
	            attach.setFilesize(StringUtil.formatDouble_str(base64.length()/1024d));
	            attach.setFiletype(fileExt);
	            attach.setServername(newFileName);
	            //attach.setMd5(MD5FileUtil.getFileMD5String(file));
	            attach.setAddress(address);
	            attach.setCoordinates(coordinates);  
	            coreAttachSvc.save(attach);
	            r.setData(attach);
	            r.success();
			} catch (Exception e) {
				r.setError("图片保存失败");
				DebugOut.logInfo("图片保存失败", e);    
			} 
		}
		return crudError(r);
	}
	
	@RequestMapping(value="/toHighMeter")
	@AuthIgnore
	public String toHighMeter(String lastPath,Model model)throws Exception{
//		timeService.addExePro();
		return "/common/picture";
	}
	/**
	 * 保存影像资料
	 * @param applyid
	 * @param dicttype
	 * @param dictcode
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/saveAttach")
	@ResponseBody
	public Result saveAttach(String applyid, String dicttype, String dictcode, String ids, String checkAction) {
		return crudError(coreAttachSvc.saveAttach(applyid, dicttype, dictcode, ids,checkAction));
	}
	/**
	 * 删除影像资料
	 * @return
	 */
	@RequestMapping(value="/delAttach")
	@ResponseBody
	public Result delAttach(String applyid, String ids, String checkAction) {
		return crudError(coreAttachSvc.delAttach(applyid, ids, checkAction));
	}
	public static void main(String[] args) throws Exception{
		String path = "/Users/llmke/Downloads/img/24b34cd3a1287da588d2c9ac7dfeab0d.jpg";
		try(
				InputStream in = new FileInputStream(new File(path));
			){
				byte[] data = new byte[in.available()];
	            in.read(data);
	            System.out.println(Base64.getEncoder().encodeToString(data));
			}
	}
}
