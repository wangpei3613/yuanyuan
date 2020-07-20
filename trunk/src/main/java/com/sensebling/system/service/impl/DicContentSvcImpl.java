package com.sensebling.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.MyServiceException;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.DictionaryContent;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.DicContentSvc;

@Service("dicContentSvc")
public class DicContentSvcImpl extends BasicsSvcImpl<DictionaryContent> implements DicContentSvc {

	public List<DictionaryContent> findAllCascade() {
		List<DictionaryContent> list =baseDao.findAllOpen();
		return list;
	}

	public List<DictionaryContent> findByTypeCode(String typeId) {
		StringBuffer hql = new StringBuffer("from DictionaryContent d where  d.typeId = ? and d.status='1' order by d.ordNumber ");
		List<Object> list = new ArrayList<Object>();
		list.add(typeId);
		
		return this.baseDao.find(hql.toString(), list);
	}

	/**
	 * 保存数据
	 * YF
	 * @param map
	 * @throws Exception
	 * 2018-1-21-下午03:50:57
	 */
	@Override
	public int saveDicContent(Map<String, Object> map) throws Exception {
		String ids  = StringUtil.sNull(map.get("id"));
		User user = (User) map.get("user");
		String typeId = StringUtil.sNull(map.get("typeId"));
		if("".equals(typeId))throw new MyServiceException("请添加数据字典表");
		StringBuffer sql = new StringBuffer();
		String dictionaryCode=StringUtil.sNull(map.get("dictionaryCode"));
		String dictionaryName=StringUtil.sNull(map.get("dictionaryName"));
		String status=StringUtil.sNull(map.get("status"));
		String ordNumber=StringUtil.sNull(map.get("ordNumber"));
		String remark=StringUtil.sNull(map.get("remark"));
		String dtypeCode=StringUtil.sNull(map.get("dtypeCode"));
		if(!"".equals(ids)){
			sql.append("update SEN_DICTIONARY_CONTENT set dictionaryCode='"+dictionaryCode+"',dictionaryName='")
			.append(dictionaryName+"',status='"+status+"',ordNumber="+ordNumber)
			.append(",remark='"+remark+"',typeId='")
			.append(typeId+"'")
			.append(" where id='"+ids+"'")
			;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String times = sdf.format(new Date());
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			sql.append("insert into SEN_DICTIONARY_CONTENT")
			.append("(")
			.append("ID,CREATEDATE,CREATEUSER,DICTIONARYCODE,DICTIONARYNAME,")
			.append("DTYPECODE,ORDNUMBER,REMARK,STATUS,TYPEID")
			.append(")")
			.append(" values ")
			.append("(")
			.append("'"+uuid+"','"+times+"','"+user.getId()+"','"+dictionaryCode+"','"+dictionaryName+"','")
			.append(dtypeCode+"',"+ordNumber+",'"+remark+"','"+status+"','"+typeId+"'")
			.append(")")
			;
		}
		this.baseDao.executeSQL(sql.toString());
		return 0;
	}

	/**
	 * 使用sql根据id删除信息
	 * YF
	 * @param map
	 * @return
	 * @throws Exception
	 * 2018-1-23-上午11:26:18
	 */
	@Override
	public int deleteDicContentById(Map<String, Object> map) throws Exception {
		if(null!=map&&!map.isEmpty()){
			String ids = StringUtil.sNull(map.get("ids"));
			StringBuffer sql = new StringBuffer();
			if(!"".equals(ids)){
				String[] id = ids.split(",",-1);
				sql.append("delete from SEN_DICTIONARY_CONTENT where id in (");
				for (int i = 0; i < id.length-1; i++) {
					sql.append("'"+id[i]+"',");
				}
				sql.append("'"+id[id.length-1]+"')");
				baseDao.executeSQL(sql.toString());
			}
		}
		return 0;
	}

	
}
