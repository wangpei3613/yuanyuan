package com.sensebling.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 数据字典类型表
 * @author 
 * @time 2014-11-26
 */
@Entity
@Table(name="sen_dictionary_type",uniqueConstraints={@UniqueConstraint(columnNames={"code"})})
public class DictionaryType implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**主键*/
	@Id
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@Column(name = "id")// 映射字段
	private String id;
	private String code;//类型编码
	private String name;//类型名称
	private String remark;//类型备注
    @Column(updatable=false)
	private String createUser;//创建人
    @Column(updatable=false)
	private String createDate;//创建时间
	
	@OneToMany(mappedBy="dtype",fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	@JsonIgnore
	@OrderBy("ordNumber asc")
	private List<DictionaryContent> contents=new ArrayList<DictionaryContent>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<DictionaryContent> getContents() {
		return contents;
	}

	public void setContents(List<DictionaryContent> contents) {
		this.contents = contents;
	}
	
	
}
