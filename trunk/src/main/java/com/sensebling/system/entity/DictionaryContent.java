package com.sensebling.system.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 数据字典内容表
 * @author 
 * @time 2014-11-26
 */
@Entity
@Table(name="sen_dictionary_content")
public class DictionaryContent implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**主键*/
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")// 映射字段
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name = "typeId")
	private DictionaryType dtype;// 所属类型
	@Column(updatable=false,insertable=false)
	private String typeId;
	
	private String dictionaryCode;// 编码
	private String dictionaryName;// 名称
	private String remark;// 备注
	private String status;// 状态
	private Integer ordNumber;// 排序
	
	private String dtypeCode;//类型编码

	@JsonIgnore
	@Column(updatable=false)
	private String createUser;// 创建人
	@JsonIgnore
	@Column(updatable=false)
	private String createDate;// 创建时间
	private BigDecimal contents;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DictionaryType getDtype() {
		return dtype;
	}
	public void setDtype(DictionaryType dtype) {
		this.dtype = dtype;
	}
	public String getDictionaryCode() {
		return dictionaryCode;
	}
	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}
	public String getDictionaryName() {
		return dictionaryName;
	}
	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOrdNumber() {
		return ordNumber;
	}
	public void setOrdNumber(Integer ordNumber) {
		this.ordNumber = ordNumber;
	}
	public String getDtypeCode() {
		return dtypeCode;
	}
	public void setDtypeCode(String dtypeCode) {
		this.dtypeCode = dtypeCode;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public BigDecimal getContents() {
		return contents;
	}
	public void setContents(BigDecimal contents) {
		this.contents = contents;
	}
	
	
	
	
}
