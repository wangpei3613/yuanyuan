package com.sensebling.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.EasyTreeUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.annotation.ModuleAuth;
import com.sensebling.system.entity.Department;
import com.sensebling.system.service.DeptSvc;
import com.sensebling.system.service.UserDeptSvc;
import com.sensebling.system.service.UserSvc;

/**
 * 系统操作相关的controller
 * 部门
 * @author
 * 
 */
@Controller
@RequestMapping("/system/depart")
public class DeptCtrl extends BasicsCtrl {

	@Resource
	private DeptSvc departmentService;
	
	@Resource
	private UserSvc userService;
	
	@Resource
	private UserDeptSvc userDeptSvc;
	
	

	/**
	 * 查询grid树和下拉树
	 */
	@RequestMapping("/getDeptTree")
	@ResponseBody
	public List<Map<String,Object>> getDeptTree(String multSelect){
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		QueryParameter qp = new QueryParameter();
		qp.setSortField("orderIndex");
		qp.addParamter("status", "1");
		List<Department> list =  departmentService.findAll(qp);
		
		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(Department m:list) {
				map = JsonUtil.beanToMap(m);
				map.put("text", m.getFullName());
				if(StringUtil.notBlank(multSelect) && "true".equals(multSelect)) {
					map.put("checked", false);
				}
				temp.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}
	
	/**
	 * 部门视野树
	 */
	@RequestMapping("/getUserDepart")
	@ResponseBody
	public List<Map<String,Object>> getUserDepart(String userId){
		List<Map<String,Object>> temp=new ArrayList<Map<String,Object>>();
		List<Department> list =  departmentService.getUserDepart(userId);
		Map<String,Object> map = null;
		if(list!=null && list.size()>0) {
			for(Department m:list) {
				map = JsonUtil.beanToMap(m);
				map.put("text", m.getFullName());
				map.put("checked", "1".equals(m.getChecked()));
				temp.add(map);
			}
		}
		return EasyTreeUtil.toTreeGrid(temp, "id", "pid");
	}
	
	
	
	/**
	 * 删除部门
	 * @param v
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	@ModuleAuth("department:remove")
	@DisposableToken
	public Result del(String id) { 
		Result r = new Result();
		QueryParameter u_qp=new QueryParameter();
		u_qp.addParamter("deptId", id);
		QueryParameter d_qp=new QueryParameter();
		d_qp.addParamter("pid", id);
		if(userService.findAllCount(u_qp)>0) {
			r.setError("当前部门含有用户，不能删除");
		}else if(departmentService.findAllCount(d_qp)>0){
			r.setError("当前部门含有子部门，不能删除");
		}else {
			departmentService.deleteByIds(id, false);
			r.success();
		}
		return crudError(r);
	}
	
	
	/**
	 * 新增or修改菜单
	 * @param v
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@ModuleAuth({"department:openAdd","department:openEdit"})
	@DisposableToken
	public Result save(Department v) { 
		Result r = new Result();
		if(departmentService.checkCode(v.getDeptCode(),v.getId())) {
			if(StringUtil.isBlank(v.getId())) {  
				v.setCreateDate(DateUtil.getStringDate());
				v.setCreateUser(getUser().getId());
				departmentService.save(v);
			}else {
				v.setUpdateDate(DateUtil.getStringDate());
				v.setUpdateUser(getUser().getId());
				departmentService.update(v);
			}
			r.success();
		}else {
			r.setError("部门编码重复");
		}
		return crudError(r);
	}
	
	/**
	 * 部门视野分配
	 * @param v
	 * @return
	 */
	@RequestMapping("/saveUserDept")
	@ResponseBody
	@DisposableToken
	public Result saveUserDept(String userId,String ids) { 
		Result r = new Result();
		userDeptSvc.updateUserDeprt(userId, ids, getUser().getId());
		r.success();
		return crudError(r);
	}
	/**
	 * 获取部门列表  用于字典
	 * @param status 状态
	 */
	@RequestMapping(value="/getDeptDict")
	@ResponseBody
	public Result getDeptDict(String status) {
		Result r = new Result();
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		List<Department> list = departmentService.findAll();
		if(list!=null && list.size()>0) {
			Map<String, Object> map = null;
			for(Department dept:list) {
				map = new HashMap<String, Object>();
				map.put("code", dept.getId());
				map.put("name", dept.getFullName());
				temp.add(map);
			}
		}
		r.setData(temp);
		r.success();
		return crudError(r);
	}
	/**
	 * 进入部门管理页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/sys/department/index";
	}
	/**
	 * 进入部门编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEdit(String id) {
		Department dept = new Department();  
		if(StringUtil.notBlank(id)) {
			dept = departmentService.get(id);
		}else {
			dept.setPid(getRequestParam("pid"));  
		}
		getRequest().setAttribute("v", dept);  
		return "/sys/department/edit";
	}
	/**
	 * 进入部门视野分配页面
	 * @return
	 */
	@RequestMapping("/toAllotDept")
	public String toAllotDept() {
		return "/sys/userManage/allotDept";
	}
}