package com.sensebling.archive.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.archive.entity.ArchRoomRackBox;

import java.util.List;

public interface ArchRoomRackBoxSvc extends BasicsSvc<ArchRoomRackBox>{
    /**
     * 根据档案架id查询盒列表
     * @param rackid
     * @return
     */
    List<ArchRoomRackBox> getListByRackid(String rackid);
}