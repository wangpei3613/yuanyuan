package com.sensebling.core.attach.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 影像资料
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="sen_core_attach")
public class CoreAttach implements Serializable{
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")
	private String id;
	private String dicttype;//字典编码
	private String dictcode;//字典类别
	private String applyid;//关联id
	private String servername;//文件服务器名称
	private String clintname;//文件原名称
	private String filesize;//文件大小
	private String filetype;//文件类别
	private String filepath;//文件路径
	private String md5;//文件md5值
	private String coordinates;//经纬度
	private String address;//详细地址
	@Column(updatable=false)
	private String addtime;
	@Column(updatable=false)
	private String adduser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDicttype() {
		return dicttype;
	}
	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}
	public String getDictcode() {
		return dictcode;
	}
	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getClintname() {
		return clintname;
	}
	public void setClintname(String clintname) {
		this.clintname = clintname;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
}
