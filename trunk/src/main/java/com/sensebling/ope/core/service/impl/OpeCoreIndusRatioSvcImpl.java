package com.sensebling.ope.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.ope.core.entity.OpeCoreIndusRatio;
import com.sensebling.ope.core.service.OpeCoreIndusRatioSvc;
@Service
public class OpeCoreIndusRatioSvcImpl extends BasicsSvcImpl<OpeCoreIndusRatio> implements OpeCoreIndusRatioSvc{

	/**
	 * 拼装指标树数据
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> getTreeGrid(List<OpeCoreIndusRatio> list){
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = null;
		if(null != list && list.size()>0) {
			for(OpeCoreIndusRatio r:list) {
				map = JsonUtil.beanToMap(r);
				map.put("text", r.getName());
				map.put("iconCls", StringUtil.isBlank(r.getParentCode())?"menumanagetreegrid-parent":"menumanagetreegrid-leaf");
				map.put("expanded", false);
				temp.add(map);
			}
		}
		temp = EasyTreeUtil.toTreeGrid(temp, "id", "parentCode");
		if(temp != null && temp.size()>0) {
			calcState(temp);
		}
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	private void calcState(List<Map<String, Object>> temp) {
		for(Map<String, Object> m:temp) {
			if(m.containsKey("children")) {
				m.put("state", "closed");
				calcState((List<Map<String, Object>>) m.get("children"));
			}
		}
	}

	/**
	 * 判断行业code是否存在
	 * @return
	 */
	public boolean checkCode(String code,String id) {
		StringBuffer sql = new StringBuffer("select 1 from ope_core_industryratio t where t.code=?");
		List<Object> param = new ArrayList<Object>();
		param.add(code);
		if(StringUtil.notBlank(id)) {
			sql.append(" and t.id != ?");
			param.add(id);
		}
		List<OpeCoreIndusRatio> list = this.baseDao.findBySQL(sql.toString(), param);
		if(null == list || list.size() == 0) {
			return true;
		}else {
			return false;
		}
	}
}
