package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sen_province")
@SuppressWarnings("serial")
public class Province implements Serializable {
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;// 主键
	private String code;//省份编码
	private String name;// 省份、直辖市、自治区、特别行政区
	private String proSort;// 序号
	private String proRemark;// 备注
	
//	@OneToMany(cascade =CascadeType.MERGE ,mappedBy="province", fetch=FetchType.LAZY)
//	@JsonIgnore
//	private Set<City> city;//城市集合

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

	public String getProSort() {
		return proSort;
	}

	public void setProSort(String proSort) {
		this.proSort = proSort;
	}

	public String getProRemark() {
		return proRemark;
	}

	public void setProRemark(String proRemark) {
		this.proRemark = proRemark;
	}

	
}
