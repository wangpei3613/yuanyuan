package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.vo.Jurisdiction;


/**
 * 权限控制
 * @author 
 * @date 2014-9-24
 */
public interface JurisdictionSvc extends BasicsSvc<Jurisdiction> {
	/**
	 * 根据userId 查找该用户下的所有权限
	 * @param id
	 * @return
	 */
	List<Jurisdiction> findByUserId(String userId);

}
