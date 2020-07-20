package com.sensebling.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name="sen_user_pwd_secure")
public class UserPwdSecure implements Serializable{
	private static final long serialVersionUID = 7264261212105544240L;
	/**密保记录id*/
	@Id
	@GenericGenerator(name="uuid",strategy="org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator ="uuid")// 主键生成策略
	@Column(name = "id")
	private String id;
	/**密保问题1*/
	@Column(name = "question1")
	private String question1;
	/**密保答案1*/
	@Column(name = "answer1")
	private String answer1;
	/**密保问题2*/
	@Column(name = "question2")
	private String question2;
	/**密保答案2*/
	@Column(name = "answer2")
	private String answer2;
	/**密保问题3*/
	@Column(name = "question3")
	private String question3;
	/**密保答案3*/
	@Column(name = "answer3")
	private String answer3;
	/**关联用户*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestion1() {
		return question1;
	}
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	public String getQuestion2() {
		return question2;
	}
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getQuestion3() {
		return question3;
	}
	public void setQuestion3(String question3) {
		this.question3 = question3;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	
}
