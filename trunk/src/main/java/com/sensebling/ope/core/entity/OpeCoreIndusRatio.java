package com.sensebling.ope.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 行业系数表
 */
@Entity
@Table(name="ope_core_IndustryRatio")
public class OpeCoreIndusRatio implements Serializable {
	   private static final long serialVersionUID = 1L;
	   @Id
	   @GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	   @GeneratedValue(generator ="uuid")// 主键生成策略
	   private String id;
	   private String code;
	   private String name;
	   /**
	    * 行业最高负债率
	    */
       private BigDecimal maxDebtRatio;
       private BigDecimal minDebtRatio;
       /**
        * 行业系数
        */
       private BigDecimal industryCoe;
       private String parentCode;
      
       //行业付息债务比
       private BigDecimal induinterateratio;
       //现金盈余倍数
       private BigDecimal cashsurpmultip;
       
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
		public BigDecimal getMaxDebtRatio() {
			return maxDebtRatio;
		}
		public void setMaxDebtRatio(BigDecimal maxDebtRatio) {
			this.maxDebtRatio = maxDebtRatio;
		}
		
		public String getParentCode() {
			return parentCode;
		}
		public void setParentCode(String parentCode) {
			this.parentCode = parentCode;
		}
		public BigDecimal getIndustryCoe() {
			return industryCoe;
		}
		public void setIndustryCoe(BigDecimal industryCoe) {
			this.industryCoe = industryCoe;
		}
		public BigDecimal getMinDebtRatio() {
			return minDebtRatio;
		}
		public void setMinDebtRatio(BigDecimal minDebtRatio) {
			this.minDebtRatio = minDebtRatio;
		}
		public BigDecimal getInduinterateratio() {
			return induinterateratio;
		}
		public void setInduinterateratio(BigDecimal induinterateratio) {
			this.induinterateratio = induinterateratio;
		}
		public BigDecimal getCashsurpmultip() {
			return cashsurpmultip;
		}
		public void setCashsurpmultip(BigDecimal cashsurpmultip) {
			this.cashsurpmultip = cashsurpmultip;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
      
      
}
