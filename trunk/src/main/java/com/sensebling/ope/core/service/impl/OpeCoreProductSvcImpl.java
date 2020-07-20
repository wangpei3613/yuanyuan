package com.sensebling.ope.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.ope.core.entity.OpeCoreModel;
import com.sensebling.ope.core.entity.OpeCoreProduct;
import com.sensebling.ope.core.entity.OpeCoreProductModelLog;
import com.sensebling.ope.core.service.OpeCoreModelSvc;
import com.sensebling.ope.core.service.OpeCoreProductModelLogSvc;
import com.sensebling.ope.core.service.OpeCoreProductSvc;
@Service
public class OpeCoreProductSvcImpl extends BasicsSvcImpl<OpeCoreProduct> implements OpeCoreProductSvc{
	@Resource
	private OpeCoreModelSvc opeCoreModelSvc;
	@Resource
	private OpeCoreProductModelLogSvc opeCoreProductModelLogSvc;
	@Override
	public Pager getProductPager(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select * from (select a.*,b.code modelcode,b.name modelname from ope_core_product a left join ope_core_model b on b.id=a.modelid) where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), OpeCoreProduct.class.getName());
	}
	
	@Override
	public boolean checkCode(String code, String id) {
		String sql = "select 1 from ope_core_product where code=?";
		List<Object> param = new ArrayList<Object>();
		param.add(code);
		if(StringUtil.notBlank(id)) {
			sql += " and id!=?";
			param.add(id);
		}
		List<OpeCoreProduct> list = baseDao.findBySQLEntity(sql, param, OpeCoreProduct.class.getName());
		if(list==null || list.size()==0) {
			return true;
		}
		return false;
	}

	@Override
	public Result updateModel(String id, String modelid) {
		Result r = new Result();
		OpeCoreProduct product = get(id);
		if(product != null) {
			OpeCoreModel model = opeCoreModelSvc.get(modelid);
			if(model != null) {
				if(modelid.equals(product.getModelid())) {
					r.success();
				}else {
					if("1".equals(model.getStatus())) {
						String sql = "update ope_core_product set modelid=? where id=?";
						baseDao.executeSQL(sql, modelid, id);
						OpeCoreProductModelLog log = new OpeCoreProductModelLog();
						log.setAddtime(DateUtil.getStringDate());
						log.setAdduser(HttpReqtRespContext.getUser().getId());
						log.setModelid(modelid);
						log.setProductid(id);
						opeCoreProductModelLogSvc.save(log);  
						r.success();
					}else {
						r.setError("当前模型已禁用");
					}
				}
			}else {
				r.setError("模型不存在");
			}
		}else {
			r.setError("产品不存在");
		}
		return r;
	}

	@Override
	public Result delProduct(String id) {
		Result r = new Result();
		OpeCoreProduct product = get(id);
		if(product != null) {
			String sql = "select count(1) num from ope_apply_baseinfo a where a.productid=?";
			List<Map<String,Object>> list = baseDao.queryBySql_listMap(sql, id);
			if(Integer.parseInt(list.get(0).get("NUM").toString()) == 0) {
				baseDao.executeSQL("delete from ope_core_product where id=?", id);
				baseDao.executeSQL("delete from ope_core_product_model_log where productid=?", id);
				r.success();
			}else {
				r.setError("当前产品已被使用，不能删除");   
			}
		}
		return r;
	}

}
