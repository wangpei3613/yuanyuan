package com.sensebling.core.attach.service.impl;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.core.attach.entity.CoreAttach;
import com.sensebling.core.attach.service.CoreAttachSvc;
@Service
public class CoreAttachSvcImpl extends BasicsSvcImpl<CoreAttach> implements CoreAttachSvc,ApplicationContextAware{

	private ApplicationContext applicationContext;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
	@Override
	public Result saveAttach(String applyid, String dicttype, String dictcode, String ids, String checkAction) {
		Result r = new Result();
		Result res = checkAction(checkAction, applyid);
		if(res==null || res.isSuccess()) {
			String sql = "update sen_core_attach a set a.dictcode=?,a.dicttype=?,a.applyid=? where a.id in ("+StringUtil.change_in(ids)+") and a.dictcode is null and a.dicttype is null and a.applyid is null";
			baseDao.executeSQL(sql, dictcode, dicttype, applyid);
			r.success();
		}else {
			r.setError(res.getError());
		}
		return r;
	}

	private Result checkAction(String checkAction, String applyid) {
		if(StringUtil.notBlank(checkAction) && checkAction.trim().split("\\.").length==2) {
			String beanName = checkAction.trim().split("\\.")[0].trim();
			String method = checkAction.trim().split("\\.")[1].trim();
			Object obj = applicationContext.getBean(beanName);
			try {
				Method m = obj.getClass().getMethod(method, String.class);
				return (Result)m.invoke(obj, applyid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Result delAttach(String applyid, String ids, String checkAction) {
		Result r = new Result();
		Result res = checkAction(checkAction, applyid);
		if(res==null || res.isSuccess()) {
			String sql = "delete from sen_core_attach a where a.id in ("+StringUtil.change_in(ids)+") and a.applyid=?";
			baseDao.executeSQL(sql, applyid);
			
			r.success();
		}else {
			r.setError(res.getError());
		}
		return r;
	}
	@Override
	public String saveAttach(CoreAttach coreAttach) {
		return null;
		
	}

}
