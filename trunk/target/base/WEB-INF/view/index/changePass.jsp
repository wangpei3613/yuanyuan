<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改密码</title>
	<%@ include file="../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">旧密码：<span class="sen-required">*</span></td>
				<td><input type="password" class="sen-edit-element easyui-textbox" name="pass" required/></td> 
			</tr>
			<tr>
				<td>新密码：<span class="sen-required">*</span></td>
				<td><input id="newpass" type="password" class="sen-edit-element easyui-textbox" name="newpass" required/></td> 
			</tr>
			<tr>
				<td>确认新密码：<span class="sen-required">*</span></td>
				<td><input type="password" class="sen-edit-element easyui-textbox" name="newpass2" validType="same[newpass]" required/></td> 
			</tr>
		</table>
	</form>
</body>
</html>