package com.sensebling.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.Pager;
import com.sensebling.common.util.QueryParameter;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.Employee;
import com.sensebling.system.service.EmpSvc;

@Service("EmpSvc")
public class EmpSvcImpl extends BasicsSvcImpl<Employee> implements EmpSvc{

	/**
	 * 查询所有人员信息
	 */
	public List<Employee> findAllEmp() {
		List<Employee> list = baseDao.findAllOpen();
		return list;
	}

	/**
	 * 新增人员信息
	 */
	public void addEmp(Employee em) {
		baseDao.saveOrUpdate(em);
	}

	/**
	 * 删除人员信息
	 */
	public void delEmp(String... ids) {
		baseDao.deleteByIds(ids,true);
	}
	/**
	 * 根据部门id查询人员信息
	 * @param departId 部门id
	 * @return
	 */
	public List<Employee> getAllEmpByDeprtId(String departId) {
		StringBuffer hql=new StringBuffer(" from Employee where 1=1");
		List<Object> list=new ArrayList<Object>();
		if(!StringUtil.isBlank(departId)){
			hql.append(" and departId=?");
			list.add(departId);
		}
		return baseDao.find(hql.toString(), list);
	}
	/**
	 * 删除所属部门时 修改人员部门id为空
	 * @param departId
	 */
	public void updateEmp(String departId,String pid){
		StringBuffer hql=new StringBuffer("update Employee set departId='' where 1=1");
		List<Object> list=new ArrayList<Object>();
		if(!StringUtil.isBlank(departId)){
			hql.append(" and departId=?");
			list.add(departId);
		}
		if(!StringUtil.isBlank(pid)){
			hql.append(" and departId in (select id from Department where pid=?)");
			list.add(pid);
		}
		baseDao.updateByHQL(hql.toString(), list);
	}
	
	/**
	 * 根据参数获取员工，当前只有一个工号参数
	 * @author 凌学斌
	 * @date Jan 9, 2015 2:23:52 PM
	 * @param empCode
	 * @return
	 */
	public Employee getEmpByParams(List<Object> params) {
		String hql = "from Employee t where t.empCode = ?";
		return baseDao.getByHQL(hql, params);
	}

