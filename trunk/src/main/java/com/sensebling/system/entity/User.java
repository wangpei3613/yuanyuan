package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sensebling.system.vo.UserModuleAuth;
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="sen_user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**用户编号*/
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	private String id;
	
	/**用户账号*/
	private String userName;

	/**用户昵称*/
	private String nickName;
	
	/**用户 密码*/
	@JsonIgnore
	private String pwd;
	
	/**联系电话*/
	private String linkPhone;
	
	/**账户状态*/
	private String status="1";
	
	
	/**部门视野是否生效*/
	private String deptControl;
	
	/**部门*/
	private String deptId; 
	@Transient
	private String deptName; 
	
	/**串号*/
	private String serialNo;
	/**默认系统*/
	private String defaultsys;
	
	/**创建日期*/
	private String createDate;
	
	/**创建人*/
	private String createUser;
	@Transient
	private String createUserName;
	
	/**创建部门*/
	private String createDept;
	@Transient
	private String userroleid;
	@Transient
	private String isleave;//是否请假  1是
	@Transient
	private String temp;
	/**账号是否被锁定*/
	private String isLocking;
	@Transient
	private Long logintime;//登录时间
	@Transient
	private String logintype;//登录类别  1、pc  2、app 3、qrcode
	@Transient
	private String logid;//登陆日志id
	@Transient
	private UserModuleAuth auth;//用户权限信息
	
	public String getIsLocking() {
		return isLocking;
	}

	public UserModuleAuth getAuth() {
		return auth;
	}

	public void setAuth(UserModuleAuth auth) {
		this.auth = auth;
	}

	public Long getLogintime() {
		return logintime;
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public void setLogintime(Long logintime) {
		this.logintime = logintime;
	}

	public String getLogintype() {
		return logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	public void setIsLocking(String isLocking) {
		this.isLocking = isLocking;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}




	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getDeptControl() {
		return deptControl;
	}

	public void setDeptControl(String deptControl) {
		this.deptControl = deptControl;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(String userroleid) {
		this.userroleid = userroleid;
	}

	public String getIsleave() {
		return isleave;
	}

	public void setIsleave(String isleave) {
		this.isleave = isleave;
	}

	public String getDefaultsys() {
		return defaultsys;
	}

	public void setDefaultsys(String defaultsys) {
		this.defaultsys = defaultsys;
	}

	
}
