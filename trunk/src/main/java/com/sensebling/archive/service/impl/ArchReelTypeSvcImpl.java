package com.sensebling.archive.service.impl;

import com.sensebling.archive.entity.ArchReelTypeRole;
import com.sensebling.archive.service.ArchReelTypeRoleSvc;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Role;
import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.archive.entity.ArchReelType;
import com.sensebling.archive.service.ArchReelTypeSvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchReelTypeSvcImpl extends BasicsSvcImpl<ArchReelType> implements ArchReelTypeSvc{
    @Resource
    private ArchReelTypeRoleSvc archReelTypeRoleSvc;
    @Override
    public void addArchTypeRole(String typeid, String roleIds) {
        ArchReelType type = get(typeid);
        if (type != null) {
            String sql = "delete from arch_reel_type_role a where a.reeltypeid=?";
            baseDao.executeSQL(sql, typeid);
            if (StringUtil.notBlank(roleIds)) {
                List<ArchReelTypeRole> list = new ArrayList<>();
                for(String id : roleIds.split(",")) {
                    ArchReelTypeRole typeRole = new ArchReelTypeRole();
                    typeRole.setAddtime(DateUtil.getStringDate());
                    typeRole.setAdduser(HttpReqtRespContext.getUser().getId());
                    typeRole.setReeltypeid(typeid);
                    typeRole.setRoleid(id);
                    list.add(typeRole);
                }
                archReelTypeRoleSvc.save(list);
            }
        }
    }

    @Override
    public List<Role> getArchTypeRole(String typeid) {
        StringBuffer sql = new StringBuffer("select t1.*,decode(t2.id,null,'0','1') checked from sen_role t1 left join arch_reel_type_role t2 on t1.id=t2.roleid and t2.reeltypeid=?");
        return this.baseDao.findBySQLEntity(sql.toString(), Role.class, typeid);
    }
}