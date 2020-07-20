package com.sensebling.archive.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.archive.entity.ArchReelFileMaterial;
import com.sensebling.common.util.Result;

public interface ArchReelFileMaterialSvc extends BasicsSvc<ArchReelFileMaterial>{
    /**
     * 获取顺序
     * @param applyid
     * @return
     */
    Integer getSerial(String applyid);

}