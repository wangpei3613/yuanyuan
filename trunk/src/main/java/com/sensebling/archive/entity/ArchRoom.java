package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_room")
@SuppressWarnings("serial")
public class ArchRoom implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**档案室编号**/
	private String room_no;
	/**档案室名称**/
	private String room_name;
	/**档案室地址**/
	private String room_address;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	private String updatetime;
	private String updateuser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 档案室编号**/
	public String getRoom_no() {
		return room_no;
	}
	/**set 档案室编号**/
	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	/**get 档案室名称**/
	public String getRoom_name() {
		return room_name;
	}
	/**set 档案室名称**/
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	/**get 档案室地址**/
	public String getRoom_address() {
		return room_address;
	}
	/**set 档案室地址**/
	public void setRoom_address(String room_address) {
		this.room_address = room_address;
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
}
