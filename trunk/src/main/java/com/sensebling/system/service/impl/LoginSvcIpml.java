package com.sensebling.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensebling.common.constant.ProtocolConstant;
import com.sensebling.common.controller.HttpReqtRespContext;
import com.sensebling.common.service.impl.BasicsSvcImpl;
import com.sensebling.common.util.AesUtil;
import com.sensebling.common.util.CookieUtil;
import com.sensebling.common.util.DateUtil;
import com.sensebling.common.util.JsonUtil;
import com.sensebling.common.util.MyServiceException;
import com.sensebling.common.util.Result;
import com.sensebling.common.util.SenAesUtil;
import com.sensebling.common.util.StringUtil;
import com.sensebling.system.bean.LoginParam;
import com.sensebling.system.context.TokenManager;
import com.sensebling.system.entity.AppVersion;
import com.sensebling.system.entity.LoginLog;
import com.sensebling.system.entity.User;
import com.sensebling.system.finals.BasicsFinal;
import com.sensebling.system.service.AppVersionSvc;
import com.sensebling.system.service.LoginLogSvc;
import com.sensebling.system.service.LoginSvc;
import com.sensebling.system.service.ModuleAuthSvc;
import com.sensebling.system.service.UserSvc;

@Service
public class LoginSvcIpml extends BasicsSvcImpl<User> implements LoginSvc {

	@Resource
	private UserSvc userSvc;
	@Resource
	private LoginLogSvc loginLogSvc;
	@Resource
	private ModuleAuthSvc moduleAuthSvc;
	@Resource
	private AppVersionSvc appVersionSvc;
	@Autowired
	private TokenManager tokenManager;
	
	@Override
	public Result doLogin(Map<String,Object> maps ) throws Exception {
		Result r = new Result();
		String data = StringUtil.sNull(maps.get("data"));//APP登录加密后数据
		String imei = StringUtil.sNull(maps.get("data"));
		String model = StringUtil.sNull(maps.get("data"));
		String username = StringUtil.sNull(maps.get("username"));
		String password = StringUtil.sNull(maps.get("password"));
		String loginFlag = StringUtil.sNull(maps.get("loginFlag"));
		if("".equals(loginFlag))throw new MyServiceException("登录标志不能为空");
		boolean isApp = (boolean)maps.get("isApp");
		if(isApp && StringUtil.notBlank(data)) {  
			JSONObject json = JsonUtil.stringToJSON(SenAesUtil.dn(data));
			username = json.getString("username");
			password = json.getString("password");
			imei = json.getString("imei");
			model = json.getString("model");
		}
		
		User u=userSvc.getUserByName(username, null);
		if(u==null) {
			r.setError("用户名不正确！");
			return r;
		}
		if(u.getPwd().equals(AesUtil.en(password))||"E".equals(loginFlag)){  
			if(u.getStatus().equals("2")){
//				log.msgPrint("用户登录--[账号:"+username+"][冻结]");
				r.setError("用户已停用！");
				return r;
			}
			if(isApp && "true".equals(BasicsFinal.getParamVal("system.login.checkimei"))) {//APP登录判断串号
				if(StringUtil.notBlank(u.getSerialNo())) {
					if(StringUtil.notBlank(imei)) {
						if(StringUtil.getTheSameSection(new ArrayList<Object>(Arrays.asList(u.getSerialNo().split(","))),new ArrayList<Object>(Arrays.asList(imei.split(",")))).size() == 0) {
							r.setError("该设备不能登录，请联系管理员！");
							return r;
						}
					}else {
						return r;
					}
				}else {
					r.setError("请联系管理员配置手机串号！");
					return r;
				}
			}
			
			
//			getSession().setAttribute("user", u);//将用户信息放入session
//			getSession().setAttribute("moduleAuth", moduleAuthSvc.getUserModuleAuth(u.getId()));//将用户权限信息放入session
//			log.msgPrint("用户登录--[账号:"+username+"][成功]");
			
			//添加登录日志
			LoginLog log = new LoginLog();
			log.setLogintime(DateUtil.getStringDate());
			log.setLoginuser(u.getId());
			if(isApp) {
				log.setType("2");
				log.setImei(imei);
				log.setModel(model);
			}else {
				log.setType("1");
			}
			if("E".equals(loginFlag)){
				log.setType("3");
			}
			loginLogSvc.save(log);
//			getSession().setAttribute("loginlog", log.getId());//将登录日志信息放入session
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isformal", BasicsFinal.getParamVal("environment.isformal"));
			map.put("loginlog", log.getId());
			map.put("user", u);
			r.setData(map);  
			r.success();
		}else{
			r.setError("密码错误！");
		}
		return r;
	}


