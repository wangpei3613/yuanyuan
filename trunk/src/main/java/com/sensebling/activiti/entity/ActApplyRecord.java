package com.sensebling.activiti.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 流程申请记录表
 *
 */
@Entity
@Table(name = "sen_act_apply_record")
@SuppressWarnings("serial")
public class ActApplyRecord implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String tacheid;//记录环节id
	private String start_time;//开始时间
	private String end_date;//结束时间
	private String auditing_result;//审批结果  1待审批 2审核通过 3审核复议 4审核否决 5被取消
	private String applyid;//流程申请id
	private Double prescription;//时效 单位小时  为空则无时效
	private String over_time;//超时时间
	private String json;//备用字段，实际业务使用
	@Transient
	private String tachename;//环节名称
	@Transient
	private String tachecode;//环节编码
	@Transient
	private String auditmanid;//审批人id
	@Transient
	private String auditman;//审批人
	@Transient
	private String result;//审批结果  1同意 2复议 3否决 4取消
	@Transient
	private String options;//审批意见
	@Transient
	private String sign_path;//签名路径
	@Transient
	private String audittime;//审批时间
	@Transient
	private List<ActApplyOption> opts;
	@Transient
	private List<ActApplyTransferRecord> trs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getAuditing_result() {
		return auditing_result;
	}
	public void setAuditing_result(String auditing_result) {
		this.auditing_result = auditing_result;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	public String getOver_time() {
		return over_time;
	}
	public void setOver_time(String over_time) {
		this.over_time = over_time;
	}
	public Double getPrescription() {
		return prescription;
	}
	public void setPrescription(Double prescription) {
		this.prescription = prescription;
	}
	public String getTachename() {
		return tachename;
	}
	public void setTachename(String tachename) {
		this.tachename = tachename;
	}
	public String getAuditmanid() {
		return auditmanid;
	}
	public void setAuditmanid(String auditmanid) {
		this.auditmanid = auditmanid;
	}
	public String getAuditman() {
		return auditman;
	}
	public void setAuditman(String auditman) {
		this.auditman = auditman;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getSign_path() {
		return sign_path;
	}
	public void setSign_path(String sign_path) {
		this.sign_path = sign_path;
	}
	public String getAudittime() {
		return audittime;
	}
	public void setAudittime(String audittime) {
		this.audittime = audittime;
	}
	public List<ActApplyOption> getOpts() {
		if(opts == null) {
			return new ArrayList<ActApplyOption>();  
		}
		return opts;
	}
	public void setOpts(List<ActApplyOption> opts) {
		this.opts = opts;
	}
	public List<ActApplyTransferRecord> getTrs() {
		return trs;
	}
	public void setTrs(List<ActApplyTransferRecord> trs) {
		this.trs = trs;
	}
	
}
