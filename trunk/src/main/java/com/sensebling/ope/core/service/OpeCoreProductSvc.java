package com.sensebling.ope.core.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.ope.core.entity.OpeCoreProduct;

public interface OpeCoreProductSvc extends BasicsSvc<OpeCoreProduct>{
	/**
	 * 查询产品列表
	 * @param qp
	 * @return
	 */
	Pager getProductPager(QueryParameter qp);
	/**
	 * 判断产品code是否存在相同的 
	 * @param code 产品编码
	 * @param id 排除的id 
	 * @return 返回true代表没有相同的
	 */
	boolean checkCode(String code, String id);
	/**
	 * 更新产品使用模型
	 * @param id
	 * @param modelid
	 * @return
	 */
	Result updateModel(String id, String modelid);
	/**
	 * 删除产品
	 * @param id
	 * @return
	 */
	Result delProduct(String id);

}
