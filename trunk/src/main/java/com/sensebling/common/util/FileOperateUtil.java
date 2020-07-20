package com.sensebling.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sensebling.system.finals.BasicsFinal;

import sun.misc.BASE64Decoder;

/**
 * 文件上传下载工具类
 * 
 * @author 
 * @date 2014-10-9
 */
public class FileOperateUtil {

	private static final String REALNAME = "realName"; // 真实名称
	private static final String STORENAME = "storeName";// 上传以后保存到服务器的名称
	private static final String SIZE = "size";// 文件大小
	private static final String SUFFIX = "suffix";// 文件类型
	private static final String CONTENTTYPE = "contentType";
	private static final String CREATETIME = "createTime";// 创建时间

	/**
	 * 将上传的文件进行重命名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午3:39:53
	 * @param name
	 * @return
	 */
	private static String rename(String name) {
		Long now = Long.parseLong(DateUtil.formatDateTimeString(new Date()));
		Long random = (long) (Math.random() * now);
		String fileName = now + "" + random;
		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}

	/**
	 * 压缩后的文件名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午6:21:32
	 * @param name
	 * @return
	 */
	private static String zipName(String name) {
		String prefix = "";
		if (name.indexOf(".") != -1) {
			prefix = name.substring(0, name.lastIndexOf("."));
		} else {
			prefix = name;
		}
		return prefix + ".zip";
	}

	/**
	 * 上传文件，上传以后不压缩，采用系统默认路径上传
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	public static List<Map<String, Object>> upload(HttpServletRequest request,
			List<MultipartFile> file) {
		return upload(request, file, null);
	}

	/**
	 * 上传文件，不需要压缩，指定上传文件路径
	 * 
	 * @param request
	 * @param file
	 * @param uploadPath
	 * @return
	 */
	public static List<Map<String, Object>> upload(HttpServletRequest request,
			List<MultipartFile> file, String uploadPath) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String uploadDir = "";
		// 获取文件上传路径
		if (StringUtil.notBlank(uploadPath)) {
			uploadDir = uploadPath;
		} else {
			uploadDir = request.getSession().getServletContext().getRealPath(
					"/")
					+ BasicsFinal.getParamVal("file.upload.path");
		}
		System.out.println("文件上传路径：="+uploadDir );
		System.out.println("上传文件数：="+file.size() );

		FileOutputStream fileOutputStream = null;
		for (int i = 0; i < file.size(); i++) {
			if (!file.get(i).isEmpty()) {
				String fileName = file.get(i).getOriginalFilename(); // 真实文件名
				String fileType = fileName.substring(fileName.lastIndexOf("."));// 文件类型
				String storeName = rename(fileName);
				File files = new File(uploadDir + storeName); // 新建一个文件
				try {
					fileOutputStream = new FileOutputStream(files);
					fileOutputStream.write(file.get(i).getBytes());
					fileOutputStream.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Map<String, Object> map = new HashMap<String, Object>();
				// 固定参数值对
				map.put(FileOperateUtil.REALNAME, fileName);// 真实名称
				map.put(FileOperateUtil.STORENAME, storeName);// 存储名称
				map.put(FileOperateUtil.SIZE, new File(storeName).length());// 大小
				map.put(FileOperateUtil.SUFFIX, fileType);// 文件类型
				map.put(FileOperateUtil.CREATETIME, new Date());// 存放日期
				result.add(map);
				if (fileOutputStream != null) { // 关闭流
					try {
						fileOutputStream.close();
					} catch (IOException ie) {
						ie.printStackTrace();
					}
				}
			}
		}

		return result;
	}

	/**
	 * 上传文件，上传以后压缩成zip ，采用默认路径
	 * 
	 * @author 
	 * @date 2012-5-5 下午12:25:47
	 * @param request
	 * @param params
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> uploadZIP(HttpServletRequest request)
			throws Exception {
		return uploadZIP(request, null);
	}

	/**
	 * 上传文件,上传以后压缩成zip，指定上传文件的绝对路径
	 * 
	 * @param request
	 * @param params
	 * @param values
	 * @param uploadPath
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> uploadZIP(
			HttpServletRequest request, String uploadPath) throws Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
		String uploadDir = "";
		if (StringUtil.notBlank(uploadPath)) {
			uploadDir = uploadPath;
		} else {
			uploadDir = request.getSession().getServletContext().getRealPath(
					"/")
					+ BasicsFinal.getParamVal("file.upload.path");
		}
		System.out.println("文件上传路径：="+uploadDir );
		File file = new File(uploadDir);

		if (!file.exists()) {
			file.mkdir();
		}

		String fileName = null;
		for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()
				.iterator(); it.hasNext();) {

			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();
			
			fileName = mFile.getOriginalFilename();
			String storeName = rename(fileName);
			String noZipName = uploadDir + storeName;
			String zipName = zipName(noZipName);

			// 上传成为压缩文件
			ZipOutputStream outputStream = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(zipName)));
			outputStream.putNextEntry(new ZipEntry(fileName));
			outputStream.setEncoding("GBK");

			FileCopyUtils.copy(mFile.getInputStream(), outputStream);

			Map<String, Object> map = new HashMap<String, Object>();
			// 固定参数值对
			map.put(FileOperateUtil.REALNAME, zipName(fileName));
			map.put(FileOperateUtil.STORENAME, zipName(storeName));
			map.put(FileOperateUtil.SIZE, new File(zipName).length());
			map.put(FileOperateUtil.SUFFIX, "zip");
			map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream");
			map.put(FileOperateUtil.CREATETIME, new Date());

			result.add(map);
		}
		return result;
	}
	/**
	 * 压缩文件
	 * @param files 文件集合,key 为压缩后压缩包中的文件名,value为要压缩的文件file
	 * @param zipName 压缩后的.zip压缩文件名称
	 * @return
	 * @throws Exception
	 */
	public static File compressFile(Map<String,File> files,String zipName) throws Exception
	{
		if(StringUtil.isBlank(zipName))
			zipName="zipFile";
		File zipFile=File.createTempFile(zipName, ".zip");//创建一个临时zip空文件
		ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
		outputStream.setEncoding("GBK");
		for(String fileNameInZip:files.keySet())
		{
			
			File file=files.get(fileNameInZip);
			if(!file.exists())
				continue;
			
			ZipEntry zipEntry=new ZipEntry(fileNameInZip);
			outputStream.putNextEntry(zipEntry);
			InputStream inputStream=new BufferedInputStream(new FileInputStream(file));
			byte[] buffer=new byte[2048000];//每2000kb读取一次
			int bytesRead;
			//读取数据到输出流中
			while (-1 != (bytesRead=inputStream.read(buffer, 0, buffer.length)))
				outputStream.write(buffer, 0, bytesRead);
			inputStream.close();
			outputStream.closeEntry();
		}
		outputStream.flush();
		outputStream.close();
		return zipFile;
	}

