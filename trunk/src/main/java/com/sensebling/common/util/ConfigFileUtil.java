package com.sensebling.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 读取全局配置文件信息
 * @author 
 * @date 2014-9-23
 */
public class ConfigFileUtil {

	/**
	 * 根据key获取配置的值
	 * 配置文件为：sysconfig.properties
	 * @param key 配置文件的key ,例如：<br>
	 * 			user.default.password=123456  ：123456 = get("user.default.password") 
	 * @return string 配置文件中key对应的value
	 */
	public static String get(String key){
		try {
			Properties prop=PropertiesLoaderUtils.loadAllProperties("sysconfig.properties");
			return prop.getProperty(key,"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 加载properties文件并将内容已map形式返回
	 * @param propertiesName 文件名称,此文件需在src目录下
	 * @return
	 */
	public static Map<String, String> loadProperties(String propertiesName)
	{
		Map<String, String> map=new HashMap<String, String>();
		try {
			Properties prot=PropertiesLoaderUtils.loadAllProperties(propertiesName);
			for(Object key:prot.keySet())
				map.put(key.toString(), prot.getProperty(key.toString(),""));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 更新properties文件
	 * @param propertiesPath 文件名,此文件需在src目录下
	 * @param content 内容
	 * @param comments 注释
	 */
	public static void updateProperties(String propertiesName,Map<String, String> content,String comments)
	{
		try {
			Properties prot=PropertiesLoaderUtils.loadAllProperties(propertiesName);
			for(String key:content.keySet())
				prot.setProperty(key, content.get(key));
			String path=URLDecoder.decode(ConfigFileUtil.class.getClassLoader().getResource(propertiesName).getFile(),"utf-8");
			File propertiesFile = new File(path);
			prot.store(new FileOutputStream(propertiesFile), comments==null?"":comments);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
