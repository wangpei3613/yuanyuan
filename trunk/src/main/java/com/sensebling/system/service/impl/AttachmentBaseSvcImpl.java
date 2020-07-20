package com.sensebling.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.AttachmentBase;
import com.sensebling.system.service.AttachmentBaseSvc;


@Service("attachmentBaseSvc.java")
@Transactional
public class AttachmentBaseSvcImpl extends BasicsSvcImpl<AttachmentBase> implements AttachmentBaseSvc{

	public List<Map<String, Object>> queryGropByBatch(QueryParameter qp) {
		String sql="select serial as SERIAL,batch as BATCH from sen_attachment_base t where"+qp.transformationCondition("t")+" group by serial,batch";
		return baseDao.queryBySql_listMap(sql);
	}
}
