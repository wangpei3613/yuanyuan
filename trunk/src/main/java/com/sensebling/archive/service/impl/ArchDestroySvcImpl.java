package com.sensebling.archive.service.impl;

import com.sensebling.archive.entity.ArchDestroy;
import com.sensebling.archive.service.ArchDestroySvc;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("archDestroySvc")
public class ArchDestroySvcImpl extends BasicsSvcImpl<ArchDestroy> implements ArchDestroySvc{
    @Resource
    private ArchReelSvc archreelsvc;
    @Override
    public String xiaoHui(ArchDestroy archdestroy) {
        if(StringUtil.notBlank(archdestroy.getArch_reel_id())){
            //将档案保存到销毁表里面
            this.baseDao.save(archdestroy);
            //删除档案表里面的档案
            archreelsvc.delete(archdestroy.getArch_reel_id());
            return "销毁成功";
        }else{
            return "参数有问题";
        }
    }

    @Override
    public Pager findPageBysql(QueryParameter qp) {
        StringBuffer sql = new StringBuffer("select d.*,b.box_no as box_name,m.menu_name as mulu_name,u1.nickname as zx_name from arch_destroy d left join arch_room_rack_box b on d.arch_room_rack_box_id = b.id left join arch_menu m on m.id = d.arch_menu_id left join sen_user u1 on u1.id = d.desty_user where ");
        sql.append(qp.transformationCondition(null)+qp.getOrderStr(null));
        Pager p = this.baseDao.querySQLPageEntity(sql.toString(),qp.getPager().getPageSize(),qp.getPager().getPageIndex(),ArchDestroy.class.getName());
        return p;
    }
    @Override
    public List<ArchDestroy> getDestroyList(QueryParameter qp) {
        StringBuffer sql = new StringBuffer("select d.*,b.box_no as box_name,m.menu_name as mulu_name,u1.nickname as zx_name from arch_destroy d left join arch_room_rack_box b on d.arch_room_rack_box_id = b.id left join arch_menu m on m.id = d.arch_menu_id left join sen_user u1 on u1.id = d.desty_user where ");
        sql.append(qp.transformationCondition(null)+qp.getOrderStr(null));
        return this.baseDao.findBySQLEntity(sql.toString(),ArchDestroy.class.getName());
    }
}