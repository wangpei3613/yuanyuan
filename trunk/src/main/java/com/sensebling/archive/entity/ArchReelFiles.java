package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_reel_files")
@SuppressWarnings("serial")
public class ArchReelFiles implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**文件名称**/
	private String file_name;
	/**父级文件ID**/
	private String pid;
	/**文件类型（单个，集合）**/
	private String file_type;
	/**年度**/
	private String year;
	/**文件顺序**/
	private Integer serial;
	/**案卷ID**/
	private String arch_reel_id;
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
	/**get 文件名称**/
	public String getFile_name() {
		return file_name;
	}
	/**set 文件名称**/
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	/**get 父级文件ID**/
	public String getPid() {
		return pid;
	}
	/**set 父级文件ID**/
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**get 文件类型（单个，集合）**/
	public String getFile_type() {
		return file_type;
	}
	/**set 文件类型（单个，集合）**/
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	/**get 年度**/
	public String getYear() {
		return year;
	}
	/**set 年度**/
	public void setYear(String year) {
		this.year = year;
	}
	/**get 文件顺序**/
	public Integer getSerial() {
		return serial;
	}
	/**set 文件顺序**/
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	/**get 案卷ID**/
	public String getArch_reel_id() {
		return arch_reel_id;
	}
	/**set 案卷ID**/
	public void setArch_reel_id(String arch_reel_id) {
		this.arch_reel_id = arch_reel_id;
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
