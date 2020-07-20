package com.sensebling.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统提醒
 * @author 
 * @date 2016-10-15
 */

@Entity
@Table(name="sen_remind")
public class Remind {
	
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id", nullable = false)
	private String id;//主键
	
	private String remindType;//提醒类型（流程任务提醒，其他提醒,催办提醒）
	private String status;//处理状态（未处理，已查阅，已处理等）
	private String remindUser;//提醒人
	private String remark;//提醒说明
	
	private String createUser;//创建人
	private String createTime;//创建时间

	private String checkTime;//处理时间
	
	private String checkRemark;//处理意见

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemindUser() {
		return remindUser;
	}

	public void setRemindUser(String remindUser) {
		this.remindUser = remindUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	
	
}
