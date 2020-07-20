package com.sensebling.archive.service;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.archive.entity.ArchReelType;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.Role;

import java.util.List;

public interface ArchReelTypeSvc extends BasicsSvc<ArchReelType>{
    /**
     * 获取所有角色，并选中当前档案类别已分配的角色
     * @param typeid
     * @return
     */
    List<Role> getArchTypeRole(String typeid);

    /**
     * 给档案类别分配角色
     * @param typeid
     * @param roleIds
     */
    void addArchTypeRole(String typeid, String roleIds);
}