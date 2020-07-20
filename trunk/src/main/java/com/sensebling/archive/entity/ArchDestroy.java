package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_destroy")
@SuppressWarnings("serial")
public class ArchDestroy implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**销毁单编号**/
	private String desty_no;
	/**注销人**/
	private String desty_user;
	/**注销时间**/
	private String desty_date;
	/**注销事由**/
	private String desty_reason;
	/**案件ID**/
	private String arch_reel_id;
	/**案件编号**/
	private String arch_reel_no;
	/**目录分类ID**/
	private String arch_menu_id;
	/**案件名称**/
	private String reel_name;
	/**归档时间**/
	private String file_date;
	/**到期时间**/
	private String expire_date;
	/**档案类型**/
	private String reel_type;
	/**保存期限**/
	private String save_date;
	/**rfid码**/
	private String rfid;
	/**原属档案盒id**/
	private String arch_room_rack_box_id;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	/**档案状态**/
	@Transient
	private String reel_status;
	/**目录名称**/
	@Transient
	private String mulu_name;
	/**档案盒名称**/
	@Transient
	private String box_name;
	/**注销人**/
	@Transient
	private String zx_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 销毁单编号**/
	public String getDesty_no() {
		return desty_no;
	}
	/**set 销毁单编号**/
	public void setDesty_no(String desty_no) {
		this.desty_no = desty_no;
	}
	/**get 注销人**/
	public String getDesty_user() {
		return desty_user;
	}
	/**set 注销人**/
	public void setDesty_user(String desty_user) {
		this.desty_user = desty_user;
	}
	/**get 注销时间**/
	public String getDesty_date() {
		return desty_date;
	}
	/**set 注销时间**/
	public void setDesty_date(String desty_date) {
		this.desty_date = desty_date;
	}
	/**get 注销事由**/
	public String getDesty_reason() {
		return desty_reason;
	}
	/**set 注销事由**/
	public void setDesty_reason(String desty_reason) {
		this.desty_reason = desty_reason;
	}
	/**get 案件ID**/
	public String getArch_reel_id() {
		return arch_reel_id;
	}
	/**set 案件ID**/
	public void setArch_reel_id(String arch_reel_id) {
		this.arch_reel_id = arch_reel_id;
	}
	/**get 案件编号**/
	public String getArch_reel_no() {
		return arch_reel_no;
	}
	/**set 案件编号**/
	public void setArch_reel_no(String arch_reel_no) {
		this.arch_reel_no = arch_reel_no;
	}
	/**get 目录分类ID**/
	public String getArch_menu_id() {
		return arch_menu_id;
	}
	/**set 目录分类ID**/
	public void setArch_menu_id(String arch_menu_id) {
		this.arch_menu_id = arch_menu_id;
	}
	/**get 案件名称**/
	public String getReel_name() {
		return reel_name;
	}
	/**set 案件名称**/
	public void setReel_name(String reel_name) {
		this.reel_name = reel_name;
	}
	/**get 归档时间**/
	public String getFile_date() {
		return file_date;
	}
	/**set 归档时间**/
	public void setFile_date(String file_date) {
		this.file_date = file_date;
	}
	/**get 到期时间**/
	public String getExpire_date() {
		return expire_date;
	}
	/**set 到期时间**/
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	/**get 档案类型**/
	public String getReel_type() {
		return reel_type;
	}
	/**set 档案类型**/
	public void setReel_type(String reel_type) {
		this.reel_type = reel_type;
	}
	/**get 保存期限**/
	public String getSave_date() {
		return save_date;
	}
	/**set 保存期限**/
	public void setSave_date(String save_date) {
		this.save_date = save_date;
	}
	/**get rfid码**/
	public String getRfid() {
		return rfid;
	}
	/**set rfid码**/
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	/**get 原属档案盒id**/
	public String getArch_room_rack_box_id() {
		return arch_room_rack_box_id;
	}
	/**set 原属档案盒id**/
	public void setArch_room_rack_box_id(String arch_room_rack_box_id) {
		this.arch_room_rack_box_id = arch_room_rack_box_id;
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

	public String getReel_status() {
		return reel_status;
	}

	public void setReel_status(String reel_status) {
		this.reel_status = reel_status;
	}

	public String getMulu_name() {
		return mulu_name;
	}

	public void setMulu_name(String mulu_name) {
		this.mulu_name = mulu_name;
	}

	public String getBox_name() {
		return box_name;
	}

	public void setBox_name(String box_name) {
		this.box_name = box_name;
	}

	public String getZx_name() {
		return zx_name;
	}

	public void setZx_name(String zx_name) {
		this.zx_name = zx_name;
	}
}
