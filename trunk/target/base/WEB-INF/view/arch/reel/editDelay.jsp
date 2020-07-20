<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>文件编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">到期时间：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-datebox" name="expire_date" required/></td>
			</tr>
		</table>
	</form>
</body>
</html>