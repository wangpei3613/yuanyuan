package com.sensebling.system.service;

import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.DictionaryContent;

public interface DicContentSvc extends BasicsSvc<DictionaryContent> {
	public List<DictionaryContent> findAllCascade();
	/**
	 * 根据数据字典类型code查找所有数据字典内容<br>
	 * 用于更新缓存
	 * @param typeCode
	 * @return
	 */
	List<DictionaryContent> findByTypeCode(String typeCode);
	
	/**
	 * 保存数据
	 * YF
	 * @param map
	 * @throws Exception
	 * 2018-1-21-下午03:50:57
	 */
	public int saveDicContent(Map<String, Object> map)throws Exception;
	
	/**
	 * 使用sql根据id删除信息
	 * YF
	 * @param map
	 * @return
	 * @throws Exception
	 * 2018-1-23-上午11:26:18
	 */
	public int deleteDicContentById(Map<String, Object> map)throws Exception;
	
//	public int 
	
}
