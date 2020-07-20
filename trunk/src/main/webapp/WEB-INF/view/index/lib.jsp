<%@page import="java.util.Map"%>
<%@page import="com.sensebling.system.vo.UserModuleAuth"%>
<%@page import="com.sensebling.common.util.StringUtil"%>
<%@page import="com.sensebling.system.entity.ModuleAuth"%>
<%@page import="java.util.List"%>
<%@page import="com.sensebling.system.finals.BasicsFinal"%>
<%@page import="com.sensebling.common.util.JsonUtil"%>
<%@page import="com.sensebling.system.entity.User"%>
<%@page import="com.sensebling.common.controller.HttpReqtRespContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
User user = HttpReqtRespContext.getUser();
request.setAttribute("path", path);
request.setAttribute("user", user);
String imgPath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+BasicsFinal.getParamVal("file.virtual.route");
String paths = request.getScheme() + "://"+ request.getServerName() + ":"+ request.getServerPort();
//读取主题
Cookie[] cookies = request.getCookies();
String easyuiThemes = "metro-gray";
if(cookies !=null){
    for(int i = 0;i < cookies.length;i++){  
        if(cookies[i].getName().equals("easyuiThemes")){
        		easyuiThemes = cookies[i].getValue();
        }
    }
}
//读取用户权限
UserModuleAuth userModuleAuth = HttpReqtRespContext.getUserModuleAuth();
if(null==userModuleAuth){
	request.getRequestDispatcher("/").forward(request, response);
	return;
}
if(StringUtil.notBlank(request.getParameter("__"))){//判断菜单按钮权限
	String moduleid = request.getParameter("__");
	Map<String, List<ModuleAuth>> map = userModuleAuth.getModuleAuths();
	if(map.containsKey(moduleid)){  
		request.setAttribute("moduleAuths", map.get(moduleid));  
	}
}
request.setAttribute("authFunctionCode", userModuleAuth.getAuthFunctionCode());
response.getClass();
%>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="shortcut icon" href="${path}/styles/images/favicon.ico"  type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${path}/styles/frame/jquery-easyui/themes/<%=easyuiThemes %>/easyui.css">
     <link rel="stylesheet" type="text/css" href="${path}/styles/frame/jquery-easyui/themes/<%=easyuiThemes %>/style.css">
	<link rel="stylesheet" type="text/css" href="${path}/styles/frame/jquery-easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${path}/styles/frame/jquery-easyui/themes/color.css">
	<link rel="stylesheet" type="text/css" href="${path}/styles/style/icon.css">
	<link rel="stylesheet" type="text/css" href="${path}/styles/style/sen.css">
	<script type="text/javascript" src="${path}/styles/frame/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${path}/styles/frame/ajaxhook/ajaxhook.min.js"></script>
	<script type="text/javascript" src="${path}/styles/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${path}/styles/frame/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${path}/styles/frame/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${path}/styles/frame/jquery-easyui/jquery.easyui.validate.js?_a=1.0"></script>
    <script type="text/javascript" src="${path}/styles/frame/layer/src/layer.js"></script>
    <script type="text/javascript">
 		window.sen = {
		    	path:'${path}',
		   		user:'',
		   		imgPath:'<%=imgPath%>',
		   		isformal:<%=BasicsFinal.getParamVal("environment.isformal") %>,
		   		easyuiThemes:'<%=easyuiThemes %>',
		   		userRoles:'',
		   		disposableToken:'${disposableToken }'
		    };
		    if(sen.isformal){
		    		sen.imgPath = sen.path+'/sen/core/attach/getImg?path=';
			}
    
    $(function(){
    });
	</script>
    <script type="text/javascript" src="${path}/styles/js/common.js"></script>
    <script type="text/javascript" src="${path}/styles/js/autoCalc.js?v=5"></script>
