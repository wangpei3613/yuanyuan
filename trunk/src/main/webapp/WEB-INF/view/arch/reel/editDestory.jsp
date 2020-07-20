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
		<input type="hidden" name="arch_reel_id" value="${v.arch_reel_id }"/>
		<table class="sen-edit-table">
			<tr>
				<td>注销事由：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="desty_reason" value="${v.desty_reason }" required/></td>
			</tr>
		</table>
	</form>
</body>
</html>