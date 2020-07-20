package com.sensebling.system.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.BasicsCtrl;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.util.AesUtil;
import com.sensebling.common.util.CommonUtil;
import com.sensebling.common.util.CookieUtil;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.SenAesUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.annotation.AuthIgnore;
import com.sensebling.system.annotation.DisposableToken;
import com.sensebling.system.bean.LoginParam;
import com.sensebling.system.bean.QrcodeInfo;
import com.sensebling.system.context.RedisManager;
import com.sensebling.system.context.TokenManager;
import com.sensebling.system.entity.LoginLog;
import com.sensebling.system.entity.LoginTwo;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.LoginLogSvc;
import com.sensebling.system.service.LoginSvc;
import com.sensebling.system.service.ModuleAuthSvc;
import com.sensebling.system.service.UserSvc;

@Controller
@RequestMapping("/login")
public class LoginCtrl extends BasicsCtrl {
	
	@Resource
	private UserSvc userSvc;
	@Resource
	private LoginLogSvc loginLogSvc;
	@Resource
	private ModuleAuthSvc moduleAuthSvc;
	@Resource
	private LoginSvc loginSvc; 
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private RedisManager redisManager;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	@ResponseBody
	public Result login(String username,String password,String uuid,HttpServletRequest request) throws Exception{
		Result r = new Result();
		String data = getRequestParam("_data_");//APP登录加密后数据
		String imei = getRequestParam("imei");
		String model = getRequestParam("model");
		Map<String,Object> maps = new HashMap<String,Object>();
		 maps.put("data", data);
		 maps.put("imei", imei);
		 maps.put("model", model);
		 maps.put("username", username);
		 maps.put("password", password);
		 maps.put("isApp", isApp);
		 uuid = StringUtil.sNull(uuid);
		 if("-1".equals(uuid)){//创建二维码
			 String path = loginSvc.createQRCode(getRequest());
			 r.setData(path);
			 r.success();
			 return crudError(r);
		 }else if(uuid.length()>0){
			 if(uuid.contains("@")){
				 maps.put("uuid", uuid);
				 r = loginSvc.updateQrCode(maps);
				 if(r.isSuccess()){
					 r.setData("ok");  
					 r.success();
				 }
				 return crudError(r);
			 }else{
				 maps.put("loginFlag", "E");
				 String loginId = loginSvc.selectQrCode(getRequest(),uuid);
				 maps.put("username", loginId);
					if(StringUtil.sNull(loginId).length() != 0) {
						 r = loginSvc.doLogin(maps);
					}else{
						 r.setData(uuid);  
						 r.success();
						 return crudError(r);
					}
			 }
		 }else{
			  maps.put("loginFlag", "C");
			  r = loginSvc.doLogin(maps);
		 }
		 if(null!=r.getData()){
			 Map<String, Object> mapp = (Map<String, Object>) r.getData(); 
			 User u = (User)mapp.get("user");
//			 getSession().setAttribute("user",u );//将用户信息放入session
//			 getSession().setAttribute("moduleAuth", moduleAuthSvc.getUserModuleAuth(u.getId()));//将用户权限信息放入session
			 log.msgPrint("用户登录--[账号:"+u.getUserName()+"][成功]");
//			 getSession().setAttribute("loginlog", mapp.get("loginlog"));//将登录日志信息放入session
			 mapp.remove("user");
			 mapp.remove("loginlog");
			 
			 //token处理
			 CookieUtil.removeToken(getResponse());
			 String token = UUID.randomUUID().toString();
			 CookieUtil.addToken(getResponse(), token);
			 
			 //返回token
			 String tokens = StringUtil.sNull(AesUtil.sign(u.getNickName(), u.getId()));
			 if(!"".equals(tokens)){
				
				 Map<String,Object> map = ProtocolConstant.tokenMap;
				 CookieUtil.addToken(getResponse(), tokens);
				 if(null==map)map= new HashMap<String,Object>();
				 mapp.put("accessToken", tokens);
				 mapp.put("path", request.getContextPath());
				 r.setData(mapp);  
				 mapp.put(tokens, tokens);
				 mapp.put("u", u);
				 mapp.put("moduleAuth", moduleAuthSvc.getUserModuleAuth(u.getId()));
				 map.put(tokens, mapp);
				 StringUtil.setTokenMap(map);
				 r.success();
			 }else{
				 r.setError("请求不合法");
				 return crudError(r);
			 }
		 }
		return crudError(r);
	}
	
