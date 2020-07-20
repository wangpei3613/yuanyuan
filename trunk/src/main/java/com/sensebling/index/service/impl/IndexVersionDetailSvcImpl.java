package com.sensebling.index.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.index.entity.IndexLibrary;
import com.sensebling.index.entity.IndexVersion;
import com.sensebling.index.entity.IndexVersionDetail;
import com.sensebling.index.service.IndexLibrarySvc;
import com.sensebling.index.service.IndexVersionDetailSvc;
import com.sensebling.index.service.IndexVersionSvc;
import com.sensebling.system.entity.User;
@Service
public class IndexVersionDetailSvcImpl extends BasicsSvcImpl<IndexVersionDetail> implements IndexVersionDetailSvc{
	@Resource
	private IndexLibrarySvc indexLibrarySvc;
	@Resource
	private IndexVersionSvc indexVersionSvc;
	@Override
	public void addVersionIndex(String ids, String versionid) {
		IndexVersion version = indexVersionSvc.get(versionid);
		if(version != null) {
			List<IndexVersionDetail> list = new ArrayList<IndexVersionDetail>();
			String time = DateUtil.getStringDate();
			User user = HttpReqtRespContext.getUser();
			for(String id:ids.split(",")) {
				IndexLibrary index = indexLibrarySvc.get(id);
				if(index!=null && "2".equals(index.getLevel()) && "1".equals(index.getStatus()) && check(id, versionid)) {  
					IndexVersionDetail v = new IndexVersionDetail();
					v.setArgument(index.getArgument());
					v.setContent(index.getContent());
					v.setCreatedate(time);
					v.setCreateuser(user.getId());
					v.setFormula(index.getFormula());
					v.setIndexid(index.getId());
					v.setMaxscore(index.getMaxscore());
					v.setRemark(index.getRemark());
					v.setSort(index.getSort());
					v.setStatus(index.getStatus());
					v.setVersionid(version.getId());  
					list.add(v);
				}
			}
			if(list.size() > 0) {
				baseDao.save(list); 
				indexVersionSvc.setChanged(versionid);
			}
		}
	}
	@Override
	public boolean check(String indexid, String versionid) {
		String sql = "select 1 from model_index_version_detail where versionid=? and indexid=?";
		List<IndexVersionDetail> list = baseDao.findBySQLEntity(sql, new Object[] {versionid, indexid}, IndexVersionDetail.class.getName());
		if(list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
	@Override
	public void delVersionIndex(String id) {
		String sql = "delete from model_index_version_conversion b where b.indexversionid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_version_parameter b where b.indexversionid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_version_detail a where a.id=?";
		baseDao.executeSQL(sql, id);
	}

}
