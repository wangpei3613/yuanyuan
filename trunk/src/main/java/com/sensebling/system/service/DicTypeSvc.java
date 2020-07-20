package com.sensebling.system.service;


import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.DictionaryType;

public interface DicTypeSvc extends BasicsSvc<DictionaryType> {
	public List<DictionaryType> findAllCascade();

	/**
	 * 检查
	 * YF
	 * @param code 
	 * @param id
	 * @return
	 * 2017-12-16-下午03:48:02
	 */
	public boolean checkCode(String code, String id)throws Exception;
	/**
	 * 删除字典
	 * @param id
	 */
	public void delDictType(String id);

	public Pager queryPaper(QueryParameter qp);
	
	
}
