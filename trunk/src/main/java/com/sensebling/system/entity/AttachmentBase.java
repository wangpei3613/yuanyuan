package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sen_attachment_base")
public class AttachmentBase implements Serializable 
{
	private static final long serialVersionUID = 7922608204649690843L;
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;//主键
	@Column(name="serial")
	private String serial;//流水号
	@Column(name="batch")
	private String batch;//批次号
	//文件所在服务器端保存名称
	@Column(name="fileName_server")
	private String fileNameServer;
	//文件上传时客户端名称
	@Column(name="fileName_client")
	private String fileNameClient;
	//文件大小mb
	@Column(name="fileSize")
	private double fileSize;
	//文件类型(.xls .txt ...)
	@Column(name="fileType")
	private String fileType;
	//服务器端保存完整路径
	@Column(name="filePath")
	private String filePath;
	//文件MD5值
	@Column(name="fileMD5")
	private String fileMD5;
	@Column(name="createDate",updatable=false)
	private String createDate;
	@Column(name="createUser",updatable=false)
	private String createUser;
	@Column(name="updateDate")
	private String updateDate;
	@Column(name="updateUser")
	private String updateUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getFileNameServer() {
		return fileNameServer;
	}
	public void setFileNameServer(String fileNameServer) {
		this.fileNameServer = fileNameServer;
	}
	public String getFileNameClient() {
		return fileNameClient;
	}
	public void setFileNameClient(String fileNameClient) {
		this.fileNameClient = fileNameClient;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileMD5() {
		return fileMD5;
	}
	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
