package com.sensebling.ope.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.index.entity.IndexVersion;
import com.sensebling.index.service.IndexVersionSvc;
import com.sensebling.ope.core.entity.OpeCoreModel;
import com.sensebling.ope.core.entity.OpeCoreModelVersionLog;
import com.sensebling.ope.core.service.OpeCoreModelSvc;
import com.sensebling.ope.core.service.OpeCoreModelVersionLogSvc;
@Service
public class OpeCoreModelSvcImpl extends BasicsSvcImpl<OpeCoreModel> implements OpeCoreModelSvc{
	@Resource
	private IndexVersionSvc indexVersionSvc;
	@Resource
	private OpeCoreModelVersionLogSvc opeCoreModelVersionLogSvc;
	
	@Override
	public Pager getModelPager(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from (select a.*,b.code versioncode,b.name versionname from ope_core_model a left join model_index_version b on b.id=a.versionid) where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), OpeCoreModel.class.getName());
	}

	@Override
	public boolean checkCode(String code, String id) {
		String sql = "select 1 from ope_core_model where code=?";
		List<Object> param = new ArrayList<Object>();
		param.add(code);
		if(StringUtil.notBlank(id)) {
			sql += " and id!=?";
			param.add(id);
		}
		List<OpeCoreModel> list = baseDao.findBySQLEntity(sql, param, OpeCoreModel.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public Result updateVersion(String id, String versionid) {
		Result r = new Result();
		OpeCoreModel model = get(id);
		if(model != null) {
			IndexVersion version = indexVersionSvc.get(versionid);
			if(version != null) {
				if(versionid.equals(model.getVersionid())) {
					r.success();
				}else {
					if("1".equals(version.getStatus())) {
						String sql = "update ope_core_model set versionid=? where id=?";
						baseDao.executeSQL(sql, versionid, id);
						OpeCoreModelVersionLog log = new OpeCoreModelVersionLog();
						log.setAddtime(DateUtil.getStringDate());
						log.setAdduser(HttpReqtRespContext.getUser().getId());
						log.setModelid(id);
						log.setVersionid(versionid);
						opeCoreModelVersionLogSvc.save(log);  
						r.success();
					}else {
						r.setError("当前指标版本已禁用");
					}
				}
			}else {
				r.setError("指标版本不存在");
			}
		}else {
			r.setError("模型不存在");
		}
		return r;
	}

}
