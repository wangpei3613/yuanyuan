package com.sensebling.system.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.MyServiceException;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.AppVersion;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.AppVersionSvc;
@Service("appVersionSvc")
public class AppVersionSvcImpl extends BasicsSvcImpl<AppVersion> implements AppVersionSvc{

	@Override
	public void addAppVersion(AppVersion d,User u) throws Exception {
		if(null!=d) {
			String id = StringUtil.sNull(d.getId());
			String  currentV = StringUtil.sNull(d.getCurrentV());
			if("".equals(currentV))throw new MyServiceException("当前版本不能为空");
			String path = BasicsFinal.getParamVal("appdownurl")+"/tz"+d.getCurrentV()+".apk";
			d.setDownUrls(path);
			d.setOpNo(u.getId());
			d.setOpName(u.getNickName());
			d.setOpTime(new Timestamp(System.currentTimeMillis()));
			if("".equals(id)) {
				baseDao.save(d);
			}else {
				baseDao.merge(d);
			}
		}
		
	}

	/**
	 * 按照时间倒叙获得一条信息
	 * @return
	 * @throws Exception
	 * 2018年7月11日-下午2:08:53
	 * YY
	 */
	@Override
	public AppVersion getListByTime(int type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SEN_APP_VERSION where type=? order by OPTIME desc fetch first 1 rows only");
		List<Object> parm = new ArrayList<Object>();
		parm.add(type);
		List<AppVersion> list= this.baseDao.findBySQLEntity(sql.toString(), parm);
		if(null!=list&&!list.isEmpty())
			return list.get(0);
		return null;
	}

}