	/**
	 * 二维码登录
	 * @param username
	 * @param password
	 * @param uuid
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	@ResponseBody
	public Result doLogin(String username,String uuids) throws Exception{
		Result r = new Result();
		String data = getRequestParam("_data_");//APP登录加密后数据
		String imei = getRequestParam("imei");
		String model = getRequestParam("model");
		Map<String,Object> maps = new HashMap<String,Object>();
		 maps.put("data", data);
		 maps.put("imei", imei);
		 maps.put("model", model);
		 maps.put("username", username);
		 boolean flag ="1".equals(getRequest().getParameter("_app"));
		 maps.put("isApp", flag);
		 uuids = StringUtil.sNull(uuids);
		 ServletContext application = getSession().getServletContext();
		 if("-1".equals(uuids)){//创建二维码
			 String uuidss =UUID.randomUUID().toString();
			 r.setData(uuidss);
			 r.success();
			 LoginTwo loginTwo = new LoginTwo();
			 loginTwo.setBegin(new Date());
			 loginTwo.setEnd(StringUtil.df_sdf3.parse(StringUtil.getTimeByMinute(5)));
			 application.setAttribute(uuidss,loginTwo );
			 return crudError(r);
		 }else if(uuids.length()>0){
			 if(!flag){
				 maps.put("uuid", uuids);
				 Object obj = application.getAttribute(uuids);
				 if(null!=obj){
					 LoginTwo loginTwo = (LoginTwo)obj;
					 Date currentDate = new Date();
					 if(currentDate.after(loginTwo.getBegin())&&currentDate.before(loginTwo.getEnd())){
						 String loginId = StringUtil.sNull(loginTwo.getLoginId());
						 if(!"".equals(loginId)){
							 maps.put("loginFlag", "E");
							 maps.put("username", loginId);
							 Result rr = loginSvc.doLogin(maps);
							 if(rr.isSuccess()){
								 r.setData("ok");  
								 r.success();
								 application.removeAttribute(uuids);
								 Map<String, Object> mapp = (Map<String, Object>) rr.getData(); 
								 User u = (User)mapp.get("user");
								 getSession().setAttribute("user",u );//将用户信息放入session
								 getSession().setAttribute("moduleAuth", moduleAuthSvc.getUserModuleAuth(u.getId()));//将用户权限信息放入session
								 log.msgPrint("用户登录--[账号:"+u.getUserName()+"][成功]");
								 getSession().setAttribute("loginlog", mapp.get("loginlog"));//将登录日志信息放入session
								 mapp.remove("user");
								 mapp.remove("loginlog");
								 
								//token处理
								 CookieUtil.removeToken(getResponse());
								 String token = UUID.randomUUID().toString();
								 CookieUtil.addToken(getResponse(), token);
								 
							 }else{
								 r.setError(rr.getError());
							 }
						 }else{//手机端没有扫
							 r.setData(uuids);  
							 r.success();
						 }
						
					 }else{
						 r.setError("二维码已过期，请刷新页面");
						 application.removeAttribute(uuids);
					 }
				 }else{
					 r.setError("二维码已过期，请刷新页面");
					 application.removeAttribute(uuids);
				 }
			 }
			 if(!r.isSuccess()){
				 return crudError(r);
			 }
		 }else{
			 r.setError("请求不合法");
		 }
		return crudError(r);
	}
	
	/**
	 * 手机端登录成功后使用扫一扫功能让pc端登录 
	 * @param username
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/san",method=RequestMethod.POST)
	@ResponseBody
	public Result san(String username,String uuids){
		Result r = new Result();
		String data = getRequestParam("_data_");//APP登录加密后数据
		String imei = getRequestParam("imei");
		String model = getRequestParam("model");
		username = StringUtil.sNull(username);
		if("".equals(username)){
			r.setError("登录账户不能为空");
			return crudError(r);
		}
		Map<String,Object> maps = new HashMap<String,Object>();
		 maps.put("data", data);
		 maps.put("imei", imei);
		 maps.put("model", model);
		 maps.put("username", username);
		 uuids = StringUtil.sNull(uuids);
		 
		 boolean flag ="1".equals(getRequest().getParameter("_app"));
		 maps.put("isApp", flag);
		 if("-1".equals(uuids)){//创建二维码
			 r.setError("请刷新登录页面");
			 return crudError(r);
		 }else if(uuids.length()>0){
			  if(flag){//手机端
				 ServletContext application = getSession().getServletContext();
				 maps.put("loginFlag", "E");
				 Object obj = application.getAttribute(uuids);
				 if(null!=obj){
					 LoginTwo loginTwo = (LoginTwo)obj;
					 Date currentDate = new Date();
					 if(currentDate.after(loginTwo.getBegin())&&currentDate.before(loginTwo.getEnd())){
						 if(!"".equals(StringUtil.sNull(loginTwo.getLoginId()))){
							 r.setError("请稍安勿躁，pc正在登录中。。。");
						 }else{
							 loginTwo.setLoginId(username);
							 application.setAttribute(uuids, loginTwo);
							 r.setData("ok");  
							 r.success();
						 }
					 }else{
						 r.setError("二维码已过期，请刷新电脑端页面后重新操作。");
					 }
				 }else{
					 r.setError("二维码已过期，请刷新电脑端页面后重新操作。");
				 }
				 
			 }
			 return crudError(r);
		 }else{
			 r.setError("请求不合法");
		 }
		return crudError(r);
	}
	
	
	
	/**
	 * 跳转用户登录页面
	 * @param lxb
	 * @return
	 */
	@RequestMapping(value="/request")
	@AuthIgnore
	public String toLogin(String lastPath,Model model){
		if(lastPath!=null&&!lastPath.trim().equals("")){
			model.addAttribute("lastPath", lastPath);
		}
		return "master/login";
	}
	
	
	@RequestMapping(value="/logout")
	@ResponseBody
	@AuthIgnore
	public Result logout(String type,HttpServletRequest request){
		Result r = new Result();
		User u = getUser();
		if(u != null){
			log.msgPrint("用户退出--[账号:"+getUser().getUserName()+"][成功]");
			if(StringUtil.notBlank(u.getLogid())) {
				LoginLog log = loginLogSvc.get(u.getLogid());
				if(log != null) {
					log.setLoginouttime(DateUtil.getStringDate());
					loginLogSvc.update(log);  
				}
			}
		}
		tokenManager.loginOff(CookieUtil.getToken(request)); 
		r.success();
		return crudError(r);   
	}
	/**
	 * 产生随机验证码
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping(value="/randomCode")
	public void getRandomImg(OutputStream out) throws IOException
	{
		HttpSession session=getSession();
		String randomCode=CommonUtil.randomImageCode(4, out);
		session.setAttribute("@userLoginVerifyCode", randomCode); 
	}
	@RequestMapping(value="/errorPage")
	@AuthIgnore
	public String errorPage() {
		return "master/error";
	}
	@RequestMapping(value="/noAuth")
	@AuthIgnore
	public String noAuth() {
		return "index/noAuth";
	}
	/**
	 * app检查版本更新
	 * @param versionCode 版本号
	 * @param _appid 
	 * @param os 系统名称Android or iOS
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkVersion")
	@ResponseBody
	@AuthIgnore
	public Result checkVersion(int versionCode, String _appid, String os) throws Exception {
		Result r = new Result();
		if(StringUtil.notBlank(_appid, os)) {
			Map<String,Object> parm = new HashMap<String,Object>();
			parm.put("versionCode", versionCode);
			parm.put("_appid", _appid);
			parm.put("os", os);
			String str = loginSvc.checkVersion(parm);
			r.setData(str);
			r.success();
		}
		return crudError(r);
	}
	/**
	 * 修改密码
	 * @param pass 旧密码
	 * @param newpass 新密码
	 * @return
	 */
	@RequestMapping(value="/changePass")
	@ResponseBody
	@DisposableToken
	public Result changePass(String pass, String newpass) {
		Result r = new Result();
		if(StringUtil.notBlank(pass, newpass)) {
			pass = pass.trim();
			newpass = newpass.trim();
			User user = getUser();
			User u = userSvc.get(user.getId());
			if(u.getPwd().equals(AesUtil.en(pass))) {
				u.setPwd(AesUtil.en(newpass));
				u.setIsLocking("2");
				userSvc.update(u);
				
				tokenManager.loginOff(CookieUtil.getToken(getRequest()));
				tokenManager.setUserValidity(u.getId(), new Date().getTime());  
				
				r.success();
			}else {
				r.setError("旧密码不对");
			}
		}
		return crudError(r);
	}
	/**
	 * 登陆-pc
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/loginPc",method = RequestMethod.POST)
	@ResponseBody
	@AuthIgnore
	public Result loginPc(LoginParam param) {
		param.setLogintype(ProtocolConstant.LoginType.pc); 
		return crudError(loginSvc.login(param));
	}
	/**
	 * 登陆-app
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/loginApp",method = RequestMethod.POST)
	@ResponseBody
	@AuthIgnore
	public Result loginApp(LoginParam param) throws Exception {  
		String data = getRequestParam("_data_");//APP登录加密后数据
		if(StringUtil.notBlank(data)) {  
			JSONObject json = JsonUtil.stringToJSON(SenAesUtil.dn(data));
			param.setUsername(StringUtil.ValueOf(json.get("username"))); 
			param.setPassword(StringUtil.ValueOf(json.get("password"))); 
			param.setImei(StringUtil.ValueOf(json.get("imei"))); 
			param.setModel(StringUtil.ValueOf(json.get("model"))); 
		}
		if(StringUtil.isBlank(param.getImei())) {
			return crudError(new Result().error("未获取到手机串号"));   
		}
		param.setLogintype(ProtocolConstant.LoginType.app); 
		return crudError(loginSvc.login(param));
	}
	/**
	 * 登陆-二维码
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/loginQrcode",method = RequestMethod.POST)
	@ResponseBody
	public Result loginQrcode(String code, String type) {
		Result r = new Result();
		if(StringUtil.notBlank(code)) {
			QrcodeInfo v = (QrcodeInfo)redisManager.getRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code);
			if(v != null && "1".equals(v.getResult())) {  
				if("1".equals(type)) {
					v.setResult("2"); 
					LoginParam param = new LoginParam();
					param.setLogintype(ProtocolConstant.LoginType.qrcode); 
					r = loginSvc.login(param);
					v.setToken(StringUtil.ValueOf(r.getData()));
					r.setData(null);
				}else {
					v.setResult("3");  
				}
				redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code, v, Long.parseLong(BasicsFinal.getParamVal("sys_qrcode_times")));
				r.success();
			}else {
				r.error("二维码已失效");
			}
		}
		return crudError(r);
	}
	
	/**
	 * 获取二维码文本
	 * @return
	 */
	@RequestMapping("/getQrcode")
	@ResponseBody
	@AuthIgnore
	public Result getQrcode() {
		String code = UUID.randomUUID().toString().replace("-", "");
		QrcodeInfo v = new QrcodeInfo();
		redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code, v, Long.parseLong(BasicsFinal.getParamVal("sys_qrcode_times")));
		return crudError(new Result().setData(code).success());
	}
	
	/**
	 * 获取二维码登陆状态
	 * @param code 二维码文本
	 * @return
	 */
	@RequestMapping("/getQrcodeResult")
	@ResponseBody
	@AuthIgnore
	public Result getQrcodeResult(String code) {
		Result r = new Result();
		if(StringUtil.notBlank(code)) {
			QrcodeInfo v = (QrcodeInfo)redisManager.getRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code);
			if(v == null) {
				v = new QrcodeInfo();
				v.setResult("9"); 
			}else if("2".equals(v.getResult())){
				CookieUtil.addToken(HttpReqtRespContext.getResponse(), v.getToken());
				v.setToken(null);  
				redisManager.removeRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code);  
			}
			r.setData(v).success();
		}
		return crudError(r);
	}
	
	/**
	 * APP扫描二维码
	 * @param code 二维码文本
	 * @return
	 */
	@RequestMapping(value="/scanQrcode",method = RequestMethod.POST)
	@ResponseBody
	public Result scanQrcode(String code) {
		Result r = new Result();
		if(StringUtil.notBlank(code)) {
			QrcodeInfo v = (QrcodeInfo)redisManager.getRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code);
			if(v != null && "0".equals(v.getResult())) {  
				User u = getUser();
				//v.setId(u.getId());
				v.setUsername(u.getUserName());
				v.setNickname(u.getNickName());
				v.setResult("1");  
				redisManager.setRedisStorage(ProtocolConstant.RedisPrefix.qrcode, code, v, Long.parseLong(BasicsFinal.getParamVal("sys_qrcode_times")));
				r.success();
			}else {
				r.error("二维码已失效");
			}
		}
		return crudError(r);
	}
}
