package com.sensebling.common.util;

import java.util.List;

import com.sensebling.system.entity.DictionaryContent;
import com.sensebling.system.finals.BasicsFinal;

/**
 * 获取数据字典工具类
 * @author 
 * @time 2014-10-23
 */
public class SysDataUtil {
	
	/**
	 * 根据数据字典类型 code 获取该类型下的数据字典列表
	 * @param key 
	 * @return Set<Dictionary>
	 */
	public static List<DictionaryContent> getDictionary(String key){
		
		return BasicsFinal.getDictContent(key);
	}
	
	public static String getDictionaryName(String key, String code){
		List<DictionaryContent> list = BasicsFinal.getDictContent(key);
		if(list!=null && list.size()>0) {
			for(DictionaryContent v:list) {
				if(v.getDictionaryCode().equals(code)) {
					return v.getDictionaryName();
				}
			}
		}
		return null;
	}

}
