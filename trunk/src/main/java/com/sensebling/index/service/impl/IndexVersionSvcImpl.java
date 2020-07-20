package com.sensebling.index.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexVersion;
import com.sensebling.index.service.IndexVersionSvc;
@Service
public class IndexVersionSvcImpl extends BasicsSvcImpl<IndexVersion> implements IndexVersionSvc{
	@Override
	public boolean checkCode(String code, String id) {
		String sql = "select 1 from model_index_version where code=?";
		List<Object> param = new ArrayList<Object>();
		param.add(code);
		if(StringUtil.notBlank(id)) {
			sql += " and id!=?";
			param.add(id);
		}
		List<IndexVersion> list = baseDao.findBySQLEntity(sql, param, IndexVersion.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public void delVersion(String id) {
		String sql = "delete from model_index_version_conversion b where b.indexversionid in (select a.id from model_index_version_detail a where a.versionid=?)";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_version_parameter b where b.indexversionid in (select a.id from model_index_version_detail a where a.versionid=?)";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_version_detail a where a.versionid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_version a where a.id=?";
		baseDao.executeSQL(sql, id);
	}

	@Override
	public void setChanged(String versionid) {
		baseDao.executeSQL("update model_index_version set changed='1' where id=?", versionid);
	}

	@Override
	public void removeChanged(String versionid) {
		baseDao.executeSQL("update model_index_version set changed=null where id=?", versionid);
	}

	@Override
	public void updateChangedByIndex(String indexid) {
		String sql = "update model_index_version a set a.changed='1' where a.id in (select b.versionid from model_index_version_detail b where b.indexid=? and b.status='1')";
		baseDao.executeSQL(sql, indexid);
	}

	@Override
	public void updateChangedByIndexParameter(String indexid, String code) {
		String sql = "update model_index_version a set a.changed='1' where a.id in (select b.versionid from model_index_version_detail b where b.indexid=? and b.status='1' and not exists (select 1 from model_index_version_parameter c where c.indexversionid=b.id and c.code=?))";
		baseDao.executeSQL(sql, indexid, code);
	}
	@Override
	public void updateChangedByIndexConversion(String indexid, String code) {
		String sql = "update model_index_version a set a.changed='1' where a.id in (select b.versionid from model_index_version_detail b where b.indexid=? and b.status='1' and not exists (select 1 from model_index_version_conversion c where c.indexversionid=b.id and c.code=?))";
		baseDao.executeSQL(sql, indexid, code);
	}

	@Override
	public List<Object[]> queryBySql(String sql, Object... objects) {
		return baseDao.queryBySql(sql, objects);
	}

	@Override
	public String callProcedure(String sql, Object... obj) {
		return baseDao.callProcedure(sql, obj);
	}
}
