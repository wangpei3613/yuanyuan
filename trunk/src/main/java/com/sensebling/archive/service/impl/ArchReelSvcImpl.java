package com.sensebling.archive.service.impl;

import com.sensebling.archive.entity.ArchReel;
import com.sensebling.archive.entity.ArchRoomRackBox;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.archive.service.ArchRoomRackBoxSvc;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArchReelSvcImpl extends BasicsSvcImpl<ArchReel> implements ArchReelSvc{
    @Resource
    private ArchRoomRackBoxSvc archRoomRackBoxSvc;
    @Override
    public String shangJia(String id, String boxid) {
        if(StringUtil.notBlank(id) && StringUtil.notBlank(boxid)){
            ArchReel reel = this.baseDao.get(id);
            QueryParameter qp = new QueryParameter();
            qp.addParamter("arch_room_rack_box_id",boxid);
            //查询该档案盒里面有多少个档案
            int count = this.baseDao.findAllCount(qp);
            //查询该档案盒里面能放多少档案
            ArchRoomRackBox box =archRoomRackBoxSvc.get(boxid);
            //如果档案盒已满
            if(StringUtil.notBlank(box)){
                if(count>=box.getBox_num()){
                    return "档案盒已满";
                }else{
                    //修改档案状态
                    reel.setReel_status("3");
                    reel.setArch_room_rack_box_id(boxid);
                    this.baseDao.update(reel);
                    return "上架成功";
            }
            }else{
                return "档案盒不存在";
            }
        }else{
            return "参数有问题";
        }
    }

    @Override
    public String xiaJia(String id) {
        if(StringUtil.notBlank(id)){
            ArchReel reel = this.baseDao.get(id);
            //修改档案状态
            reel.setReel_status("4");
            reel.setArch_room_rack_box_id("");
            this.baseDao.update(reel);
            return "下架成功";
        }else{
            return "参数有问题";
         }
    }

    @Override
    public Pager findPageBysql(QueryParameter qp,String userid) {
        StringBuffer sql = new StringBuffer("select r.*,b.box_no from arch_reel r left join")
                .append(" arch_room_rack_box b on r.arch_room_rack_box_id = b.id  " )
                .append(" join arch_reel_type_role role on role.reeltypeid = r.reel_type join sen_user_role ur on ur.roleid=role.roleid where" )
                .append(" ur.userid=? " )
                .append(" and arch_menu_id=? order by r.id ");
        Pager  p = this.baseDao.querySQLPageEntity(sql.toString(),qp.getPager().getPageSize(),qp.getPager().getPageIndex(),ArchReel.class.getName(),new Object[]{userid,qp.getParam("arch_menu_id")});
        return p;
    }

    @Override
    public Pager findPartPageBysql(QueryParameter qp, String userid) {
        StringBuffer sql = new StringBuffer("select r.*,b.box_no from arch_reel r left join")
                .append(" arch_room_rack_box b on r.arch_room_rack_box_id = b.id  " )
                .append(" join arch_reel_type_role role on role.reeltypeid = r.reel_type join sen_user_role ur on ur.roleid=role.roleid where r.file_date is not null and r.file_date !='' and " )
                .append(" ur.userid=? " )
                .append(" and arch_menu_id=? order by r.id ");
        Pager  p = this.baseDao.querySQLPageEntity(sql.toString(),qp.getPager().getPageSize(),qp.getPager().getPageIndex(),ArchReel.class.getName(),new Object[]{userid,qp.getParam("arch_menu_id")});
        return p;
    }
}