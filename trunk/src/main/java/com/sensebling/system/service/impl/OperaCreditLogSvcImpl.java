package com.sensebling.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.OperaCreditLog;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.OperaCreditLogSvc;

/**
 * 操作日志
 * 
 * @author YY
 *
 */
@Service("operaCreditLogSvc")
@Transactional
public class OperaCreditLogSvcImpl extends BasicsSvcImpl<OperaCreditLog>implements OperaCreditLogSvc {

	@Override
	public String saveOperaCreditLog(Object obj, String oldData, String newData, String dist,String creditLogId) throws Exception {
		OperaCreditLog creditLog = new OperaCreditLog();
		if(null!=obj){
			User user = HttpReqtRespContext.getUser();
			creditLog.setOLDDATA(oldData);
			creditLog.setOP_NO(user.getId());
			creditLog.setOP_NAME(user.getNickName());
			creditLog.setREMARK(dist);// 修改描述
			creditLog.setOP_TIME(StringUtil.getCurrentTime("3"));
			creditLog.setENTITYN(obj.getClass().getName());
			creditLog.setTABLENAME(StringUtil.getTableName(obj));

			creditLog.setNEWDATA(newData);
			this.save(creditLog);
			if(StringUtil.isBlank(creditLogId)){
				creditLog.setOP_HJ(creditLog.getID());
			}else{
				creditLog.setOP_HJ(creditLogId);
			}
			this.merge(creditLog);

		}
		return creditLog.getID();
	}

	/**
	 * 查询修改记录
	 * @param queryParameter
	 * @return
	 * @throws Exception
	 * 2018年4月17日-下午7:09:15
	 * YF
	 */
	@Override
	public Pager queryAll(QueryParameter queryParameter) throws Exception {
		String sDate = StringUtil.sNull(queryParameter.getParam("sDate"));
		String eDate = StringUtil.sNull(queryParameter.getParam("eDate"));
		queryParameter.removeParamter("sDate");
		queryParameter.removeParamter("eDate");
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select a.ID,TABLENAME,ENTITYN,REMARK,OP_NO,OP_NAME,OP_TIME,nvl(a.username,OP_NO) username from OPR_UPDATE_RECODE left join sen_user a on a.id=op_no  where  1=1 and ");
		if(!"".equals(sDate)){
			if(sDate.length()>10){
				sDate = sDate.substring(0,10);
			}
			sql.append(" to_char(op_time,'yyyy-MM-dd')>= '"+sDate+"' and ");
		}
		if(!"".equals(eDate)){
			if(eDate.length()>10){
				eDate = eDate.substring(0,10);
			}
			sql.append(" to_char(op_time,'yyyy-MM-dd')<= '"+eDate+"' and ");
		}
		sql.append(queryParameter.transformationCondition(null)+queryParameter.getOrderStr(null));
		return this.baseDao.querySQLPageEntity(sql.toString(), queryParameter.getPager().getPageSize(), queryParameter.getPager().getPageIndex(), OperaCreditLog.class.getName());

	}

}
