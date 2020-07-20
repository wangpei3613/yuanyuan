package com.sensebling.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 系统菜单实体类
 * @author 
 */
@Entity
@Table(name="sen_module")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("serial")
public class Module implements Serializable {
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;//id
	private String moduleName;//菜单名称
	private String moduleno;//菜单编号
	private Integer orderNumber;//排列顺序
	private String remark;//备注
	private String pid;//父级菜单id
	private String controller;// 菜单js控制层
	private String viewport;// 菜单页面
	private String moduletype;//菜单类型
	private String isuse;//是否使用
	private String url;//页面路径
	private String icons;//菜单图标
	private String weburl;//菜单路径
	private String homepage;//系统首页
	private String syscode;//所属系统
	@Column(updatable=false)
	private String createUser;// 创建人
	@Column(updatable=false)
	private String createDate;// 创建时间
	private String updateUser;// 修改人
	private String updateDate;// 修改时间
	@OneToMany(mappedBy="moduleid",fetch=FetchType.EAGER)
	@OrderBy("sort asc")
	private List<ModuleAuth> auths=new ArrayList<ModuleAuth>();
	@Transient
	private String checked;
	@Transient
	private String defaultsys;
	public String getSyscode() {
		return syscode;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDefaultsys() {
		return defaultsys;
	}
	public void setDefaultsys(String defaultsys) {
		this.defaultsys = defaultsys;
	}
	public List<ModuleAuth> getAuths() {
		return auths;
	}
	public void setAuths(List<ModuleAuth> auths) {
		this.auths = auths;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleno() {
		return moduleno;
	}
	public void setModuleno(String moduleno) {
		this.moduleno = moduleno;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getViewport() {
		return viewport;
	}
	public void setViewport(String viewport) {
		this.viewport = viewport;
	}
	public String getModuletype() {
		return moduletype;
	}
	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}
	public String getIsuse() {
		return isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcons() {
		return icons;
	}
	public void setIcons(String icons) {
		this.icons = icons;
	}
	public String getWeburl() {
		return weburl;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
}
