package com.sensebling.system.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.UserLeave;
import com.sensebling.system.service.UserLeaveSvc;
@Service
public class UserLeaveSvcImpl extends BasicsSvcImpl<UserLeave> implements UserLeaveSvc{

	@Override
	public Pager getPager(QueryParameter qp) {
		String sql = "select a.*,b.username,b.nickname,c.fullname deptname from sen_user_leave a join sen_user b on b.id=a.userid join sen_department c on c.id=b.deptid where "+StringUtil.initViewAuth("b.id", "b.deptid")+" and ";
		if(StringUtil.notBlank(qp.getParam("startdate")) || StringUtil.notBlank(qp.getParam("enddate"))) {
			if(StringUtil.notBlank(qp.getParam("startdate"))) {
				if(StringUtil.notBlank(qp.getParam("enddate"))) {
					sql += " (startdate<='"+qp.getParam("enddate").toString()+"' and enddate>='"+qp.getParam("startdate").toString()+"') and ";
				}else {
					sql += "enddate>='"+qp.getParam("startdate").toString()+"' and";
				}
			}else {
				sql += "startdate<='"+qp.getParam("enddate").toString()+"' and ";
			}
			qp.removeParamter("startdate");
			qp.removeParamter("enddate");
		}
		sql += qp.transformationCondition(null)+qp.getOrderStr(null);
		return this.baseDao.querySQLPageEntity(sql.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), UserLeave.class.getName());
	}

}
