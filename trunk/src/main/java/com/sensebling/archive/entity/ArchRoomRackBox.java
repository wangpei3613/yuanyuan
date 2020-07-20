package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_room_rack_box")
@SuppressWarnings("serial")
public class ArchRoomRackBox implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**档案盒编号**/
	private String box_no;
	/**档案盒卷数**/
	private Integer box_num;
	/**档案架ID**/
	private String arch_room_rack_id;
	/**所在层**/
	private Integer layer;
	/**所在盒数（列）**/
	private Integer column;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	private String updatetime;
	private String updateuser;

	@Transient
	private Integer reelnum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 档案盒编号**/
	public String getBox_no() {
		return box_no;
	}
	/**set 档案盒编号**/
	public void setBox_no(String box_no) {
		this.box_no = box_no;
	}
	/**get 档案盒卷数**/
	public Integer getBox_num() {
		return box_num;
	}
	/**set 档案盒卷数**/
	public void setBox_num(Integer box_num) {
		this.box_num = box_num;
	}
	/**get 档案架ID**/
	public String getArch_room_rack_id() {
		return arch_room_rack_id;
	}
	/**set 档案架ID**/
	public void setArch_room_rack_id(String arch_room_rack_id) {
		this.arch_room_rack_id = arch_room_rack_id;
	}
	/**get 所在层**/
	public Integer getLayer() {
		return layer;
	}
	/**set 所在层**/
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	/**get 所在盒数（列）**/
	public Integer getColumn() {
		return column;
	}
	/**set 所在盒数（列）**/
	public void setColumn(Integer column) {
		this.column = column;
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

	public Integer getReelnum() {
		return reelnum;
	}

	public void setReelnum(Integer reelnum) {
		this.reelnum = reelnum;
	}
}
