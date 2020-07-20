package com.sensebling.archive.controller;


import com.sensebling.activiti.entity.ActAudit;
import com.sensebling.activiti.entity.ActConfigTache;
import com.sensebling.activiti.service.ActApplyInfoSvc;
import com.sensebling.archive.entity.ArchBorrow;
import com.sensebling.archive.entity.ArchReel;
import com.sensebling.archive.entity.ArchReelType;
import com.sensebling.archive.service.ArchBorrowSvc;
import com.sensebling.archive.service.ArchReelSvc;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/arch/reel/search")
public class ArchBorrowCtrl extends BasicsCtrl{
	@Resource
	private ArchBorrowSvc archBorrowSvc;
	@Resource
	private ArchReelSvc archreelsvc;
	@Resource
	private ActApplyInfoSvc actApplyInfoSvc;
	
	@RequestMapping("/toView")
	public String toView() {
		return "/arch/search/view";
	}
	@RequestMapping("/getPager")
	@ResponseBody
	public Result getPager(){
		Result r = new Result();
		r.setData(archBorrowSvc.findPage(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toEdit")
	public String toEdit(String id,String arch_reel_id,String reel_name) {
		ArchBorrow v = new ArchBorrow();
		if(StringUtil.notBlank(id)) {
			v = archBorrowSvc.get(id);
		}else{
			v.setArch_reel_id(arch_reel_id);
			v.setReel_name(reel_name);
		}
		getRequest().setAttribute("v", v);
		return "/arch/search/edit";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(ArchBorrow v){
		Result r = new Result();
		if(StringUtil.notBlank(v.getId())) {
			v.setUpdatetime(DateUtil.getStringDate());
			v.setUpdateuser(getUser().getId());
			archBorrowSvc.update(v);
		}else {
			v.setAddtime(DateUtil.getStringDate());
			v.setAdduser(getUser().getId());
			archBorrowSvc.save(v);
		}
		r.success();
		return crudError(r);
	}
	@RequestMapping("/del")
	@ResponseBody
	public Result del(String id){
		Result r = new Result();
		archBorrowSvc.delete(id);
		r.success();
		return crudError(r);
	}

	/**
	 * 借阅
	 * @param v
	 * @return
	 */
	@RequestMapping("/jieyue")
	@ResponseBody
	public Result jieyue(ArchBorrow v) throws Exception{
		Result r = new Result();
		r = archBorrowSvc.jieYueDa(v,r);
		r.success();
		return crudError(r);
	}
	@RequestMapping("/toJieyueView")
	public String toJieyueView() {
		return "/arch/search/borrowView";
	}

	@RequestMapping("/toReelView")
	public String toReelView() {
		return "/arch/search/reelView";
	}

	/**
	 * 新增案卷借阅选择案卷下拉框
	 * @return
	 */
	@RequestMapping("/getDaList")
	@ResponseBody
	public List<Map<String,String>> getDaList() {
		List<Map<String,String>> temp=new ArrayList<Map<String,String>>();
		List<ArchReel> datas=archreelsvc.findAll();
		if(datas==null)
			return temp;
		for(ArchReel content:datas){
			Map<String,String> map=new HashMap<String, String>();
			map.put("text", content.getReel_name());
			map.put("value", content.getId());
			temp.add(map);

		}
		return temp;
	}

	/**
	 * 归还
	 * @param id
	 * @return
	 */
	@RequestMapping("/guiHuan")
	@ResponseBody
	public Result guiHuan(String id){
		Result r = new Result();
		r.setData(archBorrowSvc.guiHuanDa(id));
		r.success();
		return crudError(r);
	}

	@RequestMapping("/toAuditView")
	public String toAuditView() {
		return "/arch/reelAudit/auditView";
	}

	@RequestMapping("/toSearchView")
	public String toSearchView() {
		return "/arch/reelAudit/searchView";
	}

	/**
	 *借阅审批列表
	 */
	@RequestMapping("/getAuditPager")
	@ResponseBody
	public Result getAuditPager(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("domanid", getUser().getId());
		r.setData(archBorrowSvc.findAuditPageBySql(qp));
		r.success();
		return crudError(r);
	}
	/**
	 * 借阅查询列表(管理员)
	 */
	@RequestMapping("/getSearchPagerAdmin")
	@ResponseBody
	public Result getSearchPagerAdmin(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		r.setData(archBorrowSvc.findSearchPageBySql(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 * 借阅查询列表
	 */
	@RequestMapping("/getSearchPager")
	@ResponseBody
	public Result getSearchPager(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		if(StringUtil.notBlank(getUser())){
			qp.addParamter("b.adduser",getUser().getId());
			r.setData(archBorrowSvc.findSearchPageBySql(getQueryParameter()));
		}
		r.success();
		return crudError(r);
	}

	/**
	 * 借阅统计页面
	 * @return
	 */
	@RequestMapping("/toTongji")
	public String toTongji() {
		return "/arch/tongji/tongjiView";
	}
	/**
	 * 借阅统计查询列表
	 */
	@RequestMapping("/getSearchTongJiPager")
	@ResponseBody
	public Result getSearchTongJiPager(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("p.audit_state",3);
		r.setData(archBorrowSvc.findBorrowPageBySql(getQueryParameter()));
		r.success();
		return crudError(r);
	}
	/**
	 *
	 */
	@RequestMapping("/getTongJiResult")
	@ResponseBody
	public Result getTongJiResult(){
		Result r = new Result();
		r.setData(archBorrowSvc.getTongJiBySql());
		r.success();
		return crudError(r);
	}
}
