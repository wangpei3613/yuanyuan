<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案架编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">档案室编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="room_no" value="${v.room_no }" required/></td>
			</tr>
			<tr>
				<td>档案室名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="room_name" value="${v.room_name }" required/></td>
			</tr>
			<tr>
				<td>档案室地址：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.room_address }" name="room_address" multiline="true"/></td>
			</tr>
		</table>
	</form>
</body>
</html>