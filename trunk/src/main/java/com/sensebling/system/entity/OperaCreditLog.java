package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 操作日志
 * 
 * @author YY
 *
 */
@Entity
@Table(name = "OPR_UPDATE_RECODE")
public class OperaCreditLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String ID;//
	private String TABLENAME;//
	private String ENTITYN;//实体类名
	private String OLDDATA;//
	private String NEWDATA;//
	private String OP_HJ;//
	private String REMARK;//
	private String OP_NO;//
	private String OP_NAME;//
	private String OP_TIME;//
	@Transient
	private String username;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTABLENAME() {
		return TABLENAME;
	}
	public void setTABLENAME(String tABLENAME) {
		TABLENAME = tABLENAME;
	}
	public String getOLDDATA() {
		return OLDDATA;
	}
	public void setOLDDATA(String oLDDATA) {
		OLDDATA = oLDDATA;
	}
	public String getNEWDATA() {
		return NEWDATA;
	}
	public void setNEWDATA(String nEWDATA) {
		NEWDATA = nEWDATA;
	}
	public String getOP_HJ() {
		return OP_HJ;
	}
	public void setOP_HJ(String oP_HJ) {
		OP_HJ = oP_HJ;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getOP_NO() {
		return OP_NO;
	}
	public void setOP_NO(String oP_NO) {
		OP_NO = oP_NO;
	}
	public String getOP_NAME() {
		return OP_NAME;
	}
	public void setOP_NAME(String oP_NAME) {
		OP_NAME = oP_NAME;
	}
	public String getOP_TIME() {
		return OP_TIME;
	}
	public void setOP_TIME(String oP_TIME) {
		OP_TIME = oP_TIME;
	}
	public String getENTITYN() {
		return ENTITYN;
	}
	public void setENTITYN(String eNTITYN) {
		ENTITYN = eNTITYN;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

}
