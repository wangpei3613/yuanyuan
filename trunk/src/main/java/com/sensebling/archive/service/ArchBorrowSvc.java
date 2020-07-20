package com.sensebling.archive.service;

import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.archive.entity.ArchBorrow;
import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;

import java.util.Map;

public interface ArchBorrowSvc extends BasicsSvc<ArchBorrow>{

    Result jieYueDa(ArchBorrow v,Result r) throws Exception;

    String guiHuanDa(String id);

    Pager findAuditPageBySql(QueryParameter qp);

    Pager findSearchPageBySql(QueryParameter qp);

    Map<String,Integer> getTongJiBySql();

    Pager findBorrowPageBySql(QueryParameter qp);

    /**
     * 审批后回调
     * @param act
     */
    void auditCallAfter(ActAudit act);
}