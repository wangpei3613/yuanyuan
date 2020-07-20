<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored ="false" %>
<%
request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
	<title>打印页面</title>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <style type="text/css">
    html,body{
    		width:100%;
    		height:100%;
    		padding: 0;
    		margin: 0;
    }
    </style>
    <script type="text/javascript" src="${path}/styles/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript">
    $(function(){
    		if (parent) {
            var oHead = document.getElementsByTagName("head")[0];
            var arrStyleSheets = parent.document.getElementsByTagName("link");
            for (var i = 0; i < arrStyleSheets.length; i++)
                oHead.appendChild(arrStyleSheets[i].cloneNode(true));
        }
    });
    </script>
</head>
<body>
    
</body>
</html>