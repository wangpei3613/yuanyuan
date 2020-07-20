package com.sensebling.activiti.entity;

import javax.persistence.Transient;

import com.sensebling.common.util.TempBean;

/**
 * 流程bean  用于查询
 *
 */
public class ActBean extends TempBean{
	@Transient
	private String applyid;//申请id
	@Transient
	private String appl_date;//申请时间
	@Transient
	private String applman;//申请人id
	@Transient
	private String actid;//流程id
	@Transient
	private String audit_state;//流程状态
	@Transient
	private String caseid;//关联id
	@Transient
	private String currtachecode;//当前环节编码
	@Transient
	private String currtachenames;//当前环节名称
	@Transient
	private String applmanname;//申请人名称
	@Transient
	private String sent_time;//传送时间
	@Transient
	private String sent_usercode;//传送人
	@Transient
	private String sentusername;//传送人名称
	@Transient
	private String domanid;//处理人
	@Transient
	private String domanname;//处理人名称
	@Transient
	private String domandeptid;//处理人部门id
	@Transient
	private String applytacheid;//申请环节id  审批主键
	@Transient
	private String nextttachenames;//下环节名称
	@Transient
	private String isovertime;//是否超时  1超时
	@Transient
	private String tachename;//环节名称
	@Transient
	private String tacheid;//环节id
	@Transient
	private String tachecode;//环节编码
	@Transient
	private String processcode;//流程编码
	@Transient
	private String end_date;//环节结束时间
	@Transient
	private String auditstatus;//审批状态 1待审2已审
	@Transient
	private String actenddate;//流程结束时间
	
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	public String getActenddate() {
		return actenddate;
	}
	public void setActenddate(String actenddate) {
		this.actenddate = actenddate;
	}
	public String getAppl_date() {
		return appl_date;
	}
	public void setAppl_date(String appl_date) {
		this.appl_date = appl_date;
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
	public String getCurrtachenames() {
		return currtachenames;
	}
	public void setCurrtachenames(String currtachenames) {
		this.currtachenames = currtachenames;
	}
	public String getApplmanname() {
		return applmanname;
	}
	public void setApplmanname(String applmanname) {
		this.applmanname = applmanname;
	}
	public String getSent_time() {
		return sent_time;
	}
	public void setSent_time(String sent_time) {
		this.sent_time = sent_time;
	}
	public String getSent_usercode() {
		return sent_usercode;
	}
	public void setSent_usercode(String sent_usercode) {
		this.sent_usercode = sent_usercode;
	}
	public String getSentusername() {
		return sentusername;
	}
	public void setSentusername(String sentusername) {
		this.sentusername = sentusername;
	}
	public String getDomanid() {
		return domanid;
	}
	public void setDomanid(String domanid) {
		this.domanid = domanid;
	}
	public String getDomanname() {
		return domanname;
	}
	public void setDomanname(String domanname) {
		this.domanname = domanname;
	}
	public String getDomandeptid() {
		return domandeptid;
	}
	public void setDomandeptid(String domandeptid) {
		this.domandeptid = domandeptid;
	}
	public String getApplytacheid() {
		return applytacheid;
	}
	public void setApplytacheid(String applytacheid) {
		this.applytacheid = applytacheid;
	}
	public String getNextttachenames() {
		return nextttachenames;
	}
	public void setNextttachenames(String nextttachenames) {
		this.nextttachenames = nextttachenames;
	}
	public String getIsovertime() {
		return isovertime;
	}
	public void setIsovertime(String isovertime) {
		this.isovertime = isovertime;
	}
	public String getCurrtachecode() {
		return currtachecode;
	}
	public void setCurrtachecode(String currtachecode) {
		this.currtachecode = currtachecode;
	}
	public String getTachename() {
		return tachename;
	}
	public void setTachename(String tachename) {
		this.tachename = tachename;
	}
	public String getTacheid() {
		return tacheid;
	}
	public void setTacheid(String tacheid) {
		this.tacheid = tacheid;
	}
	public String getTachecode() {
		return tachecode;
	}
	public void setTachecode(String tachecode) {
		this.tachecode = tachecode;
	}
	public String getProcesscode() {
		return processcode;
	}
	public void setProcesscode(String processcode) {
		this.processcode = processcode;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(String auditstatus) {
		this.auditstatus = auditstatus;
	}
	
}
