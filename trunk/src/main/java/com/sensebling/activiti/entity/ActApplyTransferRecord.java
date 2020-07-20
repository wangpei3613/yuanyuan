package com.sensebling.activiti.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 流程申请移交记录
 *
 */
@Entity
@Table(name = "sen_act_apply_transfer_record")
@SuppressWarnings("serial")
public class ActApplyTransferRecord implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String add_time;
	private String add_user;
	private String record_id;//环节审批记录表id
	private String options;//移交意见
	private String sign_path;//签名路径
	private String transfer_userid;//移交人员
	private String receive_userid;//接收人员
	
	@Transient
	private String receive_nickname;//接收人员-姓名
	@Transient
	private String receive_username;//接收人员-用户名
	@Transient
	private String transfer_nickname;//移交人员-姓名
	@Transient
	private String transfer_username;//移交人员-用户名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getAdd_user() {
		return add_user;
	}
	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
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
	public String getTransfer_userid() {
		return transfer_userid;
	}
	public void setTransfer_userid(String transfer_userid) {
		this.transfer_userid = transfer_userid;
	}
	public String getReceive_userid() {
		return receive_userid;
	}
	public void setReceive_userid(String receive_userid) {
		this.receive_userid = receive_userid;
	}
	public String getReceive_nickname() {
		return receive_nickname;
	}
	public void setReceive_nickname(String receive_nickname) {
		this.receive_nickname = receive_nickname;
	}
	public String getReceive_username() {
		return receive_username;
	}
	public void setReceive_username(String receive_username) {
		this.receive_username = receive_username;
	}
	public String getTransfer_nickname() {
		return transfer_nickname;
	}
	public void setTransfer_nickname(String transfer_nickname) {
		this.transfer_nickname = transfer_nickname;
	}
	public String getTransfer_username() {
		return transfer_username;
	}
	public void setTransfer_username(String transfer_username) {
		this.transfer_username = transfer_username;
	}
	
}
