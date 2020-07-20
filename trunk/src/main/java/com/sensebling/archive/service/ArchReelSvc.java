package com.sensebling.archive.service;

import com.sensebling.archive.entity.ArchReel;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;

public interface ArchReelSvc extends BasicsSvc<ArchReel>{

    String shangJia(String id,String boxid);

    String xiaJia(String id);

    Pager findPageBysql(QueryParameter qp,String userid);

    Pager findPartPageBysql(QueryParameter qp,String userid);
}