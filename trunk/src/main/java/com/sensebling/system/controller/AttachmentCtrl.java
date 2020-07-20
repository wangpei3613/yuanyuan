package com.sensebling.system.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.FileOperateUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.MD5FileUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryCondition;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.entity.AttachmentBase;
import com.sensebling.system.entity.AttachmentRelation;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.AttachmentBaseSvc;
import com.sensebling.system.service.AttachmentRelationSvc;

/**
 * 附件模块管理控制器
 * @author 
 * @date 2014-10-13
 */
@Controller
@RequestMapping("/system/attachment")
public class AttachmentCtrl extends BasicsCtrl{
	@Resource
	private AttachmentBaseSvc attachmentBaseService;
	@Resource
	private AttachmentRelationSvc attachmentRelationService;
	
	@RequestMapping(value="/getRelationIdBySerial")
	@ResponseBody
	public String getSerialByRelaId(String relationId)
	{
		QueryParameter qp=new QueryParameter();
		qp.addParamter("relationId", relationId);
		List<AttachmentRelation> list=attachmentRelationService.findAll(qp);
		String serial="";
		for(AttachmentRelation ar:list)
		{
			serial=serial+","+ar.getAttachmentSerial();
		}
		return serial.replaceFirst(",", "");	}
	/**
	 * 转发上传附件请求
	 * @param serial 上传流水,如果不传系统自动生成
	 * @param fileType 文件类型限制
	 * @param maxSize 文件大小限制
	 * @return
	 */
	@RequestMapping(value="/toUpload", method = RequestMethod.GET)
	@AuthIgnore
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
		return "system/interface/uploadFile";
	}
	/**
	 * 上传文件，不压缩
	 * @param name
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/upload",produces = {"text/plain"})
	@ResponseBody
	public String dealUploadData(HttpServletRequest request,@RequestParam(value="Filedata", required=false) CommonsMultipartFile file)
	{
		try {
			String serial=request.getParameter("serial");//获取流水号
			String batch=request.getParameter("batch");//获取批次号
			if (!file.isEmpty()) {
				try 
				{
					/* 获取本地存储路径,在原保存父目录中已当天日期创建一个文件夹用于保存每天上传的文件*/
					String path = BasicsFinal.getParamVal("file.upload.path"); 
					File savePath=new File(path+"/"+DateUtil.getStringDateShort());
					if(!savePath.exists())
						savePath.mkdir();
					//获取文件的完整名称
					String fileNameClient = file.getOriginalFilename();
					//获取文件后缀
					String fileType = fileNameClient.substring(fileNameClient.lastIndexOf(".")+1).toLowerCase();
					//创建一个新文件用户保存上传的文件,新文件的名称采用时间戳
					String fileNameServer=UUID.randomUUID().toString() +"."+ fileType;
					File saveFile = new File(savePath+"/"+fileNameServer); // 新建一个文件
					file.transferTo(saveFile);// 将上传的文件写入新建的文件中
					/*将上传附件的信息保存到数据库中*/
					AttachmentBase ab=new AttachmentBase();
					ab.setCreateDate(DateUtil.getStringDate());
					ab.setCreateUser(getUser().getId());
					ab.setFileNameClient(fileNameClient);
					ab.setFileNameServer(fileNameServer);
					ab.setFilePath(savePath.getPath());
					ab.setFileMD5(MD5FileUtil.getFileMD5String(saveFile));
					ab.setFileSize(StringUtil.formatDouble_num(file.getSize()/1024d));
					ab.setFileType(fileType);
					ab.setSerial(serial);
					ab.setBatch(batch);
					attachmentBaseService.save(ab);
				} catch (Exception e) {
					e.printStackTrace();
					return "error";
				}
				return "success";
			} else {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 新增表单和附件流水的关联关系信息
	 * @param relation
	 * @param Serial
	 * @return 1:成功 其他:失败
	 */
	@RequestMapping(value="/addRelationAndSerial")
	@ResponseBody
	public String addAttRelaIdAndSerial(String relationId,String serial)
	{
		attachmentRelationService.saveAttRelation(relationId, serial);
		return "1";
	}
	/**
	 * 查询附件信息列表
	 * @return
	 */
	@RequestMapping(value="/queryBySerial")
	@ResponseBody
	public Pager selectBySerial()
	{
		return attachmentBaseService.findPage(getQueryParameter());
	}
	/**
	 * 删除附件
	 * @param ids 单个或多个附件的id,多个id以,号分割
	 * @return
	 */
	@RequestMapping(value="/delete",produces = {"text/plain"})
	@ResponseBody
	public String delById(String ids)
	{
		String num ="";
		getQueryParameter().addParamter("id", ids,QueryCondition.in);
		List<AttachmentBase> list=attachmentBaseService.findAll(getQueryParameter());
		num = attachmentBaseService.deleteByIds(ids,false)+"";
		for(AttachmentBase vo:list)
		{
			File file=new File(vo.getFilePath()+"/"+vo.getFileNameServer());
			if(file.exists())
				file.delete();
		}
		return num;
	}
	/**
	 * 下载附件,若同时下载多个文件则将文件打包成压缩包
	 * @param ids 单个或多个附件的id,多个id以,号分割
	 * @throws Exception 
	 */
	@RequestMapping(value="/download")
	public void download(String ids) throws Exception
	{
		QueryParameter qp=new QueryParameter();
		qp.addParamter("id", ids);
		qp.addCondition("id", QueryCondition.in);
		List<AttachmentBase> list=attachmentBaseService.findAll(qp);
		User user=getUser();
		if(list.size()==1)
		{
			AttachmentBase ab=list.get(0);
			logger.info("单文件请求下载[用户:"+(user==null?"未登录":user.getUserName())+"]:"+ab.getFileNameClient());
			renderFile(ab.getFileNameClient(), ab.getFilePath()+"/"+ab.getFileNameServer());
		}
		else if(list.size()>0)
		{
			Map<String,File> files=new HashMap<String, File>();
			for(AttachmentBase ab:list)
			{
				files.put(ab.getFileNameClient(),new File(ab.getFilePath()+"/"+ab.getFileNameServer()));
			}
			File zipFile=FileOperateUtil.compressFile(files, UUID.randomUUID().toString());
			renderFile("压缩附件包.zip", zipFile.getPath());
			zipFile.delete();//临时文件需删除
		}
	}
	/**
	 * 请求显示图片预览页面
	 * @param serial 附件流水号
	 * @param selectId 选择查看的图片附件id
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/showImgPreview")
	@AuthIgnore
	public String showPicPreview(Model model) throws Exception
	{
		QueryParameter qp=getQueryParameter();
		qp.addCondition("serial", QueryCondition.in);
		qp.addCondition("id", QueryCondition.in);
		qp.addCondition("fileType", QueryCondition.in);
		model.addAttribute("serial", qp.getParam("serial"));
		model.addAttribute("fileType", qp.getParam("fileType"));
		if(StringUtil.notBlank(qp.getParam("imgPreviewTitle")))
		{
			model.addAttribute("imgPreviewTitle", qp.getParam("imgPreviewTitle"));
			qp.removeParamter("imgPreviewTitle");
		}
		if(StringUtil.isBlank(qp.getParam("id")))
		{
			List<Map<String, Object>> list=attachmentBaseService.queryGropByBatch(qp);
			List<String> batchGroup=new ArrayList<String>();
			if(list.size()>1)
			{
				for(Map<String, Object> m:list)
					batchGroup.add(StringUtil.isBlank(m.get("BATCH"))?"null_batch":m.get("BATCH").toString());
				String batchJson=JsonUtil.entityToJSON(batchGroup);
				model.addAttribute("batchGroup",batchJson);
			}
		}
		else
			model.addAttribute("id", qp.getParam("id"));
		return "system/interface/imgPreview";
	}
	/**
	 * 请求显示图片预览页面
	 * @param serial 附件流水号
	 * @param selectId 选择查看的图片附件id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/queryImgData")
	@ResponseBody
	public List<Map<String, String>> selectImgData()
	{
		
		List<Map<String, String>> re_list=new ArrayList<Map<String,String>>();
		QueryParameter qp=getQueryParameter();
		qp.addCondition("serial", QueryCondition.in);
		qp.addCondition("id", QueryCondition.in);
		qp.addCondition("fileType", QueryCondition.in);
		List<AttachmentBase> data=attachmentBaseService.findAll(qp);
		for(AttachmentBase vo:data)
		{
			Map<String, String> m=new HashMap<String, String>();
			m.put("title", vo.getFileNameClient());
			m.put("timg", getRequest().getContextPath()+"/system/attachment/download?ids="+vo.getId());
			m.put("img", getRequest().getContextPath()+"/system/attachment/download?ids="+vo.getId());
			m.put("listimg", getRequest().getContextPath()+"/system/attachment/download?ids="+vo.getId());
			m.put("picwidth", "400");
			m.put("picheight", "300");
			re_list.add(m);
		}
		return re_list;
	}
}
