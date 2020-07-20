package com.sensebling.activiti.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.sensebling.system.entity.User;
/**
 * 流程配置环节表
 *
 */
@Entity
@Table(name = "sen_act_config_tache")
@SuppressWarnings("serial")
public class ActConfigTache implements Serializable{

	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	private String name;//环节名称
	@Column(updatable=false)
	private String actid;//流程主表id
	private String remark;//备注
	private Integer ordernum;//环节顺序
	private String tachecode;//环节编码
	private String tache_type;//环节类型，1流程 2审批 3会签
	private Double sign_ratio;//会签通过比例
	private String needsignature;//是否需要签名
	private String del;//删除标识
	private String sign_type;//会签是否需要全员审批 1、是 0、否
	@Column(updatable=false)
	private String is_to_people = "1";//是否到人 1、是 0、否
	private Integer sign_offset;//会签人员偏移量   即额外的通过人数  可为负数
	private String prescription;//时效 单位小时  若为空则无时效   可以是数字或sql
	private String saveurl;//提交前回调
	private String redirect;//提交后回调
	private String sign_return;//是否允许复议 1、是 0、否
	private String people_select_type;//人员选择方式   1 手动  2 自动
	private String is_transfer;//是否允许移交
	private String transfer_saveurl;//移交前回调
	private String transfer_redirect;//移交后回调
	private String transfer_sign;//移交是否签名
	private String transfer_audit;//移交是否审批
	private String transfer_people_type;//移交人员选择方式 1 手动  2 自动
	private String transfer_actcode;//移交审批流程编码
	
	@Transient
	private String actname;//流程名称
	@Transient
	private String branch_condition;//分支条件
	@Transient
	private List<User> users;//环节权限审批人员
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public String getTachecode() {
		return tachecode;
	}
	public void setTachecode(String tachecode) {
		this.tachecode = tachecode;
	}
	public String getTache_type() {
		return tache_type;
	}
	public void setTache_type(String tache_type) {
		this.tache_type = tache_type;
	}
	public Double getSign_ratio() {
		return sign_ratio;
	}
	public void setSign_ratio(Double sign_ratio) {
		this.sign_ratio = sign_ratio;
	}
	public String getNeedsignature() {
		return needsignature;
	}
	public void setNeedsignature(String needsignature) {
		this.needsignature = needsignature;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getIs_to_people() {
		return is_to_people;
	}
	public void setIs_to_people(String is_to_people) {
		this.is_to_people = is_to_people;
	}
	public Integer getSign_offset() {
		if(sign_offset == null) {
			return 0;
		}
		return sign_offset;
	}
	public void setSign_offset(Integer sign_offset) {
		this.sign_offset = sign_offset;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getSaveurl() {
		return saveurl;
	}
	public void setSaveurl(String saveurl) {
		this.saveurl = saveurl;
	}
	public String getRedirect() {
		return redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public String getSign_return() {
		return sign_return;
	}
	public void setSign_return(String sign_return) {
		this.sign_return = sign_return;
	}
	public String getPeople_select_type() {
		return people_select_type;
	}
	public void setPeople_select_type(String people_select_type) {
		this.people_select_type = people_select_type;
	}
	public String getIs_transfer() {
		return is_transfer;
	}
	public void setIs_transfer(String is_transfer) {
		this.is_transfer = is_transfer;
	}
	public String getTransfer_saveurl() {
		return transfer_saveurl;
	}
	public void setTransfer_saveurl(String transfer_saveurl) {
		this.transfer_saveurl = transfer_saveurl;
	}
	public String getTransfer_redirect() {
		return transfer_redirect;
	}
	public void setTransfer_redirect(String transfer_redirect) {
		this.transfer_redirect = transfer_redirect;
	}
	public String getTransfer_sign() {
		return transfer_sign;
	}
	public void setTransfer_sign(String transfer_sign) {
		this.transfer_sign = transfer_sign;
	}
	public String getActname() {
		return actname;
	}
	public void setActname(String actname) {
		this.actname = actname;
	}
	public String getBranch_condition() {
		return branch_condition;
	}
	public void setBranch_condition(String branch_condition) {
		this.branch_condition = branch_condition;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getTransfer_audit() {
		return transfer_audit;
	}
	public void setTransfer_audit(String transfer_audit) {
		this.transfer_audit = transfer_audit;
	}
	public String getTransfer_people_type() {
		return transfer_people_type;
	}
	public void setTransfer_people_type(String transfer_people_type) {
		this.transfer_people_type = transfer_people_type;
	}
	public String getTransfer_actcode() {
		return transfer_actcode;
	}
	public void setTransfer_actcode(String transfer_actcode) {
		this.transfer_actcode = transfer_actcode;
	}
}
