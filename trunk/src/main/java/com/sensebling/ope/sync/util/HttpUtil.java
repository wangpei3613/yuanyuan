package com.sensebling.ope.sync.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sensebling.common.util.DebugOut;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.entity.User;
import com.sensebling.system.service.UserSvc;

/**
 * 同步信贷系统http帮助类
 *
 */
@SuppressWarnings("deprecation")
@Component
public class HttpUtil implements ApplicationContextAware{
	
    /***** 服务器信息start ****/
    private final String http_domain = "kwwi7t.natappfree.cc";
    private final String http_path = "/credit";
    private final String http_host = "http://kwwi7t.natappfree.cc/credit";
    /***** 服务器信息end ****/

    /***** 登录信息start ****/
    public String login_uname = "";// 登录用户名
    private String login_upass = "";// 登录用户密码
    public String login_name = "";// 登录用户姓名
    public String login_deptid = "";// 登录用户机构号
    public String login_deptname = "";// 登录用户机构名称
    /***** 登录信息end ****/

    /**** 权限信息start ******/
    private String jsessionid;
    private boolean loginStatus = false;//是否登录成功
    /**** 权限信息end ******/
    
    /**** 同步用户使用状态start ******/
    public static Map<String, Long> syncUname = new HashMap<String, Long>(); 
    private static Long checkTime = new Date().getTime();//用户使用状态全局判断时间
    private static Long intervalTime = 5l*60*1000;//判断间隔时间
    /**** 同步用户使用状态end ******/
    
    /**
     * 移除同步用户使用状态
     * @param uname
     */
    public void removeSyncUname(String uname) {
    		if(loginStatus) {
    			syncUname.remove(uname);
    		}
    }
    /**
     * 判断同步用户使用状态
     * @param uname
     * @return
     */
    private synchronized static boolean hasSyncUname(String uname) {
    		if(new Date().getTime() - checkTime > intervalTime) {
    			calcSyncUname();
    		}
    		boolean b = syncUname.containsKey(uname);
    		if(!b) {
    			syncUname.put(uname, new Date().getTime());
    		}
    		return b;
    }
    /**
     * 全局判断同步用户使用状态，清除异常数据
     */
    private static void calcSyncUname() {
    		for(String key:syncUname.keySet()) {
    			if(new Date().getTime() - syncUname.get(key) > intervalTime) {  
    				syncUname.remove(key);
    			}
    		}
	}

	protected DebugOut log=new DebugOut(this.getClass());

