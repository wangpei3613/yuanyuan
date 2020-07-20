package com.sensebling.archive.service;

import com.sensebling.archive.entity.ArchMenu;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.QueryParameter;

import java.util.List;

public interface ArchMenuSvc extends BasicsSvc<ArchMenu>{
    List<ArchMenu> findParentMenuListBySql(String mids);
}