	/**
	 * 生成二维码
	 * @param content
	 * @param imgPath
	 * @param ccbPath
	 * @throws Exception
	 */
	public String createQRCode(HttpServletRequest request) throws Exception {
		String fileStr = System.getProperty("catalina.home")+"/webapps/";// 获取当前项目部署路径
		String UUID = java.util.UUID.randomUUID().toString();// 获取唯一标识ID
		String content = UUID + "@";
		String ccbPath = fileStr + "images/login/codelogo.png";// 获取logo文件路径(二维码logo标志路径)
		String imaPath = fileStr + "images/dimensioncode/" + UUID+ ".png";// 存放生成的二维码路径
//		System.out.println("ccbPath="+ccbPath+" imaPath="+imaPath);
		File file = new File(fileStr+"/images/login");
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(fileStr+"/images/dimensioncode");
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(fileStr+"/images/login/codelogo.png");
		if(!file.exists()){
			file.createNewFile();
		}
		file = new File(imaPath);
		if(!file.exists()){
			file.createNewFile();
		}
		createQRCode(content, imaPath, ccbPath);// 生成二维码
		String ipAdd = StringUtil.getIpAddress(request);
		String sqlString="insert into sen_m_dimencode (uuid,ip,is_sm,is_overtime,cretime,sx_time) values ('"
			             +UUID+"','"+ipAdd+"','0','0',current timestamp,TIMESTAMP (CURRENT TIMESTAMP +0 day)+0 HOUR +5 MINUTE)"; 
		int len = baseDao.executeSQL(sqlString);
		if(len == 0) throw new MyServiceException("执行失败,请刷新页面后重新操作！");
		return UUID;
	}
	
	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content二维码图片的内容
	 * @param imgPath生成二维码图片完整的路径
	 * @param ccbpath二维码图片中间的logo路径
	 */
	public void createQRCode(String content, String imgPath,String ccbPath) throws Exception {
//		Qrcode qrcodeHandler = new Qrcode();
//		qrcodeHandler.setQrcodeErrorCorrect('M');
//		qrcodeHandler.setQrcodeEncodeMode('B');
//		qrcodeHandler.setQrcodeVersion(7);
//
//		byte[] contentBytes = content.getBytes("gb2312");
//		BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);
//		Graphics2D gs = bufImg.createGraphics();
//
//		gs.setBackground(Color.WHITE); 
//		gs.clearRect(0, 0, 160, 160);
//		
//		gs.setColor(Color.BLACK);// 设定图像颜色 > BLACK
//
//		int pixoff = 2;// 设置偏移量 不设置可能导致解析出错
//		// 输出内容 > 二维码
//		if (contentBytes.length > 0 && contentBytes.length < 120) {
//			boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
//			for (int i = 0; i < codeOut.length; i++) {
//				for (int j = 0; j < codeOut.length; j++) {
//					if (codeOut[j][i]) {
//						gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//					}
//				}
//			}
//		} else {
//			throw new MyServiceException("QRCode content bytes length = "+ contentBytes.length + " not in [ 0,120 ]. ");
//		}
//		Image img = ImageIO.read(new File(ccbPath));// 实例化一个Image对象。
//		gs.drawImage(img, 50, 50, null);
//		gs.dispose();
//		bufImg.flush();
//		
//		File imgFile = new File(imgPath);// 生成二维码QRCode图片
//		ImageIO.write(bufImg, "png", imgFile);
	}
	
	public String selectQrCode(HttpServletRequest request, String uuid) throws Exception {
		if (uuid.length() == 0) throw new MyServiceException("参数有误");
		String sql="SELECT UUID,USERID,IS_SM FROM SEN_M_DIMENCODE WHERE UUID='"+uuid+"' AND CURRENT TIMESTAMP < SX_TIME AND LOGIN_TIME IS NULL "; 
//		Map<String, Object> map = (Map<String, Object>) splitPageDao.SqlUniqueResult2Map(sql);
		List<Map<String,Object>> lists = baseDao.queryBySql_listMap(sql);
		if(lists == null||lists.isEmpty()) {//过期
			String filePath = System.getProperty("catalina.home")+ "/webapps/images/dimensioncode/" + uuid + ".png";
			File file = new File(filePath);
			if (file.exists() && file.isFile()) file.delete();
			throw new MyServiceException("二维码已过期,请 确定 后再用手机扫一扫!");
		} else if(StringUtil.sNull(lists.get(0).get("IS_SM")).equals("0")) {
			//throw new MyServiceException("请先登录手机端,使用扫一扫后登录!");
		} else {
			String filePath = System.getProperty("catalina.home")+ "/webapps/images/dimensioncode/" + uuid + ".png";
			File file = new File(filePath);
			if (file.exists() && file.isFile()) file.delete();
			//登录成功
			baseDao.executeSQL("UPDATE SEN_M_DIMENCODE SET LOGIN_TIME=CURRENT TIMESTAMP WHERE UUID='"+uuid+"'");			
			return StringUtil.sNull(lists.get(0).get("USERID"));
		}
		return "";
	}
	
