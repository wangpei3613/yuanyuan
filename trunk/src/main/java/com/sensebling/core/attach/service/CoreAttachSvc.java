package com.sensebling.core.attach.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Result;
import com.sensebling.core.attach.entity.CoreAttach;

public interface CoreAttachSvc extends BasicsSvc<CoreAttach>{
	/**
	 * 保存影像资料
	 * @param applyid
	 * @param dicttype
	 * @param dictcode
	 * @param ids
	 * @param checkAction
	 * @return
	 */
	Result saveAttach(String applyid, String dicttype, String dictcode, String ids, String checkAction);
	/**
	 * 删除影像资料
	 * @param applyid
	 * @param ids
	 * @param checkAction
	 * @return
	 */
	Result delAttach(String applyid, String ids, String checkAction);
	/**
	 * 新增影像资料
	 * @param applyid
	 * @param ids
	 * @param checkAction
	 * @return
	 */
	public String saveAttach(CoreAttach coreAttach);

}