	@Override
	public Pager querySatrapEmp(String empCode, String empName, String sidx,
			String sord, Pager pager) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (select * from sen_employee a where a.issatrap=? and a.status=? " +
				"union select c.* from ir_achieve_emp_relation_create b,sen_employee c " +
				"where b.satrapempid=c.id and c.status=? and b.branchempid is null) t where 1=1");
		List<Object> list = new ArrayList<Object>();
		list.add("1");
		list.add("1");
		list.add("1");
		if(StringUtil.notBlank(empCode)){
			sb.append(" and t.empCode like ? ");
			list.add("%"+empCode.trim()+"%");
		}
		if(StringUtil.notBlank(empName)){
			sb.append(" and t.empName like ? ");
			list.add("%"+empName.trim()+"%");
		}
		if(StringUtil.notBlank(sidx)){
			sb.append(" order by "+sidx+" "+sord);
		}
		return this.baseDao.querySQLPageEntity(sb.toString(), pager.getPageSize(), pager.getPageIndex(), "com.sensebling.system.entity.Employee",list);
	}

	@Override
	public Pager getEmpBySql(String sql, String empCode, String departId,
			String empName, String areaId, String sidx, String sord, Pager pager) {
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		sb.append("select t.* from sen_employee t where t.status=? ");
		list.add("1");
		if(StringUtil.notBlank(empCode)){
			sb.append(" and t.empCode like ? ");
			list.add("%"+empCode.trim()+"%");
		}
		if(StringUtil.notBlank(empName)){
			sb.append(" and t.empName like ? ");
			list.add("%"+empName.trim()+"%");
		}
		if(StringUtil.notBlank(departId)){
			sb.append(" and t.departId in ("+departId+") ");
		}
		if(StringUtil.notBlank(areaId)){
			sb.append(" and t.areaId in ("+areaId.trim()+") ");
		}
		if(StringUtil.notBlank(sql)){
			sb.append(" "+sql);
		}
		if(StringUtil.notBlank(sidx)){
			sb.append(" order by "+sidx+" "+sord);
		}
		return this.baseDao.querySQLPageEntity(sb.toString(), pager.getPageSize(), pager.getPageIndex(), "com.sensebling.system.entity.Employee",list);
	}

	@Override
	public List<Employee> findObjChosenEmp(String objectId,QueryParameter qp) {
		StringBuffer sql = new StringBuffer("select c.* from ir_ach_target_base a left join ir_ach_target_base_org b on a.id=b.targetid " +
				"left join sen_employee c on b.individualid=c.id where a.targettype='3'");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notBlank(objectId)){
			sql.append(" and a.id= ?");
			params.add(objectId);
		}
		if(StringUtil.notBlank(qp.getParam("departId"))){
			sql.append(" and c.departId= ?");
			params.add(qp.getParam("departId"));			
		}
		if(StringUtil.notBlank(qp.getParam("empCode"))){
			sql.append(" and c.empCode= ?");
			params.add(qp.getParam("empCode"));			
		}
		return this.baseDao.findBySQLEntity(sql.toString(),params,"com.sensebling.system.entity.Employee");
	}

	@Override
	public Pager getEvalDept(QueryParameter qp,String eId) {
		String[] date = qp.getParam("yearMonth").toString().split("-");
		String sql ="select distinct a.id,a.deptCode,a.deptLevel,a.deptType,a.shortName,a.fullName,case when h.id is null then '0' else '1' end indexStatus from sen_department a " +
				"left join ir_ach_answer_leader h on a.id=h.empid and h.year=? and h.month=? " +
				"where a.id in (select b.individualid from ir_ach_target_base t inner join ir_ach_target_base_org b on t.id=b.targetid where t.id=?)";
		List<Object> params = new ArrayList<Object>();
		params.add(date[0]);
		params.add(date[1]);
		params.add(eId);
		if(StringUtil.notBlank(qp.getParam("shortName"))){
			sql += " and a.shortName like ?";
			params.add("%"+qp.getParam("shortName").toString().trim()+"%");
		}
		if(StringUtil.notBlank(qp.getParam("fullName"))){
			sql += " and a.fullName like ?";
			params.add("%"+qp.getParam("fullName").toString().trim()+"%");
		}
		if(StringUtil.notBlank(qp.getSortField())){
			sql += " order by "+qp.getSortField()+" "+qp.getSortOrder();
		}
		return this.baseDao.querySQLPageEntity(sql, qp.getPager().getPageSize(), qp.getPager().getPageIndex(), "com.sensebling.system.entity.Department",params);
	}

	@Override
	public Pager getEvalEmp(QueryParameter qp,String eId) {
		String[] date = qp.getParam("yearMonth").toString().split("-");
		String sql = "select distinct a.*,case when h.id is null then '0' else '1' end indexStatus from sen_employee a " +
				"left join ir_ach_answer_leader h on a.id = h.empid and h.year=? and h.month=? where a.id in " +
				"(select b.individualid from ir_ach_target_base t inner join ir_ach_target_base_org b on t.id=b.targetid where t.id = ?) ";
		List<Object> params = new ArrayList<Object>();
		params.add(date[0]);
		params.add(date[1]);
		params.add(eId);
		if(StringUtil.notBlank(qp.getParam("empCode"))){
			sql += " and a.empcode like ?";
			params.add("%"+qp.getParam("empCode")+"%");
		}
		if(StringUtil.notBlank(qp.getParam("empName"))){
			sql += " and a.empname like ?";
			params.add("%"+qp.getParam("empName")+"%");
		}
		if(StringUtil.notBlank(qp.getParam("departId"))){
			sql += " and a.departId like ?";
			params.add("%"+qp.getParam("departId")+"%");
		}
		if(StringUtil.notBlank(qp.getSortField())){
			sql += " order by "+qp.getSortField()+" "+qp.getSortOrder();
		}
		return this.baseDao.querySQLPageEntity(sql, qp.getPager().getPageSize(), qp.getPager().getPageIndex(), "com.sensebling.system.entity.Employee",params);
	}

	/**
	 * 考核对象中，选择个体时，加载已选中员工信息
 	 * @param 
	 * @return
	 */
	public List<Employee> findEmpInObj(QueryParameter qp) {
		StringBuffer sql = new StringBuffer("select a.*,b.positionType from sen_employee a inner join ir_emp_position b on a.id = empid where 1=1 and b.status='1'");
		List<Object> params = new ArrayList<Object>();
		if(StringUtil.notBlank(qp.getParam("id").toString())){
			sql.append(" and a.id in ("+StringUtil.change_in(qp.getParam("id").toString())+")");
		}
		return this.baseDao.findBySQLEntity(sql.toString(),params,"com.sensebling.system.entity.Employee");
	}
	

}
