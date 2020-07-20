package com.sensebling.system.service;

import java.util.List;

import com.sensebling.common.service.BasicsSvc;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.system.entity.Employee;

public interface EmpSvc extends BasicsSvc<Employee> {
	/**
	 * 查询所有人员信息
	 */
	public List<Employee> findAllEmp();
	
	//插入人员
	public void addEmp(Employee em) ;
	
	//删除一条数据
	public void delEmp(String...ids);
	/**
	 * 根据部门id查询人员信息
	 * @param departId 部门id
	 * @return
	 */
	public List<Employee> getAllEmpByDeprtId(String departId);
	/**
	 * 删除所属部门时 修改人员部门id为空
	 * @param departId
	 */
	public void updateEmp(String departId,String pid);
	/**
	 * 根据工号获取员工
	 * @author  
	 * @date Jan 9, 2015 2:23:52 PM
	 * @param empCode
	 * @return
	 */
	public Employee getEmpByParams(List<Object> params);

	/**
	 * 查询是主管且在职的员工和已添加的主行员
	 * @param empCode
	 * @param empName
	 * @param sidx
	 * @param sord
	 * @param pager
	 * @return
	 */
	public Pager querySatrapEmp(String empCode, String empName, String sidx,
			String sord, Pager pager);

	/**
	 * 根据sql查询员工
	 * @param sql
	 * @param empCode
	 * @param departId
	 * @param empName
	 * @param areaId
	 * @param sidx
	 * @param sord
	 * @param pager
	 * @return
	 */
	public Pager getEmpBySql(String sql, String empCode, String departId,
			String empName, String areaId, String sidx, String sord, Pager pager);
	/**
	 * 加载考核对象中指定的员工信息
	 * @param objectId  指标对象id
	 * @return
	 */
	public List<Employee> findObjChosenEmp(String objectId,QueryParameter qp);
	
	/**
	 * 员工考核管理 查询分页
	 * @param employeeService 
	 * @return
	 */
	public Pager getEvalEmp(QueryParameter queryParameter,String eId);
	/**
	 * 部门考核管理 查询分页
	 * @param employeeService 
	 * @return
	 */
	public Pager getEvalDept(QueryParameter queryParameter,String eId);
	/**
	 * 考核对象中，选择个体时，加载已选中员工信息
 	 * @param 
	 * @return
	 */
	public List<Employee> findEmpInObj(QueryParameter qp);
}
