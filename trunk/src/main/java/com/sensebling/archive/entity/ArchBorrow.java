package com.sensebling.archive.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "arch_borrow")
@SuppressWarnings("serial")
public class ArchBorrow implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**案卷ID**/
	private String arch_reel_id;
	/**申请编号**/
	private String apply_no;
	/**借阅人**/
	private String brow_user;
	/**申请时间**/
	private String apply_date;
	/**借阅时间**/
	private String brow_date;
	/**归还时间**/
	private String back_date;
	/**实际归还时间**/
	private String real_back_date;
	/**审批状态**/
	private String audit_status;
	/**借阅状态**/
	private String brow_status;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	private String updatetime;
	private String updateuser;
	@Transient
	private String reel_name;
	@Transient
	private String brow_name;
	@Transient
	private String apply_name;
	@Transient
	private String reel_no;
	@Transient
	private String currtachenames;
	@Transient
	private String nextttachenames;
	@Transient
	private String sendMan;
	@Transient
	private String sent_time;
	@Transient
	private String applytacheid;
	@Transient
	private String applyid;
	@Transient
	private String audit_state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 案卷ID**/
	public String getArch_reel_id() {
		return arch_reel_id;
	}
	/**set 案卷ID**/
	public void setArch_reel_id(String arch_reel_id) {
		this.arch_reel_id = arch_reel_id;
	}
	/**get 申请编号**/
	public String getApply_no() {
		return apply_no;
	}
	/**set 申请编号**/
	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}
	/**get 借阅人**/
	public String getBrow_user() {
		return brow_user;
	}
	/**set 借阅人**/
	public void setBrow_user(String brow_user) {
		this.brow_user = brow_user;
	}
	/**get 申请时间**/
	public String getApply_date() {
		return apply_date;
	}
	/**set 申请时间**/
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	/**get 借阅时间**/
	public String getBrow_date() {
		return brow_date;
	}
	/**set 借阅时间**/
	public void setBrow_date(String brow_date) {
		this.brow_date = brow_date;
	}
	/**get 归还时间**/
	public String getBack_date() {
		return back_date;
	}
	/**set 归还时间**/
	public void setBack_date(String back_date) {
		this.back_date = back_date;
	}
	/**get 实际归还时间**/
	public String getReal_back_date() {
		return real_back_date;
	}
	/**set 实际归还时间**/
	public void setReal_back_date(String real_back_date) {
		this.real_back_date = real_back_date;
	}
	/**get 审批状态**/
	public String getAudit_status() {
		return audit_status;
	}
	/**set 审批状态**/
	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}
	/**get 借阅状态**/
	public String getBrow_status() {
		return brow_status;
	}
	/**set 借阅状态**/
	public void setBrow_status(String brow_status) {
		this.brow_status = brow_status;
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

	public String getReel_name() {
		return reel_name;
	}
	public void setReel_name(String reel_name) {
		this.reel_name = reel_name;
	}
	public String getBrow_name() {
		return brow_name;
	}
	public void setBrow_name(String brow_name) {
		this.brow_name = brow_name;
	}
	public String getApply_name() {
		return apply_name;
	}
	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}

	public String getReel_no() {
		return reel_no;
	}

	public void setReel_no(String reel_no) {
		this.reel_no = reel_no;
	}

	public String getSendMan() {
		return sendMan;
	}

	public void setSendMan(String sendMan) {
		this.sendMan = sendMan;
	}

	public String getCurrtachenames() {
		return currtachenames;
	}

	public void setCurrtachenames(String currtachenames) {
		this.currtachenames = currtachenames;
	}

	public String getNextttachenames() {
		return nextttachenames;
	}

	public void setNextttachenames(String nextttachenames) {
		this.nextttachenames = nextttachenames;
	}

	public String getSent_time() {
		return sent_time;
	}

	public void setSent_time(String sent_time) {
		this.sent_time = sent_time;
	}

	public String getApplytacheid() {
		return applytacheid;
	}

	public void setApplytacheid(String applytacheid) {
		this.applytacheid = applytacheid;
	}

	public String getApplyid() {
		return applyid;
	}

	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}

	public String getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
}
