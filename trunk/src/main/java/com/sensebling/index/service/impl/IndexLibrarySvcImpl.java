package com.sensebling.index.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexLibrary;
import com.sensebling.index.service.IndexLibrarySvc;
@Service
public class IndexLibrarySvcImpl extends BasicsSvcImpl<IndexLibrary> implements IndexLibrarySvc{

	@Override
	public String getNextSeq() {
		String sql = "select nextval for S_MODEL_INDEX seq from sysibm.sysdummy1";
		return String.valueOf(baseDao.queryBySql_listMap(sql).get(0).get("SEQ"));
	}

	@Override
	public List<IndexLibrary> getVersionIndex(String versionid) {
		String sql = "WITH modules(id, code, name, level, pid, category, argument, content, maxscore, formula, status, sort, remark, createuser, createdate, indexversionid, versionid, indexid) AS( SELECT id, code, name, level, pid, category, argument, content, maxscore, formula, status, sort, remark, createuser, createdate, indexversionid, versionid, indexid FROM ( SELECT b.id, b.code, b.name, b.level, b.pid, b.category, a.argument, a.content, a.maxscore, a.formula, a.status, a.sort, a.remark, a.createuser, a.createdate, a.id indexversionid, a.versionid, a.indexid FROM model_index_version_detail a JOIN model_index_library b ON a.indexid=b.id AND a.versionid=?) c UNION ALL SELECT d.id, d.code, d.name, d.level, d.pid, d.category, d.argument, d.content, d.maxscore, d.formula, d.status, d.sort, d.remark, d.createuser, d.createdate, NULL indexversionid, NULL versionid, null indexid FROM model_index_library d, modules e WHERE e.pid=d.id) SELECT DISTINCT * FROM modules ORDER BY sort";
		return baseDao.findBySQLEntity(sql, new Object[] {versionid}, IndexLibrary.class.getName());
	}
	
	@Override
	public List<IndexLibrary> getVersionIndexEnable(String versionid) {
		String sql = "WITH modules(id, code, name, level, pid, category, argument, content, maxscore, formula, status, sort, remark, createuser, createdate, indexversionid, versionid, indexid) AS( SELECT id, code, name, level, pid, category, argument, content, maxscore, formula, status, sort, remark, createuser, createdate, indexversionid, versionid, indexid FROM ( SELECT b.id, b.code, b.name, b.level, b.pid, b.category, a.argument, a.content, a.maxscore, a.formula, a.status, a.sort, a.remark, a.createuser, a.createdate, a.id indexversionid, a.versionid, a.indexid FROM model_index_version_detail a JOIN model_index_library b ON a.indexid=b.id AND a.versionid=? and a.status='1') c UNION ALL SELECT d.id, d.code, d.name, d.level, d.pid, d.category, d.argument, d.content, d.maxscore, d.formula, d.status, d.sort, d.remark, d.createuser, d.createdate, NULL indexversionid, NULL versionid, null indexid FROM model_index_library d, modules e WHERE e.pid=d.id) SELECT DISTINCT * FROM modules ORDER BY sort";
		return baseDao.findBySQLEntity(sql, new Object[] {versionid}, IndexLibrary.class.getName());
	}

	@Override
	public List<Map<String, Object>> getTreeGrid(List<IndexLibrary> list) {
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(IndexLibrary m:list) {
				map = JsonUtil.beanToMap(m);
				map.put("iconCls", "1".equals(m.getLevel())?"menumanagetreegrid-parent":"menumanagetreegrid-leaf");
				map.put("text", m.getName());
				if(StringUtil.notBlank(m.getChecked())) {
					map.put("checked", "1".equals(m.getChecked()));
				}
				temp.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}

	@Override
	public List<IndexLibrary> getIndexToVersionTree(String versionid) {
		String sql = "select a.*,'0' checked from model_index_library a where not exists (select 1 from model_index_version_detail b where b.indexid=a.id and b.versionid=?) order by a.sort asc";
		return baseDao.findBySQLEntity(sql, new Object[] {versionid}, IndexLibrary.class.getName());  
	}

	@Override
	public void delLibrary(String id) {
		String sql = "delete from model_index_conversion a where a.indexid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_parameter a where a.indexid=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from model_index_library a where a.id=?";
		baseDao.executeSQL(sql, id);
	}

}
