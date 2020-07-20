package com.sensebling.system.vo;
/**
 * 用户权限信息
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sensebling.system.entity.ModuleAuth;

public class UserModuleAuth implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1165268931281071443L;
	private Set<String> authCode = new HashSet<String>();//用户权限code集合
	private Set<String> authFunctionCode = new HashSet<String>();//用户功能权限code集合
	private Map<String, List<ModuleAuth>> moduleAuths = new HashMap<String, List<ModuleAuth>>();//用户菜单按钮权限集合
	public Set<String> getAuthCode() {
		return authCode;
	}
	public void setAuthCode(Set<String> authCode) {
		this.authCode = authCode;
	}
	public Map<String, List<ModuleAuth>> getModuleAuths() {
		return moduleAuths;
	}
	public void setModuleAuths(Map<String, List<ModuleAuth>> moduleAuths) {
		this.moduleAuths = moduleAuths;
	}
	public Set<String> getAuthFunctionCode() {
		return authFunctionCode;
	}
	public void setAuthFunctionCode(Set<String> authFunctionCode) {
		this.authFunctionCode = authFunctionCode;
	}
	/**
	 * 判断用户是否有权限
	 * @param auths 
	 * @return 返回true代表有权限
	 */
	public boolean checkUserAuth(String[] auths) {
		for(String auth:auths) {
			if(authCode.contains(auth)) {
				return true;
			}
		}
		return false;
	}
}
