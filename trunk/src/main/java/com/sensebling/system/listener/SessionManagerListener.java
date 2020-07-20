package com.sensebling.system.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sensebling.common.util.DebugOut;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.vo.UserOnlineInfo;
import com.sensebling.system.vo.UserOnlineInfo.SessionInfo;


public class SessionManagerListener implements HttpSessionListener {
	private static int sessionNum=0;//当前会话数
	private static DebugOut logger=new DebugOut(SessionManagerListener.class);
	private static Map<String,UserOnlineInfo> onlineSessionMap= new HashMap<String,UserOnlineInfo>();
	public void sessionCreated(HttpSessionEvent event) {
		sessionNum++;
		logger.msgPrint("发现新的访问...当前访问人数:"+sessionNum);
		//设定session过期时间
		int timeout=Integer.parseInt(BasicsFinal.getParamVal("system.session.timeout"));
		event.getSession().setMaxInactiveInterval(timeout);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		sessionNum--;
		logger.msgPrint("一个访问退出...当前访问人数:"+sessionNum);
		HttpSession session=event.getSession();
		
		if(session.getAttribute("user")!=null)
		{
			try
			{
				User u=(User)session.getAttribute("user");
				UserOnlineInfo uoi=onlineSessionMap.get(u.getId());
				if(uoi==null)
					return;
				//移除当前登录用户的当前session,返回移除后此用户剩余的session数
				SessionInfo si=uoi.getSessionInfo(session.getId());
				int cs_num=uoi.removeSession(session.getId());
				if(cs_num==0)//当此用户的登录会话数为0时,移除此用户在线记录
				{
					onlineSessionMap.remove(u.getId());
					logger.msgPrint("用户完全退出--[账号:"+u.getUserName()+"][成功]");
				}
				else
				{
					logger.msgPrint("用户退出--[账号:"+u.getUserName()+"][IP:"+si.getLoginIP()+"][成功]");
					si=null;
				}
			}catch (Exception e) 
			{
				logger.msgPrint("[session销毁异常]"+e.getMessage());
			}
		}
	}

	public static int getSessionNum() {
		return sessionNum;
	}
	/**
	 *  获取在线用户数
	 *  @return 在线用户数
	 * */
	public static int getOnlineUserNum()
	{
		return onlineSessionMap.size();
	}
	/**
	 * 获取在线用户集合
	 * @return 所有在线的用户集合
	 */
	public static List<UserOnlineInfo> getOnlineUsers()
	{
		return new ArrayList<UserOnlineInfo>(onlineSessionMap.values());
	}
	/**
	 * 更具用户id获取给定此用户在线信息
	 * @return 若此用户不在线则返回null
	 */
	public static UserOnlineInfo queryOnlineUser(String userId)
	{
		return onlineSessionMap.get(userId);
	}
	public static void addOnlineUser(UserOnlineInfo vo)
	{
		onlineSessionMap.put(vo.getLoginUser().getId(), vo);
	}
	public static void updateOnlineUser(User u)
	{
		UserOnlineInfo uoi = onlineSessionMap.get(u.getId());
		if(uoi!=null)
		{
			List<UserOnlineInfo.SessionInfo> list=uoi.getSessions();
			for(UserOnlineInfo.SessionInfo si:list)
				si.getLoginSession().setAttribute("user", u);
			logger.msgPrint("用户["+u.getUserName()+"]信息发生变更,session中的user已更改!");
		}
	}
}
