<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored ="false" %>
<%
request.setAttribute("path", request.getContextPath());

%>
<!DOCTYPE html>
<html>
<head>
	<title>登陆页面</title>
        <meta name="viewport" content="width=device-width" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <link rel="shortcut icon" href="${path}/styles/images/favicon.ico"  type="image/x-icon" />
        
        <link rel="stylesheet" type="text/css" href="${path}/styles/base.css"/>
        <link rel="stylesheet" type="text/css" href="${path}/styles/style.css"/>
        <script type="text/javascript" src="${path}/styles/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="${path}/styles/js/jquery.qrcode.min.js"></script>
  <style type="text/css">
    .divcontainer{
      margin:0 auto;
      width:200px;
      height: 200px;  
    }
     canvas{  
        border:1px solid black;  
        margin-left: -10;
        margin-right: auto;
        width:200px;
        height: 200px;
     } 
  </style>
        <script type="text/javascript">
        
        	$(function(){
        		 
				isScanCode();
        	});
        	
        	/**
    		 *请求服务器，查看web页面的二维码是否被扫描,扫描了自动登录
    		 **/
    		function isScanCode(){
    			var uuid = getCookie("tznsh_uuid");
    			if(uuid == ""||null==uuid) uuid="-1";
    			var username =getCookie('username');
    			$.ajax({  
    				url:"${path}/login/doLogin",
    			    type:'post',
    			    data:{username:username,uuids:uuid},//
    			    cache: false,
    			    async: false,//同步
    			    success:function(result,status){
    			    	if(status == "success"){
    			    		if(result.success){
    			    			if(uuid=="-1"){
    			    				uuid = result.data;
    			    				$("#code").html("");
    			    				$("#code").qrcode(uuid);
    			    				delCookie("tznsh_uuid");
    			    				setCookie("tznsh_uuid",uuid);
    			    			} else{
    			    				if("ok"==result.data){
    			    					delCookie("tznsh_uuid");
    			    					location.reload();
    			    				}else{
    			    					uuid = result.data;
        			    				$("#code").html("");
        			    				$("#code").qrcode(uuid);
        			    				delCookie("tznsh_uuid");
        			    				setCookie("tznsh_uuid",uuid);
    			    				}
    			    			}
    			    		}else{
    			    			delCookie("tznsh_uuid");
    			    			alert(result.message);
    			    			location.reload();
    			    			return false;
    			    		}
    			        }
    			    },
    			    error:function(xhr,textStatus,errorThrown){
    			    	delCookie("tznsh_uuid");
    			    	var msg = xhr.responseJSON.message;
    			    	alert(xhr.responseJSON.message);
    			    	location.reload();
    			    }  
    			});
    			setTimeout("isScanCode()",1000);
    		}
        	
        	 
        	//读取cookies 
        	function getCookie(name){ 
     		    
        	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        	    if(arr=document.cookie.match(reg))
        	        return unescape(arr[2]); 
        	    else 
        	        return null; 
        	}
        	//写cookies 
        	function setCookie(name,value) { 
        	    var Days = 30; 
        	    var exp = new Date(); 
        	    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
        	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
        	}
        	//删除cookies 
        	function delCookie(name) { 
        	    var exp = new Date(); 
        	    exp.setTime(exp.getTime() - 1); 
        	    var cval=getCookie(name); 
        	    if(cval!=null) 
        	        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
        	}
        </script>
        <!--[if lte IE 11]>
		<script>
		$(function(){
			$('body').html('<h1 style="text-align:center;margin-top:10em;">本系统不支持IE11以下浏览器访问！</h1>');
		});
		</script>
		<![endif]-->
</head>
<body>
    <div class="bg"></div>
    <div class="container">
        <div class="line bouncein">
            <div class="xs6 xm4 xs3-move xm4-move">
                <div style="height:150px;"></div>
                <div class="media media-y margin-big-bottom">
                </div>
                <form>
                    <div class="panel loginbox">
                        <div class="text-center margin-big padding-big-top">
                            <h1>江苏农商行移动运营管理系统</h1>
                        </div>
							<div id="code" class="divcontainer"  ></div>
							<div style="padding:30px;text-align: center;">
								<font size="3" color="red" face="微软雅黑,黑体">请用手机登录系统扫描二维码</font>
							</div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>