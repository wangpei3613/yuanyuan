package com.sensebling.ope.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.ope.core.entity.OpeCorePerIncome;
import com.sensebling.ope.core.service.OpeCorePerIncomeSvc;
@Service
public class OpeCorePerIncomeSvcImpl extends BasicsSvcImpl<OpeCorePerIncome> implements OpeCorePerIncomeSvc{

	/**
	 * 获取档期人均收入
	 * @param org
	 * @return
	 */
	public OpeCorePerIncome getPerIncome(String org) {
		String sql = "select t1.perincome from ope_core_perincome t1 where t1.orgcode='"+org+"'";
		List<OpeCorePerIncome> list = this.baseDao.findBySQLEntity(sql);
		if(null != list && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 当地人均收入配置
	 * @param qp
	 * @return
	 */
	public List<OpeCorePerIncome> queryPerIncome(QueryParameter qp) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.fullname as orgname,t1.id as orgcode,t1.pid as orgPartCode,t2.perincome from sen_department t1 left join ope_core_perincome t2 on t1.id=t2.orgcode where ");
		sql.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return this.baseDao.findBySQLEntity(sql.toString());
	}
	
	/**
	 * 配置支行人均收入
	 * @param p
	 * @return
	 */
	public String doSavePerIncome(OpeCorePerIncome p) {
		this.baseDao.executeSQL("delete from ope_core_perincome t where t.orgcode = '"+p.getOrgCode()+"'");
		return String.valueOf(this.baseDao.save(p));
	}
}
