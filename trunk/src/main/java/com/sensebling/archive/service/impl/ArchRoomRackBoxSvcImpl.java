package com.sensebling.archive.service.impl;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.archive.entity.ArchRoomRackBox;
import com.sensebling.archive.service.ArchRoomRackBoxSvc;

import java.util.List;

@Service
public class ArchRoomRackBoxSvcImpl extends BasicsSvcImpl<ArchRoomRackBox> implements ArchRoomRackBoxSvc{
    @Override
    public List<ArchRoomRackBox> getListByRackid(String rackid) {
        String sql = "select a.*,nvl(c.num, 0) reelnum from arch_room_rack_box a left join( select b1.arch_room_rack_box_id, count(1) num from arch_reel b1 join arch_room_rack_box b2 on b2.id=b1.arch_room_rack_box_id where b2.arch_room_rack_id=? group by b1.arch_room_rack_box_id) c on c.arch_room_rack_box_id=a.id where a.arch_room_rack_id=?";
        return baseDao.findBySQLEntity(sql, ArchRoomRackBox.class, rackid, rackid);
    }
}