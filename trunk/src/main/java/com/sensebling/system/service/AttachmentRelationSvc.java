package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.system.entity.AttachmentRelation;




/**
 * 系统 附件信息_关联关系 业务层接口
 * @author YF
 * 2017-11-24
 */
public interface AttachmentRelationSvc extends BasicsSvc<AttachmentRelation>{
	/**
	 * 保存应用表单id和流水id的关联关系
	 * @param relationId
	 * @param serial
	 */
	void saveAttRelation(String relationId,String serial);

	/**
	 * 主表ID和分页数字
	 * @param paramValues
	 */
	int findCouBySql(String[] paramValues);

	/**
	 * 主表ID和分页数字
	 * @param paramValues
	 * @return 附件
	 */
	List<AttachmentRelation> findBySql(String[] paramValues);

	int delBySerial(String serial);

	int delByRelationId(String topicId);
	/**
	 * 通过关联id获取附件中间表对象
	 * @author  
	 * @date Jun 24, 2015 4:38:09 PM
	 * @param relationId
	 * @return
	 */
	public AttachmentRelation getAttRelationByRelation(String relationId);
}
