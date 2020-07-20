package com.sensebling.archive.controller;


import com.sensebling.archive.entity.ArchReelFileMaterial;
import com.sensebling.archive.service.ArchReelFileMaterialSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.*;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/arch/reel/file/material")
public class ArchReelFileMaterialCtrl extends BasicsCtrl{
	@Resource
	private ArchReelFileMaterialSvc archReelFileMaterialSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/reel/file/material/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archReelFileMaterialSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id) {
		ArchReelFileMaterial v = new ArchReelFileMaterial();
		if(StringUtil.notBlank(id)) {
			v = archReelFileMaterialSvc.get(id);
		}
		getRequest().setAttribute("v", v);
		return "/arch/reel/file/material/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchReelFileMaterial v){
		Result r = new Result();
		if(StringUtil.notBlank(v.getId())) {
			archReelFileMaterialSvc.update(v);
		}else {
			archReelFileMaterialSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		archReelFileMaterialSvc.delete(id);
		r.success();
		return crudError(r);
	}
	@RequestMapping(value="/toAttachIndex")
	@AuthIgnore
	public String toAttachIndex(String fileid)throws Exception{
		getRequest().setAttribute("applyid",fileid);
		return "/core/attach/uploadFile";
	}

	/**
	 * 上传附件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadAttachArchive")
	@ResponseBody
	@AuthIgnore
	public Object uploadAttachArchive(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String applyid= request.getParameter("applyid");
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
		MultiValueMap<String,MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
		List<MultipartFile> fileSet = new LinkedList<>();
		for(Map.Entry<String, List<MultipartFile>> temp : multiFileMap.entrySet()){
			fileSet = temp.getValue();
		}
		for(MultipartFile file : fileSet){
			// 检查文件大小
			if (file.getSize() > maxSize) {
				return getError("上传文件大小超过限制。");
			}
			// 检查扩展名
			String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
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
			ArchReelFileMaterial material = new ArchReelFileMaterial();
			material.setAddtime(DateUtil.getStringDate());
			User user = getUser();
			if(null==user)
				material.setAdduser("");
			else
				material.setAdduser(user.getId());
			String id=UUID.randomUUID().toString();
			material.setClintname(file.getName());
			material.setMeta_url(saveUrl + newFileName);
			material.setFilesize(StringUtil.formatDouble_str(file.getSize()/1024d));
			material.setMeta_type(fileExt);
			material.setServername(newFileName);
			material.setId(id);
			material.setMeta_serial(archReelFileMaterialSvc.getSerial(applyid));
			material.setMeta_name(file.getOriginalFilename());
			material.setArch_reel_files_id(applyid);
			archReelFileMaterialSvc.save(material);
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
	 * @param applyid
	 * @return
	 */
	@RequestMapping("/getAttach")
	@ResponseBody
	public Result getAttach(String applyid) {
		Result r = new Result();
		if(StringUtil.notBlank(applyid)) {
			QueryParameter qp = new QueryParameter();
			qp.addParamter("arch_reel_files_id", applyid);
			qp.setSortField("addtime");
			r.setData(archReelFileMaterialSvc.findAll(qp));
		}
		r.success();
		return crudError(r);
	}

	/**
	 * 转发上传附件请求
	 * @param serial 上传流水,如果不传系统自动生成
	 * @param fileType 文件类型限制
	 * @param maxSize 文件大小限制
	 * @return
	 */
	@RequestMapping(value="/toUpload", method = RequestMethod.GET)
	public String toUpload(String serial,String fileType,String maxSize,String callback,Model model)
	{
		if(StringUtil.isBlank(serial))
			serial=UUID.randomUUID().toString();
		if(StringUtil.isBlank(fileType))
			fileType="*.txt;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.jpg;*.png;*.jpeg;*.gif";
		if(StringUtil.isBlank(maxSize))
			maxSize="50mb";
		model.addAttribute("isCheck", true);
		model.addAttribute("serial", serial);
		model.addAttribute("batch", (new Date()).getTime());
		model.addAttribute("fileType", fileType);
		model.addAttribute("maxSize", maxSize);
		model.addAttribute("callback", callback);
		return "core/attach/uploadFile";
	}

	/**
	 * 从文件服务器获取文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFile")
	@AuthIgnore
	public void  getFile(HttpServletResponse response) throws Exception{
		String filePath = getRequest().getParameter("url");
		String fileName = getRequest().getParameter("filename");
		OutputStream out = null;
		FileInputStream fis = null;
		//是否是正式环境
		boolean isReal = "true".equalsIgnoreCase(BasicsFinal.getParamVal("environment.isformal"));
		String savePath = BasicsFinal.getParamVal("file.upload.path");
		if(isReal) {
			savePath = BasicsFinal.getParamVal("file.upload.sftppath");
		}
		try {
			out = response.getOutputStream();
			// 设置response的Header 防止文件名乱码
			response.addHeader("Content-Disposition",
					"attachment;filename="
							+ new String((fileName).getBytes("utf-8"), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			fis = new FileInputStream(savePath+filePath);
			int b;
			while ((b = fis.read()) != -1) {
				out.write(b);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("执行 文件下载downFile 方法失败：" + e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
