package com.sensebling.activiti.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 流程配置环节走向表
 *
 */
@Entity
@Table(name = "sen_act_config_tache_trend")
@SuppressWarnings("serial")
public class ActConfigTacheTrend implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String curr_tache_id;//当前环节id
	@Column(updatable=false)
	private String actid;//流程主表id
	private String next_tache_id;//下一环节id
	private String trend_type;//类别   1前进  2后退
	private String state;//状态 1启用2禁用
	private Integer ordernum;//顺序
	private String branch_condition;//分支条件一般是sql
	private String branch_condition_remark;//分支条件描述
	@Transient
	private String actname;//流程名称
	@Transient
	private String curr_tache_name;
	@Transient
	private String next_tache_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurr_tache_id() {
		return curr_tache_id;
	}
	public void setCurr_tache_id(String curr_tache_id) {
		this.curr_tache_id = curr_tache_id;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public String getNext_tache_id() {
		return next_tache_id;
	}
	public void setNext_tache_id(String next_tache_id) {
		this.next_tache_id = next_tache_id;
	}
	public String getTrend_type() {
		return trend_type;
	}
	public void setTrend_type(String trend_type) {
		this.trend_type = trend_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBranch_condition() {
		return branch_condition;
	}
	public void setBranch_condition(String branch_condition) {
		this.branch_condition = branch_condition;
	}
	public String getBranch_condition_remark() {
		return branch_condition_remark;
	}
	public void setBranch_condition_remark(String branch_condition_remark) {
		this.branch_condition_remark = branch_condition_remark;
	}
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public String getActname() {
		return actname;
	}
	public void setActname(String actname) {
		this.actname = actname;
	}
	public String getCurr_tache_name() {
		return curr_tache_name;
	}
	public void setCurr_tache_name(String curr_tache_name) {
		this.curr_tache_name = curr_tache_name;
	}
	public String getNext_tache_name() {
		return next_tache_name;
	}
	public void setNext_tache_name(String next_tache_name) {
		this.next_tache_name = next_tache_name;
	}
	
}
