package com.sensebling.archive.service;

import com.sensebling.archive.entity.ArchDestroy;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;

import java.util.List;

public interface ArchDestroySvc extends BasicsSvc<ArchDestroy>{

    String xiaoHui(ArchDestroy archdestroy);

    Pager findPageBysql(QueryParameter qp);

    public List<ArchDestroy> getDestroyList(QueryParameter qp);
}