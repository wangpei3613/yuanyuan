package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.MyServiceException;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.DictionaryType;
import com.sensebling.system.service.DicTypeSvc;

@Service("dicTypeSvc")
public class DicTypeSvcImpl extends BasicsSvcImpl<DictionaryType> implements DicTypeSvc {

	public List<DictionaryType> findAllCascade() {
		List<DictionaryType> list =baseDao.findAllOpen();
		return list;
	}
	
	/**
	 * 检查
	 * YF
	 * @param code 
	 * @param id
	 * @return
	 * 2017-12-16-下午03:48:02
	 */
	@Override
	public boolean checkCode(String code, String id)throws Exception {
		StringBuffer sql = new StringBuffer();
		code = StringUtil.sNull(code);
		id = StringUtil.sNull(id);
		if("".equals(code)&&"".equals(id))throw new MyServiceException();
		sql.append("select 1 from SEN_DICTIONARY_TYPE where 1=1");
		List<Object> params = new ArrayList<Object>();
		
		if(!"".equals(code)){
			sql.append(" and code = ?")
			;
			params.add(code);
		}
		if(!"".equals(id)){
			sql.append(" and id !=?")
			;
			params.add(id);
		}
		List<DictionaryType> list = baseDao.findBySQLEntity(sql.toString(), params, DictionaryType.class.getName());
		if(null==list||list.isEmpty())return true;
		
		return false;
	}

	@Override
	public void delDictType(String id) {
		String sql = "delete from sen_dictionary_type a where a.id=?";
		baseDao.executeSQL(sql, id);
		sql = "delete from sen_dictionary_content a where a.typeid=?";
		baseDao.executeSQL(sql, id);
	}

	@Override
	public Pager queryPaper(QueryParameter qp) {
		StringBuffer sql = new StringBuffer("select * from sen_dictionary_type where");
		sql.append(qp.transformationCondition(null)+qp.getOrderStr(null));
		return this.baseDao.querySQLPageEntity(sql.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), DictionaryType.class.getName());
	}
}
