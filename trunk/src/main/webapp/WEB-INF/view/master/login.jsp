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
        <script type="text/javascript" src="${path}/styles/js/jquery.cookie.js"></script>
        <script type="text/javascript" src="${path}/styles/js/jquery.qrcode.min.js"></script>
        <script type="text/javascript">
        	var type = 1;
        	$(function(){
        		
        		$('#username').focus();
        		$('#button').on('click',function(){
        			login();
        		});
        		$(document).on('keypress',function(e){
        			if(type == 1 && e.keyCode == 13){
        				$('#button').trigger('click');
        			}
        		});
        		if($.cookie('username')){
        			$('#username').val($.cookie('username'));
        		}
        		
        	});
        	function login(){
        		var username = $.trim($('#username').val());
        		var password = $.trim($('#password').val());
        		if(!username){
        			alert('请输入用户名');
        			return;
        		}
        		if(!password){
        			alert('请输入密码');
        			return;
        		}
        		$('#button').prop('disabled',true).val('登陆中...');
        		$.ajax({
        			url:'${path}/login/loginPc',
        			type:'post',
        			data:{username:username,password:password},
        			success:function(d){
						$.cookie('username',username,{expires:365*100,path:'/'});
						if(window.flag && window.flag==1){
							top.layer.close(top.layer.getFrameIndex(window.name));
						}else{
	 						location.reload();
						}        				
        			},
        			complete:function(){
        				$('#button').prop('disabled',false).val('登陆');
        			},
        			error:function(d){
        				if(d.status == 450){
        					alert(JSON.parse(d.responseText).error);
        				}
        		    }
        		});
        	}

			function getCookie(name){
				var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
				if(arr=document.cookie.match(reg))
				return unescape(arr[2]);
				else
				return null;
			}

			function setCookie(name,value,time){
				var strsec = getsec(time);
				var exp = new Date();
				exp.setTime(exp.getTime() + strsec*1);
				document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
			}
			function delCookie(name,p){
				var exp = new Date();
				exp.setTime(exp.getTime() - 1);
				var cval=getCookie(name);
				if(cval!=null){
					if(p){
						document.cookie= name + "="+cval+";expires="+exp.toGMTString() + ";path="+p;
					}else{
						document.cookie= name + "="+cval+";expires="+exp.toGMTString() + ";path=/lottery";
					}
					
				}
				
			}
			
			function clearAllCookie() {
                var date=new Date();
                date.setTime(date.getTime()-10000);
                var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
                if (keys) {
                    for (var i =  keys.length; i--;)
                      document.cookie=keys[i]+"=0; expire="+date.toGMTString()+"; path=/";
                }
            }
			
			function switchTab(t,d){
				type = d;
				$('.loginbox').hide();
				$('.loginbox'+d).show();
				if(d == 2){//扫描登陆
					getQrcode();
				}
			}
			function getQrcode(){
				$("#qrcode").empty();
				$.ajax({
        			url:'${path}/login/getQrcode',
        			success:function(d){
        				$("#qrcode").qrcode(d.data);
        				getQrcodeResult(d.data);
        			},
        			error:function(d){
        				if(d.status == 450){
        					alert(JSON.parse(d.responseText).error);
        				}
        		    }
        		});
			}
			function getQrcodeResult(code){
				if(type == 2){
					setTimeout(function() {
						$.ajax({
		        			url:'${path}/login/getQrcodeResult',
		        			data:{code:code},
		        			success:function(d){
		        				d = d.data || {};
		        				if(d.result == '1'){//已扫描
		        					if($('#qrcode .user-info').length == 0){
		        						$('#qrcode').append('<div class="user-info">扫描成功，扫描用户名为'+d.username+'，昵称为'+d.nickname+'，请点击登陆！</div>');
		        					}
		        					getQrcodeResult(code);
		        				}else if(d.result == '2'){//已登陆
		        					$.cookie('username',d.username,{expires:365*100,path:'/'});
		    						if(window.flag && window.flag==1){
		    							top.layer.close(top.layer.getFrameIndex(window.name));
		    						}else{
		    	 						location.reload();
		    						}  
		        				}else if(d.result == '9' || d.result == '3'){//二维码失效或取消登陆 重新获取二维码
		        					getQrcode();
		        				}else{
		        					getQrcodeResult(code);
		        				}
		        			},
		        			error:function(d){
		        				if(d.status == 450){
		        					alert(JSON.parse(d.responseText).error);
		        				}
		        		    }
		        		});
					}, 1000);
				}
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
                    <div class="panel loginbox loginbox1">
                        <div class="text-center margin-big padding-big-top">
                            <h1 style="white-space: nowrap;">江苏农商行移动运营管理系统</h1>  
                        </div>
                        <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                            <div class="form-group">
                                <div class="field field-icon-right">
                                    <input type="text" class="input input-big" name="username" id="username" placeholder="用户名">
                                    <span class="icon icon-user margin-small"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="field field-icon-right">
                                    <input type="password" class="input input-big" name="password" id="password" placeholder="密码">
                                    <span class="icon icon-key margin-small"></span>
                                </div>
                            </div>
                        </div>
                        <div style="padding:30px 30px 15px;">
                            <input type="button" id="button" class="button button-block bg-main text-big input-big" value="登录">
                        </div>
                        <!-- <div class="switch" onclick="switchTab(this,2)">扫描快捷登陆</div> -->
                    </div>
                    
                    <div class="panel loginbox loginbox2" style="display: none;">
                        <div class="text-center margin-big padding-big-top">
                            <h1 style="white-space: nowrap;">江苏农商行移动运营管理系统</h1>  
                        </div>
                        <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                        	<div id="qrcode" style="width: 173px;height: 173px;margin: 0 auto 20px;position: relative;">
                        	</div>
                        </div>
                        <div class="switch" onclick="switchTab(this,1)">账号密码登陆</div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>