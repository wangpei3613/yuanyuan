package com.sensebling.archive.service.impl;

import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.activiti.service.ActApplyInfoSvc;
import com.sensebling.archive.entity.ArchBorrow;
import com.sensebling.archive.entity.ArchReel;
import com.sensebling.archive.service.ArchBorrowSvc;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sensebling.common.controller.HttpReqtRespContext.getUser;

@Service("archBorrowSvc")
public class ArchBorrowSvcImpl extends BasicsSvcImpl<ArchBorrow> implements ArchBorrowSvc{
    @Resource
    private ArchReelSvc archreelsvc;
    @Resource
    private ActApplyInfoSvc actApplyInfoSvc;
    @Override
    public Result jieYueDa(ArchBorrow v,Result r) throws Exception{
        //新增借阅表案件
        v.setAddtime(DateUtil.getStringDate());
        v.setAdduser(getUser().getId());
        //借阅人
        v.setBrow_user(getUser().getId());
        v.setApply_date(DateUtil.getStringDate());
        //申请编号
        v.setApply_no(DateUtil.formatDateTimeString(new Date()));
        //借阅状态为已借阅
        v.setBrow_status("1");
        this.baseDao.save(v);
        //将档案表里面的状态改为借阅
        if(StringUtil.notBlank(v.getArch_reel_id())){
            ArchReel reel = archreelsvc.get(v.getArch_reel_id());
            String rstate = "";
            rstate = reel.getReel_status();
            reel.setReel_status("5");
            archreelsvc.update(reel);
            if(actApplyInfoSvc.checkActivitiConfig(ProtocolConstant.activitiProcessCode.BORROW)) {
                //新增提交流程申请
                actApplyInfoSvc.newApplyAndSubmit(v.getId(),ProtocolConstant.activitiProcessCode.BORROW,getUser(),r,rstate);
            }else {
                r.setError("流程配置有误，请联系管理员");
            }
        }
        return r;
    }

