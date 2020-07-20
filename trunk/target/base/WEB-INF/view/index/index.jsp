<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>主页面</title>
	<%@ include file="../index/lib.jsp" %>
	<link rel="stylesheet" type="text/css" href="${path}/styles/style/index.css">
	<style type="text/css">
    .divcontainer{
      margin:0 auto;
      width:200px;
      height: 200px;  
    }
  </style>
  	<script type="text/javascript">
  	sen.sysurl = {
  		dh:'<%=BasicsFinal.getParamVal("sys.url.dh") %>',
  		ydyy:'<%=BasicsFinal.getParamVal("sys.url.ydyy") %>',
  		'default':'<%=BasicsFinal.getParamVal("sys.url.default") %>'
  	};
  	sen.quickLogon = function(){
  		layer.win({
			  title: '快捷登陆',
			  area: ['800px', '600px'],
			  content: sen.path,
				success:function(layero){
				  win = layero.find('iframe')[0].contentWindow;
				  win.flag = 1;
			  }
		});
  	};
  	</script>
	<script type="text/javascript" src="${path}/styles/js/index.js?a=1"></script>
	<script type="text/javascript" src="${path}/styles/js/jquery.qrcode.js"></script>
	<script type="text/javascript" src="${path}/styles/js/uuid.js"></script>
</head>
<body class="easyui-layout">
	<div class="sen-header" data-options="region:'north',border:false">
    		<div class="sen-header-left">
        		<h1>移动运营管理系统</h1>
        </div>
        <div class="sen-header-center">
        		<ul>
        			
        		</ul>
        </div>
        <div class="sen-header-right">
        		<p><strong class="easyui-tooltip" title="${userRoles}">${user.nickName }</strong>，欢迎您！</p>
            <p><a href="javascript:;" class="act-app" style="display: none;">app下载</a>|<a href="javascript:;" class="act-changepass">修改密码</a>|<a href="javascript:;" class="act-loginout">安全退出</a></p>
        </div>
    </div>
	<div class="sen-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
  		 
    </div>	
    <div class="sen-main" data-options="region:'center'">
        <div id="sen-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="closable:false,iconCls:'icon-tip',cls:'pd3'" style="overflow: hidden;">  
            		<iframe id="homepage" scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
            </div>
        </div>
    </div>
	<div class="sen-footer" data-options="region:'south',border:true">
    		&copy; 2019 润和软件 All Rights Reserved V6.0
    		<div class="sen-theme">
    			主题：<select id="cb-theme" style="width:120px;"></select>
    		</div>
    </div>
    <div id="dd" class="easyui-dialog" title="app下载" style="margin: 10px 2px 0px 2px;"
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed: true">
         <div id="codes" class="divcontainer"></div>
    </div>
    <div id="mm" class="easyui-menu" style="width:150px;">
		<div data-options="iconCls:'icon-reload'" id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
	</div>
</body>
</html>