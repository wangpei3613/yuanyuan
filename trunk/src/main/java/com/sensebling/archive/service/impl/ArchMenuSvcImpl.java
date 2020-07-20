package com.sensebling.archive.service.impl;

import com.sensebling.common.util.QueryParameter;
import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.archive.entity.ArchMenu;
import com.sensebling.archive.service.ArchMenuSvc;

import java.util.List;

@Service
public class ArchMenuSvcImpl extends BasicsSvcImpl<ArchMenu> implements ArchMenuSvc{

    @Override
    public List<ArchMenu> findParentMenuListBySql(String mids) {
        StringBuffer sb = new StringBuffer("WITH  RPL (pid, id, menu_name,ordernumber) AS (" )
                .append("SELECT ROOT.pid, ROOT.id, ROOT.menu_name, ROOT.ordernumber FROM ARCH_MENU ROOT WHERE ROOT.id in (?)")
                .append(" UNION  ALL SELECT CHILD.pid, CHILD.id, CHILD.menu_name, CHILD.ordernumber FROM RPL PARENT, ARCH_MENU CHILD WHERE PARENT.pid = CHILD.id  )")
                .append("SELECT DISTINCT pid, id, menu_name,ordernumber FROM RPL ORDER BY ordernumber,pid, id, menu_name");
        return this.baseDao.findBySQLEntity(sb.toString(),new Object[]{mids});
    }
}