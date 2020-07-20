package com.sensebling.system.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sensebling.system.entity.User;

public class UserOnlineInfo {
	
	//会话用户
	private User loginUser;
	//此用户登录的所有会话
	private Map<String, SessionInfo> sessions=new HashMap<String, UserOnlineInfo.SessionInfo>();
	
	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public List<SessionInfo> getSessions() {
		return new ArrayList<UserOnlineInfo.SessionInfo>(sessions.values());
	}
	public SessionInfo getSessionInfo(String sessionId) {
		return sessions.get(sessionId);
	}
	public void addSession(HttpSession session,String sessionIP) {
		SessionInfo sessionInfo=new SessionInfo();
		sessionInfo.loginIP=sessionIP;
		sessionInfo.loginSession=session;
		sessionInfo.loginTime=new Date();
		sessions.put(session.getId(), sessionInfo);
	}
	public int removeSession(String sessionId) {
		sessions.remove(sessionId);
		return sessions.size();
	}
	/**
	 * 销毁用户在线session(所有)
	 */
	public void destroySessions()
	{
		synchronized (this) 
		{
			ArrayList<SessionInfo> list=new ArrayList<UserOnlineInfo.SessionInfo>(sessions.values());
			for(SessionInfo si:list)
			{
				si.getLoginSession().invalidate();
			}
		}
	}
	public class SessionInfo
	{
		//登录时间
		private Date loginTime;
		//登录ip地址
		private String loginIP;
		//会话session
		private HttpSession loginSession;
		public Date getLoginTime() {
			return loginTime;
		}
		public void setLoginTime(Date loginTime) {
			this.loginTime = loginTime;
		}
		public String getLoginIP() {
			return loginIP;
		}
		public void setLoginIP(String loginIP) {
			this.loginIP = loginIP;
		}
		public HttpSession getLoginSession() {
			return loginSession;
		}
		public void setLoginSession(HttpSession loginSession) {
			this.loginSession = loginSession;
		}
	}
}