	/**
	 * 
	 * @param paraMap
	 * @throws Exception
	 */
	public Result updateQrCode(Map<String, Object> paraMap) throws Exception {
		Result r = new Result();
		String uuid = StringUtil.sNull(paraMap.get("uuid"));
		String username = StringUtil.sNull(paraMap.get("username"));
		if (uuid.length() == 0 || username.length() == 0){
			r.setError("请求参数为空,请刷新页面重新扫描!");
			return r;
		}
		String[] uuids = uuid.split("@",-1);
		String sql = "update sen_m_dimencode set is_sm='1',USERID='" + username
				+ "' where uuid='" + uuids[0]
				+ "' and is_sm='0' and current timestamp < sx_time ";
		int len = baseDao.executeSQL(sql);
		if (len == 0){
			r.setError("二维码已过期,请刷新页面重新扫描!");
			return r;
		}
		r.success();
		return r;
	}

	/**
	 * 检查版本
	 * @param _appid
	 * @param os
	 * @return
	 * 2018年7月11日-下午2:06:49
	 * YY
	 */
	@Override
	public String checkVersion(Map<String,Object> parm) throws Exception {
		if(null==parm||parm.isEmpty())throw new MyServiceException("版本检查参数传递有误，请检查。");
		String types = StringUtil.sNull(parm.get("os"));
		int type = "Android".equals(types)?1:2;
		AppVersion appVersion = appVersionSvc.getListByTime(type);
		if(null==appVersion)throw new MyServiceException("请联系管理员上传最新的app");
		int versionCode = StringUtil.iNull(parm.get("versionCode"));
		String currentVs = appVersion.getCurrentV().replace(".", "");
		int currentV = StringUtil.iNull(currentVs);
		if(versionCode<currentV) {
			return appVersion.getDownUrls();
		}
		return null;
	}


	@Override
	public Result login(LoginParam param) {
		Result r = new Result();
		User u = null;
		if(ProtocolConstant.LoginType.pc.equals(param.getLogintype()) 
				|| ProtocolConstant.LoginType.app.equals(param.getLogintype())) {
			if(!StringUtil.notBlank(param.getUsername(), param.getPassword())) {
				return r.error("用户名或密码为空");
			}
			String username = param.getUsername().trim();
			String password = param.getPassword().trim();
			u = userSvc.getUserByName(username, null);
			if(u==null) {
				return r.error("用户名不正确！");
			}
			if(!u.getPwd().equals(AesUtil.en(password))) {
				return r.error("密码错误");
			}
			if(u.getStatus().equals("2")){
				return r.error("用户已停用！");
			}
			if(ProtocolConstant.LoginType.app.equals(param.getLogintype()) 
					&& "true".equals(BasicsFinal.getParamVal("system.login.checkimei"))) {//APP登录判断串号
				if(StringUtil.isBlank(u.getSerialNo())) {
					return r.error("请联系管理员配置手机串号！");
				}
				if(StringUtil.getTheSameSection(new ArrayList<Object>(Arrays.asList(u.getSerialNo().split(","))),new ArrayList<Object>(Arrays.asList(param.getImei().split(",")))).size() == 0) {
					return r.error("该设备不能登录，请联系管理员！");
				}
			}
			u.setLogintype(param.getLogintype());
		}else if(ProtocolConstant.LoginType.qrcode.equals(param.getLogintype())){
			u = userSvc.getUserByName(HttpReqtRespContext.getUser().getUserName(), null);
			u.setLogintype(ProtocolConstant.LoginType.pc);
		}
		/**登陆验证通过后处理**/
		if(u != null) {
			//添加登录日志
			LoginLog log = new LoginLog();
			log.setLogintime(DateUtil.getStringDate());
			log.setLoginuser(u.getId());
			log.setType(param.getLogintype());
			log.setImei(param.getImei());
			log.setModel(param.getModel());
			loginLogSvc.save(log);
			
			//处理用户权限
			u.setLogintime(System.currentTimeMillis());
			u.setLogid(log.getId());
			u.setAuth(moduleAuthSvc.getUserModuleAuth(u.getId()));
			
			//处理token
			String _token_ = tokenManager.getToken(u);
			if(ProtocolConstant.LoginType.qrcode.equals(param.getLogintype())){
				r.setData(_token_);
			}else {
				CookieUtil.addToken(HttpReqtRespContext.getResponse(), _token_);
			}
			
			//处理返回值
			if(ProtocolConstant.LoginType.app.equals(param.getLogintype())){ 
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("isformal", BasicsFinal.getParamVal("environment.isformal"));
				r.setData(map);
			}
			r.success();
		}
		return r;
	}
}
