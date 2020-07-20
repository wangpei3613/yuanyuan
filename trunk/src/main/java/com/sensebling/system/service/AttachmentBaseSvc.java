package com.sensebling.system.service;

import java.util.List;
import java.util.Map;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.AttachmentBase;



/**
 * 系统 模块对象 业务层接口
 * @author 
 * @date 2012-9-23
 */
public interface AttachmentBaseSvc extends BasicsSvc<AttachmentBase>{

    /**
     * 根据条件 查询附件
     * YF
     * 2017-11-24
     * @param qp
     * @return
     */
	List<Map<String, Object>> queryGropByBatch(QueryParameter qp);
}
