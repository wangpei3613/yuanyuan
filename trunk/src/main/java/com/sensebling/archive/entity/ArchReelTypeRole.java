package com.sensebling.archive.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arch_reel_type_role")
@SuppressWarnings("serial")
public class ArchReelTypeRole implements Serializable{
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	private String id;
	
	/**档案类别id**/
	private String reeltypeid;
	/**角色id**/
	private String roleid;
	@Column(updatable = false)
	private String addtime;
	@Column(updatable = false)
	private String adduser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**get 档案类别id**/
	public String getReeltypeid() {
		return reeltypeid;
	}
	/**set 档案类别id**/
	public void setReeltypeid(String reeltypeid) {
		this.reeltypeid = reeltypeid;
	}
	/**get 角色id**/
	public String getRoleid() {
		return roleid;
	}
	/**set 角色id**/
	public void setRoleid(String roleid) {
		this.roleid = roleid;
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
