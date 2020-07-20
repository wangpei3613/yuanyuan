<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>申请提交</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">姓名：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td> 
			</tr>
			<tr>
				<td>证件号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" validType="idcard" name="cardid" value="${v.cardid }" required/></td> 
			</tr>
		</table>
	</form>
</body>
</html>