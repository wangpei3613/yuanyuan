package com.sensebling.index.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.index.entity.IndexValueVersion;
import com.sensebling.index.service.IndexValueVersionSvc;
@Service
public class IndexValueVersionSvcImpl extends BasicsSvcImpl<IndexValueVersion> implements IndexValueVersionSvc{

	@Override
	public void delData(String caseid, String versionid) {
		String sql = "delete from model_index_value_detail where valueid in (select id from model_index_value_version where caseid=? and versionid=?)";
		baseDao.executeSQL(sql, caseid, versionid);
		sql = "delete from model_index_value_parameter where valueindexid in (select id from model_index_value_detail where valueid in (select id from model_index_value_version where caseid=? and versionid=?))";
		baseDao.executeSQL(sql, caseid, versionid);
		sql = "delete from model_index_value_version where caseid=? and versionid=?";
		baseDao.executeSQL(sql, caseid, versionid);
	}

	@Override
	public IndexValueVersion getByParams(String caseid, String versionid) {
		String sql = "select * from model_index_value_version where caseid=? and versionid=?";
		return (IndexValueVersion)baseDao.getBySQLEntity(sql, IndexValueVersion.class.getName(), caseid, versionid);
	}

	@Override
	public void delData(String caseid) {
		String sql = "delete from model_index_value_detail where valueid in (select id from model_index_value_version where caseid=?)";
		baseDao.executeSQL(sql, caseid);
		sql = "delete from model_index_value_parameter where valueindexid in (select id from model_index_value_detail where valueid in (select id from model_index_value_version where caseid=?))";
		baseDao.executeSQL(sql, caseid);
		sql = "delete from model_index_value_version where caseid=?";
		baseDao.executeSQL(sql, caseid);
	}

}
