package com.sensebling.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * 
 */
public class CookieUtil {
	
	public CookieUtil() {
    }

    /**
     * 添加cookie
     * 
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     * 
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie uid = new Cookie(name, null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }

    /**
     * 获取cookie值
     * 
     * @param request
     * @return
     */
    public static String getCookie(HttpServletRequest request,String cookieName) {
        Cookie cookies[] = request.getCookies();
        if(cookies!=null && cookies.length>0) {
        	for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

	public static void addToken(HttpServletResponse response, String token) {
		response.addHeader("Set-Cookie", "token="+token+"; Path=/; HttpOnly");
	}

	public static void removeToken(HttpServletResponse response) {
		removeCookie(response, "token");  
	}
	
	public static String getToken(HttpServletRequest request) {
		return getCookie(request, "token");  
	}
}
