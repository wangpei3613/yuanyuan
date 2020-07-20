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
        <script type="text/javascript">
        
        	$(function(){
        		 
        		$(document).on('keypress',function(e){
        			if(e.keyCode == 13){
        				$('#button').trigger('click');
        			}
        		});
        		if(getCookie('username')){
        			$('#username').val(getCookie('username'));
        		}
//         		for(var i=0;i<1000;i++){
//         			isScanCode();
//         		}
        		isScanCode();
    			setTimeout(function(){
    				if($("#username").val()!="")$("#pwd").focus();
    			},1);
        	});
        	
        	/**
    		 *请求服务器，查看web页面的二维码是否被扫描,扫描了自动登录
    		 **/
    		function isScanCode(){
    			var uuid = getCookie("synsh_uuid");
    			if(uuid == ""||null==uuid) uuid="-1";
    			var username =$("#username").val();
    			var  _basePath ='http://127.0.0.1:8080/';
    			$.ajax({  
    				url:"${path}/login/submit",
    			    type:'post',
    			    data:{username:username,password:'21',uuid:uuid},//
    			    cache: false,
    			    async: false,//同步
    			    success:function(result,status){
    			    	if(status == "success"){
    			    		if(result.success){
    			    			if(uuid=="-1"){
    			    				$("#ewmImg").attr("src",_basePath+"images/dimensioncode/"+result.data+".png");
    			    				delCookie("synsh_uuid");
    			    				setCookie("synsh_uuid",result.data);
    			    			} else{
    			    				if("ok"==result.data){
    			    					delCookie("synsh_uuid");
    			    					location.reload();
    			    				}else{
    			    					$("#ewmImg").attr("src",_basePath+"images/dimensioncode/"+result.data+".png");
    			    				}
    			    			}
    			    		}else{
    			    			delCookie("synsh_uuid");
    			    			alert(result.msg);
    			    			location.reload();
    			    			return false;
    			    		}
    			        }
    			    },
    			    error:function(xhr,textStatus,errorThrown){
    			    	delCookie("synsh_uuid");
    			    	isScanCode();
    			    }  
    			});
    			setTimeout("isScanCode()",60000);
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
<!--                         <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;"> -->
<!--                             <div class="form-group"> -->
<!--                                 <div class="field field-icon-right"> -->
<!--                                     <input type="text" class="input input-big" name="username" id="username" placeholder="用户名"> -->
<!--                                     <span class="icon icon-user margin-small"></span> -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                             <div class="form-group"> -->
<!--                                 <div class="field field-icon-right"> -->
<!--                                     <input type="password" class="input input-big" name="password" id="password" placeholder="密码"> -->
<!--                                     <span class="icon icon-key margin-small"></span> -->
<!--                                 </div> -->
<!--                             </div> -->
<!--                         </div> -->
							<div id="code"></div>
							<div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;text-align: center;">
							       <img id="ewmImg" src="" style="width: 180px; height: 180px;" />
							</div>
							<div style="padding:30px;text-align: center;">
								<font size="3" color="red" face="微软雅黑,黑体">请用手机登录系统扫描二维码</font>
							</div>
<!--                         <div style="padding:30px;"> -->
<!--                             <input type="button" id="button" class="button button-block bg-main text-big input-big" value="登录"> -->
<!--                         </div> -->
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>