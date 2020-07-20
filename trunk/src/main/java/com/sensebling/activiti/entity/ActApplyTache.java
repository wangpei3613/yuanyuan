package com.sensebling.activiti.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
/**
 * 流程申请环节表
 *
 */
@Entity
@Table(name = "sen_act_apply_tache")
@SuppressWarnings("serial")
public class ActApplyTache implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String applyid;//流程申请id
	private String sent_time;//传送时间
	private String sent_usercode;//传送人
	private String pre_tache_id;//上一环节id
	private String next_tache_id;//下一环节ids
	private String curr_tache_id;//当前环节id
	private String record_id;//审批记录id
	private String back_tache_id;//复议环节ids
	private String curr_user_ids;//记录到人环节当前审核人
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
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
	public String getPre_tache_id() {
		return pre_tache_id;
	}
	public void setPre_tache_id(String pre_tache_id) {
		this.pre_tache_id = pre_tache_id;
	}
	public String getNext_tache_id() {
		return next_tache_id;
	}
	public void setNext_tache_id(String next_tache_id) {
		this.next_tache_id = next_tache_id;
	}
	public String getCurr_tache_id() {
		return curr_tache_id;
	}
	public void setCurr_tache_id(String curr_tache_id) {
		this.curr_tache_id = curr_tache_id;
	}
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getBack_tache_id() {
		return back_tache_id;
	}
	public void setBack_tache_id(String back_tache_id) {
		this.back_tache_id = back_tache_id;
	}
	public String getCurr_user_ids() {
		return curr_user_ids;
	}
	public void setCurr_user_ids(String curr_user_ids) {
		this.curr_user_ids = curr_user_ids;
	}
}
