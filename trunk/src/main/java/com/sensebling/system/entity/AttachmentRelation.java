package com.sensebling.system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="sen_attachment_relation")
public class AttachmentRelation implements Serializable
{
	private static final long serialVersionUID = -1912576272126464793L;
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;//主键
	
	
	
	@Column(name="relation_id")
	private String relationId;
	//附件上传的流水号
	@Column(name="attachment_serial")
	private String attachmentSerial;

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.REFRESH)
	@JoinColumn(name="serial",referencedColumnName="attachment_serial")
	private List<AttachmentBase> attachmentBase;
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getAttachmentSerial() {
		return attachmentSerial;
	}
	public void setAttachmentSerial(String attachmentSerial) {
		this.attachmentSerial = attachmentSerial;
	}

	public List<AttachmentBase> getAttachmentBase() {
		return attachmentBase;
	}

	public void setAttachmentBase(List<AttachmentBase> attachmentBase) {
		this.attachmentBase = attachmentBase;
	}
}
