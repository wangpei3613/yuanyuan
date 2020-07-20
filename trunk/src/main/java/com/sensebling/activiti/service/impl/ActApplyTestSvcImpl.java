package com.sensebling.activiti.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sensebling.activiti.entity.ActApplyTest;
import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.activiti.service.ActApplyInfoSvc;
import com.sensebling.activiti.service.ActApplyTestSvc;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.system.entity.User;
@Service("actApplyTestSvc")
public class ActApplyTestSvcImpl extends BasicsSvcImpl<ActApplyTest> implements ActApplyTestSvc{
	@Resource
	private ActApplyInfoSvc actApplyInfoSvc;

	@Override
	public Result addApply(ActApplyTest apply, User user) {
		Result r = new Result();
		if(actApplyInfoSvc.checkActivitiConfig(ProtocolConstant.activitiProcessCode.TEST)) {
			apply.setAddtime(DateUtil.getStringDate());
			apply.setAdduser(user.getId()); 
			String caseid = (String) save(apply);
			actApplyInfoSvc.newApply(caseid, ProtocolConstant.activitiProcessCode.TEST, user);
			r.success();
		}else {
			r.setError("流程配置有误，请联系管理员");
		}
		return r;
	}

	@Override
	public Pager select(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select b.*,a.* from v_act_apply_info a join sen_act_apply_test b on b.id=a.caseid where ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ActApplyTest.class.getName());
	}

	@Override
	public ActApplyTest getApply(String id) {
		String sql = "select b.*,a.applyid, a.appl_date, a.applman, a.actid, a.audit_state, a.caseid, a.currtachenames, a.applmanname from v_act_apply_info a join sen_act_apply_test b on b.id=a.caseid where b.id=?";
		List<ActApplyTest> list = baseDao.findBySQLEntity(sql, new Object[] {id}, ActApplyTest.class.getName());
		if(list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Result delApply(String id) {
		Result r = new Result();
		ActApplyTest apply = getApply(id);
		if(apply!=null && ProtocolConstant.activitiApplyState.state1.equals(apply.getAudit_state())) {
			delete(id);
			actApplyInfoSvc.delApply(apply.getApplyid());  
			r.success();
		}else {
			r.setError("只有待提交的申请允许删除");
		}
		return r;
	}

	@Override
	public Result cancelApply(String id) {
		Result r = new Result();
		ActApplyTest apply = getApply(id);
		if(apply!=null && (ProtocolConstant.activitiApplyState.state1.equals(apply.getAudit_state())
				|| ProtocolConstant.activitiApplyState.state4.equals(apply.getAudit_state()))) {
			actApplyInfoSvc.cancelApply(apply.getApplyid());  
			r.success();
		}else {
			r.setError("只有待提交或复议的申请允许取消");
		}
		return r;
	}
	@Override
	public Pager auditList(QueryParameter qp) {
		StringBuffer sb = new StringBuffer("select b.*,a.* from v_act_apply_todo a join sen_act_apply_test b on b.id=a.caseid where a.audit_state='2' and ");
		sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ActApplyTest.class.getName());
	}

	@Override
	public Result submitCallBefore(ActAudit act) {
		Result r = new Result();
		r.success();
		return r; 
	}

	@Override
	public void submitCallAfter(ActAudit act) {
		System.out.println("提交后回调");  
	}

	@Override
	public Result auditCallBefore(ActAudit act) {
		Result r = new Result();
		r.success();
		return r; 
	}

	@Override
	public void auditCallAfter(ActAudit act) {
		System.out.println("审批后回调");  		
	}
}
