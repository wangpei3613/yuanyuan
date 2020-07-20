package com.sensebling.activiti.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sensebling.activiti.entity.ActApplyTest;
import com.sensebling.activiti.service.ActApplyTestSvc;
import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
/**
 * 流程测试业务表
 *
 */
@Controller
@RequestMapping("/act/apply/test")
public class ActApplyTestCtrl extends BasicsCtrl{
	@Resource
	private ActApplyTestSvc actApplyTestSvc;
	
	/**
	 * 查询流程测试业务列表
	 * @return
	 */
	@RequestMapping(value="/select",method=RequestMethod.GET)
	@ResponseBody
	public Result select(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("applman", getUser().getId());
		r.setData(actApplyTestSvc.select(qp));
		r.success();
		return crudError(r);
	}
	/**
	 * 新增流程测试业务
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result addApply(ActApplyTest apply){
		Result r = new Result();
		r = actApplyTestSvc.addApply(apply, getUser());
		return crudError(r);
	}
	/**
	 * 修改流程测试业务
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result updateApply(ActApplyTest v){ 
		Result r = new Result();
		ActApplyTest apply = actApplyTestSvc.getApply(v.getId());
		if(apply!=null && (ProtocolConstant.activitiApplyState.state1.equals(apply.getAudit_state()) 
				|| ProtocolConstant.activitiApplyState.state4.equals(apply.getAudit_state()))) {
			apply.setCardid(v.getCardid());
			apply.setName(v.getName());
			actApplyTestSvc.update(apply);
			r.success();
		}else {
			r.setError("只有待提交和复议的申请允许修改");
		}
		return crudError(r);
	}
	/**
	 * 删除流程测试业务
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result delApply(String data){
		Result r = new Result();
		List<ActApplyTest> list = new Gson().fromJson(data, new TypeToken<List<ActApplyTest>>(){}.getType());
		if(list!=null && list.size()>0) {
			for(ActApplyTest v:list) {
				r = actApplyTestSvc.delApply(v.getId());
			}
		}
		return crudError(r);
	}
	/**
	 * 取消申请
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/cancelApply",method=RequestMethod.POST)
	@ResponseBody
	@DisposableToken
	public Result cancelApply(String id) {
		Result r = new Result();
		r = actApplyTestSvc.cancelApply(id);
		return crudError(r);
	}
	/**
	 * 查询流程测试审批列表
	 * @return
	 */
	@RequestMapping(value="/auditList",method=RequestMethod.GET)
	@ResponseBody
	public Result auditList(){
		Result r = new Result();
		QueryParameter qp = getQueryParameter();
		qp.addParamter("domanid", getUser().getId());
		r.setData(actApplyTestSvc.auditList(qp));
		r.success();
		return crudError(r);
	}
	/**
	 * 进入流程测试页面
	 * @return
	 */
	@RequestMapping("/applyIndex")
	public String applyIndex(){
		return "activiti/apply/index";
	}
	/**
	 * 进入流程测试编辑页面
	 * @return
	 */
	@RequestMapping("/applyEdit")
	public String applyEdit(String id){
		ActApplyTest v = new ActApplyTest();
		if(StringUtil.notBlank(id)) {
			v = actApplyTestSvc.get(id);
		}
		getRequest().setAttribute("v", v); 
		return "activiti/apply/edit";
	}
	/**
	 * 进入流程测试审批页面
	 * @return
	 */
	@RequestMapping("/auditIndex")
	public String auditIndex(){
		return "activiti/auditList/index";
	}
}
