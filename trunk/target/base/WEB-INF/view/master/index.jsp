<%@page import="com.sensebling.system.finals.BasicsFinal"%>
<%@page import="com.sensebling.system.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored ="false" %>
<% 
request.setAttribute("pageCode", "master"); 
User user = (User)session.getAttribute("user");
user.setSerialNo(null);
String imgPath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+BasicsFinal.getParamVal("file.virtual.route");
%>
<!DOCTYPE html>

<html>
    <head>
    	<title>主页面</title>
        <meta name="viewport" content="width=device-width" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <link rel="shortcut icon" href="${path}/styles/images/favicon.ico"  type="image/x-icon" />
        <link href="${path}/styles/js/kindeditor/themes/default/default.css" type="text/css" rel="stylesheet"></link>
    </head>
	<body>

    <%@ include file="../common/common.jsp" %>

     <script id="header_usertitle_template" type="text/template">
        <div id="header_title" class="header-user">
          <span class="text" title="">${username}</span>
          <span class="icon-caret x-fa fa-caret-down"></span>
        </div>
     </script>

     <script id="header_user_template" type="text/template">
       <div id="header_user" class="user-wrapper">
          <span class="icon-up fa fa-sort-up"></span>
          <div class="user-menu clearfix">
              <span class="header-icon"><img src="${path}/extjs/resources/images/gentlemen.png" alt=""></span>
              <ul class="header-userinfo">
                  <li><label><%=user.getNickName()%></label></li>
                  <li>
                      <div><span>用户类型：${userRoles}</span></div>
                      <div><label></label></div>
                  </li>
              </ul>
          </div>
          <a class="btn-logout" data-field="log-out" href="javascript:;"><span class="btn-inner">退出登录</span></a>
       </div>
     </script>

    <script id="working-table-tpl" type="text/template">
        <div class="working-table-wrapper">
            <tpl for=".">
                <div class="working-table-line">
                    <tpl for="items">
                        <a href="javascript:;" class="btn-open-module {className}" data-module-id="{id}">
                            <ul>
                                <li><i class="iconfont {icon}"></i></li>
                                <li><span>{name}</span></li>
                            </ul>
                            <span class="num">{qty}</span>
                        </a>
                    </tpl>
                </div>
            </tpl>
        </div>
    </script>

     <script type="text/javascript" src="${path}/extjs/packages/core/src/util/flexcroll.min.js"></script>
     <script type="text/javascript" src="${path}/extjs/packages/core/src/util/d3.min.js"></script>
     <script type="text/javascript" src="${path}/extjs/packages/core/src/util/snapsvg.min.js"></script>
     <script type="text/javascript" src="${path}/styles/js/jquery-1.11.1.min.js"></script>
     <script type="text/javascript" src="${path}/styles/js/kindeditor/kindeditor-all-min.js"></script>
     <script type="text/javascript" src="${path}/styles/js/kindeditor/lang/zh-CN.js"></script>
     <script type="text/javascript" src="${path}/styles/js/CJL.0.1.min.js"></script>
     <script type="text/javascript" src="${path}/styles/js/ImageTrans.js"></script>
     <script type="text/javascript" src="${path}/styles/js/sen.js"></script>
     <iframe id="printFrame" src="${path}/master/print" style="position:absolute;top:100%;width:100%;left:-5000px;height: 100%;"></iframe>
    </body>
</html>