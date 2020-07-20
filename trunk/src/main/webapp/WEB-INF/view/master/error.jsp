<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored ="false" %>
<%
request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
	<title>页面没有找到</title>
        <meta name="viewport" content="width=device-width" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

        <link rel="shortcut icon" href="${path}/styles/images/favicon.ico"  type="image/x-icon" />
        
        <link rel="stylesheet" type="text/css" href="${path}/styles/error.css"/>
</head>
<body>
    <div id="container">
		<img class="png" src="${path}/styles/images/404.png" />
		<img class="png msg" src="${path}/styles/images/404_msg.png" />
		<p><a href="${path}/"><img class="png" src="${path}/styles/images/404_to_index.png" /></a> </p>
	</div>
	
	<div id="cloud" class="png"></div>
</body>
</html>