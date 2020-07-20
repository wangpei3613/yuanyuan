package com.sensebling.index.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
/**
 * 模型-版本历史指标表
 *
 */
@Entity
@Table(name = "model_index_history_detail")
@SuppressWarnings("serial")
public class IndexHistoryDetail implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	/** 版本历史id **/
	private String hisid;
	/** 指标编码 **/
	private String code;
	/** 指标名称 **/
	private String name;
	/** 指标级别（1指标类、2指标） **/
	private String level;
	/** 所属父级id **/
	private String pid;
	/** 指标id 与pid处理成树**/
	private String indexid;
	/** 指标类别（1定性、2定量）  **/
	private String category;
	/** 指标取值参数 **/
	private String argument;
	/** 指标内容 **/
	private String content;
	/** 指标满分 **/
	private BigDecimal maxscore;
	/** 指标公式 **/
	private String formula;
	/** 排序号 **/
	private Integer sort;
	/** 备注 **/
	private String remark;
	
	/** 计算指标值 **/
	@Transient
	private String calcValue;
	@Transient
	List<IndexHistoryParameter> parameters;
	@Transient
	List<IndexHistoryConversion> conversions;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHisid() {
		return hisid;
	}
	public void setHisid(String hisid) {
		this.hisid = hisid;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BigDecimal getMaxscore() {
		return maxscore;
	}
	public void setMaxscore(BigDecimal maxscore) {
		this.maxscore = maxscore;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<IndexHistoryParameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<IndexHistoryParameter> parameters) {
		this.parameters = parameters;
	}
	public List<IndexHistoryConversion> getConversions() {
		return conversions;
	}
	public void setConversions(List<IndexHistoryConversion> conversions) {
		this.conversions = conversions;
	}
	public String getIndexid() {
		return indexid;
	}
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	public String getCalcValue() {
		return calcValue;
	}
	public void setCalcValue(String calcValue) {
		this.calcValue = calcValue;
	}
}
