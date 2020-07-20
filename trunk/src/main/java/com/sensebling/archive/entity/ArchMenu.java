package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_menu")
@SuppressWarnings("serial")
public class ArchMenu implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**目录父ID**/
	private String pid;
	/**目录编号**/
	private String menu_no;
	/**目录名称**/
	private String menu_name;
	/**排序号**/
	private Integer ordernumber;
	/**备注**/
	private String remark;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	private String updatetime;
	private String updateuser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 目录父ID**/
	public String getPid() {
		return pid;
	}
	/**set 目录父ID**/
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**get 目录编号**/
	public String getMenu_no() {
		return menu_no;
	}
	/**set 目录编号**/
	public void setMenu_no(String menu_no) {
		this.menu_no = menu_no;
	}
	/**get 目录名称**/
	public String getMenu_name() {
		return menu_name;
	}
	/**set 目录名称**/
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	/**get 备注**/
	public String getRemark() {
		return remark;
	}
	/**set 备注**/
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getAdduser() {
		return adduser;
	}
	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Integer getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(Integer ordernumber) {
		this.ordernumber = ordernumber;
	}
}
