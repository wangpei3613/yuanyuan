package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.system.entity.AttachmentRelation;
import com.sensebling.system.service.AttachmentRelationSvc;

@Service("attachmentRelationSvc")
@Transactional
public class AttachmentRelationSvcImpl  extends BasicsSvcImpl<AttachmentRelation> implements AttachmentRelationSvc{

	public void saveAttRelation(String relationId, String serial) {
		AttachmentRelation ar=new AttachmentRelation();
		ar.setAttachmentSerial(serial);
		ar.setRelationId(relationId);
		this.save(ar);
	}

	@Override
	public List<AttachmentRelation> findBySql(String[] paramValues) {
		int count = findCouBySql(paramValues);
		
		String sql = "select *"+
					 "	  from (select row_.*, rownum rownum_"+
					 "	          from (select distinct this_.id                as id,"+
					 "	                       this_.attachment_serial as attachment_serial,"+
					 "	                       this_.relation_id       as relation_id"+
					 "	                  from sen_attachment_relation this_"+
					 " inner join  sen_attachment_base t2 on this_.attachment_serial = t2.serial and regexp_like(t2.filetype, 'jpg|gif|png|jpeg')"+
					 "	                 where this_.relation_id in (?)) row_"+
					 "	         where rownum <= "+count+")"+
					 "	 where rownum_ in (?,?,?) order by case rownum_ when "+paramValues[1]+" then 1 when "+paramValues[2]+" then 2 when "+paramValues[3]+" then 3 end ";
		return baseDao.findBySQL(sql, paramValues);
	}

	@Override
	public int findCouBySql(String[] paramValues) {
		String sql = "select distinct this_.id as id,"+
		 "	                       this_.attachment_serial as attachment_serial,"+
		 "	                       this_.relation_id       as relation_id"+
		 "	                  from sen_attachment_relation this_"+
		 " inner join  sen_attachment_base t2 on this_.attachment_serial = t2.serial and regexp_like(t2.filetype, 'jpg|gif|png|jpeg')"+
		 "	                 where this_.relation_id in (?)";
		return baseDao.findBySQL(sql, new String[]{paramValues[0]}).size();
	}

	@Override
	public int delBySerial(String serial) {
		String sql = " delete from sen_attachment_relation where attachment_serial=?";
		return baseDao.updateBySQL(sql, serial);
	}
	@Override
	public int delByRelationId(String relationId) {
		String sql = " delete from sen_attachment_relation where relation_id=?";
		return baseDao.updateBySQL(sql, relationId);
	}
	
	/**
	 * 通过关联id获取附件中间表对象
	 * @author 凌学斌
	 * @date Jun 24, 2015 4:38:09 PM
	 * @param relationId
	 * @return
	 */
	public AttachmentRelation getAttRelationByRelation(String relationId) {
		List<Object> params = new ArrayList<Object>();
		String hql = "from AttachmentRelation where 1=1 and relationId=?";
		params.add(relationId);
		return baseDao.getByHQL(hql.toString(),params);
	}
}
