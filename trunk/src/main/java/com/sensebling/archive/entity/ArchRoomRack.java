package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_room_rack")
@SuppressWarnings("serial")
public class ArchRoomRack implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**档案架编号**/
	private String rack_no;
	/**档案架层数**/
	private Integer rack_layer;
	/**档案架每层盒数**/
	private Integer rack_layer_column;
	/**档案室ID**/
	private String arch_room_id;
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
	/**get 档案架编号**/
	public String getRack_no() {
		return rack_no;
	}
	/**set 档案架编号**/
	public void setRack_no(String rack_no) {
		this.rack_no = rack_no;
	}
	/**get 档案架层数**/
	public Integer getRack_layer() {
		return rack_layer;
	}
	/**set 档案架层数**/
	public void setRack_layer(Integer rack_layer) {
		this.rack_layer = rack_layer;
	}
	/**get 档案架每层盒数**/
	public Integer getRack_layer_column() {
		return rack_layer_column;
	}
	/**set 档案架每层盒数**/
	public void setRack_layer_column(Integer rack_layer_column) {
		this.rack_layer_column = rack_layer_column;
	}
	/**get 档案室ID**/
	public String getArch_room_id() {
		return arch_room_id;
	}
	/**set 档案室ID**/
	public void setArch_room_id(String arch_room_id) {
		this.arch_room_id = arch_room_id;
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
