package com.sensebling.activiti.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 流程申请主表
 *
 */
@Entity
@Table(name = "sen_act_apply_info")
@SuppressWarnings("serial")
public class ActApplyInfo implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String appl_date;//申请时间
	private String applman;//申请人
	private String actid;//流程配置主表id
	private String audit_state;//审核状态 1待提交 2审核中 3审核通过 4复议 5否决 6申请取消
	private String caseid;//案件id
	private String json;//备用字段，实际业务使用
	private String end_date;//流程结束时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getAppl_date() {
		return appl_date;
	}
	public void setAppl_date(String appl_date) {
		this.appl_date = appl_date;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getApplman() {
		return applman;
	}
	public void setApplman(String applman) {
		this.applman = applman;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public String getAudit_state() {
		return audit_state;
	}
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
}