    @Override
    public String guiHuanDa(String id) {
        if(StringUtil.notBlank(id)){
            //修改借阅表案件状态
            ArchBorrow archborrow = this.baseDao.get(id);
            if(StringUtil.notBlank(archborrow)){
                //如果当前事件晚于归还时间，则状态改为延期归还
                if(DateUtil.getDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())).getTime()>=DateUtil.getDate(archborrow.getBack_date()).getTime()){
                    archborrow.setBrow_status("4");
                }else{
                    archborrow.setBrow_status("2");
                }
                archborrow.setReal_back_date(DateUtil.formatDate(new Date()));
                archborrow.setUpdatetime(DateUtil.getStringDate());
                archborrow.setUpdateuser(getUser().getId());
                this.baseDao.update(archborrow);
                //修改档案状态
                ArchReel reel = archreelsvc.get(archborrow.getArch_reel_id());
                if(StringUtil.notBlank(reel)){
                    reel.setReel_status("3");
                    reel.setUpdatetime(DateUtil.getStringDate());
                    reel.setUpdateuser(getUser().getId());
                    archreelsvc.update(reel);
                }
            }

        }
        return "归还成功";
    }

    @Override
    public Pager findAuditPageBySql(QueryParameter qp) {
        StringBuffer sb = new StringBuffer("select b.*,r.reel_name,r.reel_no,a.*,u1.nickname as brow_name,u2.nickname as apply_name,u3.nickname as sendMan from v_act_apply_todo a join arch_borrow b on b.id=a.caseid  left join arch_reel r on r.id = b.arch_reel_id left join sen_user u1 on u1.id = b.brow_user left join sen_user u2 on u2.id = b.adduser left join sen_user u3 on u3.id = a.sent_usercode where a.audit_state='2' and");
        sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
        return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ArchBorrow.class.getName());
    }

    @Override
    public Pager findSearchPageBySql(QueryParameter qp) {
        StringBuffer sb = new StringBuffer("select b.*,r.reel_name,r.reel_no,u1.nickname as brow_name,u2.nickname as apply_name,p.audit_state,p.id as applyid from arch_borrow b left join arch_reel r on r.id = b.arch_reel_id left join sen_act_apply_info p on p.caseid=b.id left join sen_user u1 on u1.id = b.brow_user left join sen_user u2 on u2.id = b.adduser where 1=1 and ");
        sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
        return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), ArchBorrow.class.getName());
    }

    @Override
    public Map<String,Integer> getTongJiBySql() {
        StringBuffer sb = new StringBuffer("select b.brow_status from arch_borrow b left join arch_reel r on r.id = b.arch_reel_id left join sen_act_apply_info p on p.caseid=b.id left join sen_user u1 on u1.id = b.brow_user left join sen_user u2 on u2.id = b.adduser where 1=1 and p.audit_state='3' ");
        List<ArchBorrow> alist = baseDao.findBySQLEntity(sb.toString());
        Map<String,Integer> map = new HashMap<>();
        if(StringUtil.notBlank(alist)){
            map.put("guihuanTotal",(int) alist.stream().filter(s-> "2".equals(s.getBrow_status()) || "4".equals(s.getBrow_status())).count());//已归还
            map.put("anshiguihuanTotal", (int) alist.stream().filter(s-> "2".equals(s.getBrow_status())).count());//按时归还
            map.put("yuqiguihuanTotal",(int) alist.stream().filter(s-> "4".equals(s.getBrow_status())).count());//逾期归还
            map.put("weiguihuanTotal",(int) alist.stream().filter(s-> "1".equals(s.getBrow_status()) || "3".equals(s.getBrow_status())).count());//未归还
            map.put("qixianneiweiguihuanTotal",(int) alist.stream().filter(s-> "1".equals(s.getBrow_status())).count());//期限内未归还
            map.put("yuqiweiguihuanTotal",(int) alist.stream().filter(s-> "3".equals(s.getBrow_status())).count());//逾期未归还
        }
        return map;
    }

    @Override
    public Pager findBorrowPageBySql(QueryParameter qp) {
        StringBuffer sb = new StringBuffer("select b.*,r.reel_name,r.reel_no,u1.nickname as brow_name,u2.nickname as apply_name,p.audit_state,p.id as applyid from arch_borrow b left join arch_reel r on r.id = b.arch_reel_id left join sen_act_apply_info p on p.caseid=b.id left join sen_user u1 on u1.id = b.brow_user left join sen_user u2 on u2.id = b.adduser where 1=1 and ");
        List<Object> params = new ArrayList<>();
        if(StringUtil.notBlank(qp.getParam("begindate"))){
            sb.append(" b.back_date >= ? and ");
            params.add(qp.getParam("begindate"));
        }
        if(StringUtil.notBlank(qp.getParam("enddate"))){
            sb.append(" b.back_date <= ? and ");
            params.add(qp.getParam("enddate"));
        }
        qp.removeParamter("begindate");
        qp.removeParamter("enddate");
        sb.append(qp.transformationCondition(null)+qp.getOrderStr(null));
        return baseDao.querySQLPageEntity(sb.toString(), qp.getPager().getPageSize(), qp.getPager().getPageIndex(), params);
    }

    @Override
    public void auditCallAfter(ActAudit act) {
        //如果否决，则将借阅状态改为
        if("3".equals(act.getResult())){
            if(StringUtil.notBlank(act) && StringUtil.notBlank(act.getApply()) && StringUtil.notBlank(act.getApply().getCaseid())){
                ArchBorrow borrow = get(act.getApply().getCaseid());
                if(StringUtil.notBlank(borrow) && StringUtil.notBlank(borrow.getArch_reel_id())){
                    ArchReel reel = archreelsvc.get(borrow.getArch_reel_id());
                    if(StringUtil.notBlank(reel)){
                        reel.setReel_status(act.getApply().getJson());
                        archreelsvc.save(reel);
                    }
                }
            }
        }
    }
}