package com.sensebling.activiti.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 流程申请审批结果表
 *
 */
@Entity
@Table(name = "sen_act_apply_option")
@SuppressWarnings("serial")
public class ActApplyOption implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String add_time;//添加时间
	private String add_user;//添加人
	private String record_id;//审核环节记录id
	private String result;//审核结果   1同意 2复议 3否决 4取消
	private String options;//审核意见
	private String sign_path;//签名路径
	private String json;//备用字段，实际业务使用
	@Transient
	private String username;
	@Transient
	private String nickname;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
