<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>风险模型编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">模型编码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required/></td> 
			</tr>
			<tr>
				<td>模型名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>