	public HttpUtil(){
    	
    }
    public String getJsessionid() {
		return jsessionid;
	}
    private static ApplicationContext context;
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext; 
	}
	/**
     * 公共登陆方法
     * @param login_uname 登录用户名
     * @param login_upass 登录用户密码
     */
    public void toLogin(String login_uname, String login_upass) throws Exception{
    		if(StringUtil.notBlank(login_uname, login_upass)) {
    			this.login_uname = login_uname;
        		this.login_upass = login_upass;
        		//判断是否有正在使用的用户，若存在则等待
        		Long time = new Date().getTime();
        		while(hasSyncUname(login_uname)) { 
        			if((new Date().getTime()-time) > (intervalTime+60*1000)) {
        				throw new Exception("当前同步信贷系统繁忙，请稍后再试！"); 
        			}
        			Thread.sleep(1000);  
        		}
        		loginStatus = true;
        		login();
    		}else {
    			throw new Exception("用户"+login_uname+"登录失败，用户名或密码为空"); 
    		}
    }
    
    /**
     * 登陆
     */
	private void login() throws Exception{
		CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
		try {
			Map<String, Object> data = new HashMap<String, Object>();
	        data.put("UserID", login_uname);
	        data.put("Password", login_upass);
	
	        HttpPost httpPost = new HttpPost(http_host + "/Logon.jsp");
	        HttpContext httpContext = new BasicHttpContext();
	        httpClient = HttpClients.createDefault();
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            StringEntity requestEntity = new StringEntity(JsonUtil.formatJsonHttp(data), "utf-8");
            httpPost.setEntity(requestEntity);
            RequestConfig requestConfig = RequestConfig.custom()    
			        .setConnectTimeout(20000).setConnectionRequestTimeout(20000)    
			        .setSocketTimeout(20000).build();    
			httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost, httpContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                if (resultStr.contains("/credit/Redirector?ComponentURL=/Untitled.jsp")) {
                    if (response.getFirstHeader("Set-Cookie") != null) {
                        String setCookie = response.getFirstHeader("Set-Cookie").getValue();
                        jsessionid = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
                    }else{
                    		loginError(login_uname, null);
                    }
                }else{
                		loginError(login_uname, null);
                }
            }else{
            		loginError(login_uname, null); 
            }
        }catch (Exception e) {
        		loginError(login_uname, e);
        }finally {
            if (response != null) {
            		try {
                    response.close();
                }catch (IOException e) {
                		throw e;
                }
            }
        }
    }
	/**
	 * 登陆失败抛出异常
	 * @param uname
	 * @param e
	 * @throws Exception
	 */
	private void loginError(String uname,Exception e) throws Exception {
		UserSvc userSvc = (UserSvc)context.getBean("userSvc");
		User u = userSvc.getUserByName(uname);
		String str = "用户"+login_uname+"登录失败";
		if(u != null) {
			str = "用户"+login_uname+"["+u.getNickName()+"]登录失败";
		}
		log.errPrint(str,null);
		if(e != null) {
			throw e;
		}else {
			throw new Exception(str);  
		}
	}
    /**
     * 获取信贷系统权限标识（即aoid）
     * @param url
     * @return
     */
	public String getAoid(String url) throws Exception{
    		CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
		    HttpGet httpGet = new HttpGet(http_host + url);
		    CookieStore store = new BasicCookieStore();
		    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jsessionid);
		    cookie.setVersion(0);
		    cookie.setDomain(http_domain);
		    cookie.setPath(http_path);
		    store.addCookie(cookie);
		
		    HttpContext httpContext = new BasicHttpContext();
		    httpGet.addHeader("Content-Type", "text/html; charset=utf-8");
		    httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
	        response = httpClient.execute(httpGet, httpContext);
            if(StringUtil.isBlank(login_deptid)){
            		HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultStr = EntityUtils.toString(entity, "utf-8");
                    String str = resultStr.substring(resultStr.indexOf("AsCredit.userName=\"")+19);
                    login_name = str.split("\"")[0];
                    str = resultStr.substring(resultStr.indexOf("AsCredit.orgName=\"")+18);
                    login_deptname = str.split("\"")[0];
                    str = resultStr.substring(resultStr.indexOf("AsCredit.orgId=\"")+16);
                    login_deptid = str.split("\"")[0];
                }
            }
            HttpUriRequest realRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            return realRequest.getURI().toString().substring(realRequest.getURI().toString().indexOf("aoID=") + 5);
        }catch (Exception e) {
            throw e;
        }finally {
            if (response != null) {
            		try {
                    response.close();
                }catch (IOException e) {
                		throw e;
                }
            }
        }
    }
    /**
     * 发送get请求
     * @param url
     * @return
     */
    public String httpGet(String url) throws Exception{
    		CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
	        HttpGet httpGet = new HttpGet(http_host + url);
	        CookieStore store = new BasicCookieStore();
	        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jsessionid);
	        cookie.setVersion(0);
	        cookie.setDomain(http_domain);
	        cookie.setPath(http_path);
	        store.addCookie(cookie);
	
	        HttpContext httpContext = new BasicHttpContext();
	        httpGet.addHeader("Content-Type", "text/html; charset=utf-8");
	        httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
            response = httpClient.execute(httpGet, httpContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return resultStr;
            }
        }catch (Exception e) {
            throw e;
        }finally {
            if (response != null) {
            		try {
                    response.close();
                }catch (IOException e) {
                		throw e;
                }
            }
        }
        return null;
    }
    /**
     * 发送get请求  返回权限标识和结果值
     * @param url
     * @return
     */
	public Map<String, Object> getAoidAndHttpGet(String url) throws Exception{
    		CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        
        try {
	    		HttpGet httpGet = new HttpGet(http_host + url);
	        CookieStore store = new BasicCookieStore();
	        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jsessionid);
	        cookie.setVersion(0);
	        cookie.setDomain(http_domain);
	        cookie.setPath(http_path);
	        store.addCookie(cookie);
	
	        HttpContext httpContext = new BasicHttpContext();
	        httpGet.addHeader("Content-Type", "text/html; charset=utf-8");
	        httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
            response = httpClient.execute(httpGet, httpContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                HttpUriRequest realRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("aoid", realRequest.getURI().toString().substring(realRequest.getURI().toString().indexOf("aoID=") + 5));
                m.put("result", resultStr);
                return m;
            }
        }catch (Exception e) {
            throw e;
        }finally {
            if (response != null) {
            		try {
                    response.close();
                }catch (IOException e) {
                		throw e;
                }
            }
        }
        return null;
    }
    /**
     * 发送post请求
     * @param url
     * @param data
     * @return
     */
    public String httpPost(String url, Object data) throws Exception{
        HttpPost httpPost = new HttpPost(http_host + url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
	        CookieStore store = new BasicCookieStore();
	        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jsessionid);
	        cookie.setVersion(0);
	        cookie.setDomain(http_domain);
	        cookie.setPath(http_path);
	        store.addCookie(cookie);
	        httpClient = HttpClients.custom().setDefaultCookieStore(store).build();
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	        HttpContext httpContext = new BasicHttpContext();

            StringEntity requestEntity = new StringEntity(JsonUtil.formatJsonHttp(data), "utf-8");
            httpPost.setEntity(requestEntity);

            response = httpClient.execute(httpPost, httpContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                return resultStr;
            }
        }catch (Exception e) {
            throw e;
        }finally {
            if (response != null) {
            		try {
                    response.close();
                }catch (IOException e) {
                		throw e;
                }
            }
        }
        return null;
    }
    /**
     * 执行AJAX方法
     * @param aoid
     * @param className
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    public String runMethod(String aoid, String className, String methodName, String args) throws Exception {
		return httpGet("/Common/ToolsB/RunMethodAJAX.jsp?CompClientID="+aoid+"&ClassName="+className+"&MethodName="+methodName+"&Args="+args.replace(" ", "%20"));
    }
    /**
     * 获取客户编号，若不存在则新增  (复制于新增客户功能)
     * @param aoid
     * @param custname 客户名称
     * @param certid 证件号码
     * @param certtype 证件类型
     * @param customertype_ent 企业机构类型
     * @return 返回逗号分隔：客户编号,新证件号
     * @throws Exception 
     */
    public String getCm(String aoid, String custname, String certid, String certtype, String customertype_ent) throws Exception {
    		String customertype = "Ind01".equals(certtype)?"0318":"0110";
    		if(StringUtil.isBlank(customertype_ent)) {
    			customertype_ent = "0310";
    		}
	    	aoid = getAoid("/RedirectorDialog?DiaglogURL=/CustomerManage/AddCustomerDialog.jsp&OpenerClientID="+aoid+"&ComponentURL=/CustomerManage/AddCustomerDialog.jsp&CustomerType="+customertype);
		String customInfo = httpGet("/Common/ToolsB/RunMethodAJAX.jsp?CompClientID="+aoid+"&ClassName=CustomerManage&MethodName=CheckCustomerAction&Args="+customertype+","+custname+","+certtype+","+certid+","+login_uname);
		String[] sReturnStatus = customInfo.split("@");
		String sStatus = (sReturnStatus.length>=1)?sReturnStatus[0]:"";
		String sCustomerID = (sReturnStatus.length>=2)?sReturnStatus[1]:"";
		String sHaveCustomerType = (sReturnStatus.length>=3)?sReturnStatus[2]:"";
		//String sHaveCustomerTypeName = (sReturnStatus.length>=4)?sReturnStatus[3]:"";
		String sHaveStatus = (sReturnStatus.length>=5)?sReturnStatus[4]:"";
		if("80".equals(sStatus)){
			runMethod(aoid, "CustomerManage", "UpdateCustomerCertID", sCustomerID+","+certid);
		}else if("23".equals(sStatus)) {
			throw new Exception("营业执照号码只能为18位，请核对后重新输入18位的证件号然后确定！");
		} else if("100".equals(sStatus)){
			certid = sHaveStatus;
		}else if("01".equals(sStatus) || "04".equals(sStatus)) {
			String sFamilyID = "";
			if("01".equals(sStatus)){
				String cmStr = httpGet("/RedirectorDialog?DiaglogURL=/Common/ToolsB/GetSerialNo.jsp&OpenerClientID=" + aoid+ "&ComponentURL=/Common/ToolsB/GetSerialNo.jsp&TableName=CUSTOMER_INFO&ColumnName=CustomerID&Prefix=CM");
				int cmIndex = cmStr.indexOf("CM" + new SimpleDateFormat("yyyyMMdd").format(new Date()));
				sCustomerID = cmStr.substring(cmIndex, cmIndex + 18);
				if("Ind01".equals(certtype)) {
					sFamilyID = httpGet("/Common/ToolsB/RunMethodAJAX.jsp?CompClientID="+aoid+"&ClassName=SunCredit&MethodName=GetFamilyID&Args="+login_deptid);
				}
			}else if("Ind01".equals(certtype)){
				sFamilyID = httpGet("/Common/ToolsB/RunMethodAJAX.jsp?CompClientID="+aoid+"&ClassName=%25E5%2585%25AC%25E7%2594%25A8%25E6%2596%25B9%25E6%25B3%2595&MethodName=GetColValue&Args=Sun_Ind_Info,FarmilyID,CertType='Ind01'%2520and%2520CertID='"+certid+"'");
		    		if(StringUtil.isBlank(sFamilyID)){ 
		    			sFamilyID = httpGet("/Common/ToolsB/RunMethodAJAX.jsp?CompClientID="+aoid+"&ClassName=SunCredit&MethodName=GetFamilyID&Args="+login_deptid);
		    		}
			}
			httpGet("/Common/ToolsB/RunMethodAJAX.jsp?CompClientID="+aoid+"&ClassName=CustomerManage&MethodName=AddCustomerAction&Args="+sCustomerID+","+custname+","+customertype+","+certtype+","+certid+","+sStatus+","+customertype_ent+","+login_uname + ","+login_deptid+ ","+sHaveCustomerType+","+sFamilyID);
		}
    		return sCustomerID+","+certid;
    }
    /**
     * 获取普通请求的返回html中的returnValue
     * <script language=javascript>
	 * 	self.returnValue = "020";
	 * 	self.close();
	 * </script>
     * @param string
     * @return
     * @throws Exception 
     */
	public String getReturnValue(String url) throws Exception {
		String str = httpGet(url);
		if(StringUtil.notBlank(str)) {
			for(String s:str.split("\n")) {
				s = s.trim();
				if(s.startsWith("self.returnValue")) {
					return s.substring(s.indexOf("\"")+1, s.length()-2);
				}
			}
		}
		return null;
	}
	/**
	 * 获取普通请求的返回html中的returnValue
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public String getReturnValueByHtml(String html) throws Exception {
		String str = html;
		if(StringUtil.notBlank(str)) {
			for(String s:str.split("\n")) {
				s = s.trim();
				if(s.startsWith("self.returnValue")) {
					return s.substring(s.indexOf("\"")+1, s.length()-2);
				}
			}
		}
		return null;
	}
	public static void main(String[] args){
		
	}
}
