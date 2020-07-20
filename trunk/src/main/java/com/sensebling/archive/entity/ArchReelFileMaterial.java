package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_reel_file_material")
@SuppressWarnings("serial")
public class ArchReelFileMaterial implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**材料名称**/
	private String meta_name;
	/**文件后缀，如JPG,MP4.DOCX等**/
	private String meta_type;
	/**材料路径**/
	private String meta_url;
	/**材料顺序**/
	private Integer meta_serial;
	/**文件服务器名称**/
	private String servername;
	/**文件原名称**/
	private String clintname;
	/**文件大小**/
	private String filesize;
	/**文件ID**/
	private String arch_reel_files_id;
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
	/**get 材料名称**/
	public String getMeta_name() {
		return meta_name;
	}
	/**set 材料名称**/
	public void setMeta_name(String meta_name) {
		this.meta_name = meta_name;
	}
	/**get 文件后缀，如JPG,MP4.DOCX等**/
	public String getMeta_type() {
		return meta_type;
	}
	/**set 文件后缀，如JPG,MP4.DOCX等**/
	public void setMeta_type(String meta_type) {
		this.meta_type = meta_type;
	}
	/**get 材料路径**/
	public String getMeta_url() {
		return meta_url;
	}
	/**set 材料路径**/
	public void setMeta_url(String meta_url) {
		this.meta_url = meta_url;
	}
	/**get 材料顺序**/
	public Integer getMeta_serial() {
		return meta_serial;
	}
	/**set 材料顺序**/
	public void setMeta_serial(Integer meta_serial) {
		this.meta_serial = meta_serial;
	}
	/**get 文件服务器名称**/
	public String getServername() {
		return servername;
	}
	/**set 文件服务器名称**/
	public void setServername(String servername) {
		this.servername = servername;
	}
	/**get 文件原名称**/
	public String getClintname() {
		return clintname;
	}
	/**set 文件原名称**/
	public void setClintname(String clintname) {
		this.clintname = clintname;
	}
	/**get 文件大小**/
	public String getFilesize() {
		return filesize;
	}
	/**set 文件大小**/
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	/**get 文件ID**/
	public String getArch_reel_files_id() {
		return arch_reel_files_id;
	}
	/**set 文件ID**/
	public void setArch_reel_files_id(String arch_reel_files_id) {
		this.arch_reel_files_id = arch_reel_files_id;
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