	/**
	 * 下载 ，采用默认文件路径
	 * 
	 * @author 
	 * @date 2012-5-5 下午12:25:39
	 * @param request 
	 * @param response
	 * @param storeName 需要下载的文件名
	 * @param realName 下载以后展示给用户的文件名
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String realName)
			throws Exception {
		download(request, response, storeName, realName, null);
	}

	/**
	 * 下载文件，指定下载文件绝对路径
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param storeName 需要下载的文件名
	 * @param realName 下载以后展示给用户的文件名
	 * @param filePath  需要下载的文件路径
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String realName,
			String filePath) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String downLoadPath = "";
		// 当没有指定下载路径的时候，直接去获取文件上传路径地址
		if (StringUtil.notBlank(filePath)) {
			String ctxPath = filePath;
			downLoadPath = ctxPath + storeName;
		} else {
			String ctxPath = request.getSession().getServletContext()
					.getRealPath("/")
					+ BasicsFinal.getParamVal("file.upload.path");
			downLoadPath = ctxPath + storeName;
		}

		long fileLength = new File(downLoadPath).length();
		 response.setContentType("application/x-msdownload"); 
		//response.setContentType("application/octet-stream");
		 realName = URLEncoder.encode(realName, "UTF-8");
         response.setHeader("Content-disposition", "attachment; filename="  
                 +realName);    
		response.setHeader("Content-Length", String.valueOf(fileLength));
		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
	
	 /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
	public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        //判断目录或文件是否存在
        if (!file.exists()) {  
        	//不存在返回 false
            return flag;
        } else {
            //判断是否为文件
            if (file.isFile()) {  
            	//为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  
            	//为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
    
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteFile(String sPath) {
    	boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
        	file.delete();
            flag = true;
        }
        return flag;
    }
    
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    private static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 保存签名图片
     * @param data  图片base64字符串
     * @return
     * @throws Exception
     */
    public static String saveSignImg(String data){
		if(StringUtil.isBlank(data) || data.length() < 22) {
			return null;
		}
		String imgs = "/signimg"; 
		String time = DateUtil.getStringDateShort();
		String fileNameServer = UUID.randomUUID().toString() + ".jpg";
		if(!"true".equalsIgnoreCase(BasicsFinal.getParamVal("environment.isformal"))) {
			String path = BasicsFinal.getParamVal("file.upload.path"); 
			if(!new File(path+imgs).exists()) {
				new File(path+imgs).mkdir();
			}
			File savePath=new File(path+imgs+"/"+time);
			if(!savePath.exists()){
				savePath.mkdir();
			}
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(data.substring(22));    
				for(int i=0;i<b.length;++i){  
				    if(b[i]<0){
				        b[i]+=256;  
				    }  
				}  
				OutputStream out = new FileOutputStream(savePath + "/" + fileNameServer);      
				out.write(b);  
				out.flush();  
				out.close();
			} catch (Exception e) {
				Logger.getLogger(FileOperateUtil.class).error("转换签名数据为图片错误", e);;
				return null;
			} 
		}else {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(data.substring(22)); 
				for(int i=0;i<b.length;++i){  
				    if(b[i]<0){
				        b[i]+=256;  
				    }  
				}  
	        		SFTPUtils.uploadInptStrems(BasicsFinal.getParamVal("file.upload.sftppath")+imgs+"/"+time+"/", fileNameServer, new ByteArrayInputStream(b));
			} catch (Exception e) {
				Logger.getLogger(FileOperateUtil.class).error("转换签名数据为图片错误", e);;
				return null;
			}
		}
        return imgs+"/"+time+"/"+fileNameServer;
    }
}
