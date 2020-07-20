package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_reel")
@SuppressWarnings("serial")
public class ArchReel implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**案件编号**/
	private String reel_no;
	/**目录分类ID**/
	private String arch_menu_id;
	/**rfid码**/
	private String rfid;
	/**归属档案盒id**/
	private String arch_room_rack_box_id;
	/**案件名称**/
	private String reel_name;
	/**档案类型**/
	private String reel_type;
	/**归档时间**/
	private String file_date;
	/**到期时间**/
	private String expire_date;
	/**保存期限**/
	private String save_date;
	/**档案状态**/
	private String reel_status;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	private String updatetime;
	private String updateuser;
	@Transient
	private String box_no;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 案件编号**/
	public String getReel_no() {
		return reel_no;
	}
	/**set 案件编号**/
	public void setReel_no(String reel_no) {
		this.reel_no = reel_no;
	}
	/**get 目录分类ID**/
	public String getArch_menu_id() {
		return arch_menu_id;
	}
	/**set 目录分类ID**/
	public void setArch_menu_id(String arch_menu_id) {
		this.arch_menu_id = arch_menu_id;
	}
	/**get rfid码**/
	public String getRfid() {
		return rfid;
	}
	/**set rfid码**/
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	/**get 归属档案盒id**/
	public String getArch_room_rack_box_id() {
		return arch_room_rack_box_id;
	}
	/**set 归属档案盒id**/
	public void setArch_room_rack_box_id(String arch_room_rack_box_id) {
		this.arch_room_rack_box_id = arch_room_rack_box_id;
	}
	/**get 案件名称**/
	public String getReel_name() {
		return reel_name;
	}
	/**set 案件名称**/
	public void setReel_name(String reel_name) {
		this.reel_name = reel_name;
	}
	/**get 档案类型**/
	public String getReel_type() {
		return reel_type;
	}
	/**set 档案类型**/
	public void setReel_type(String reel_type) {
		this.reel_type = reel_type;
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
	/**get 保存期限**/
	public String getSave_date() {
		return save_date;
	}
	/**set 保存期限**/
	public void setSave_date(String save_date) {
		this.save_date = save_date;
	}
	/**get 档案状态**/
	public String getReel_status() {
		return reel_status;
	}
	/**set 档案状态**/
	public void setReel_status(String reel_status) {
		this.reel_status = reel_status;
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

	public String getBox_no() {
		return box_no;
	}

	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
}
