<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>征信规则参数编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 30%;">规则编码：<span class="sen-required">*</span></td>
				<td><input type="text" readonly class="sen-edit-element easyui-textbox" name="ruleid" value="${v.ruleid }" required style="width: 250px;"/></td> 
			</tr>
			<tr>
				<td>参数编码：<span class="sen-required">*</span></td>
				<td><input type="text" readonly class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required style="width: 250px;"/></td> 
			</tr>
			<tr>
				<td>参数名称：<span class="sen-required">*</span></td>
				<td><input type="text" readonly class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required style="width: 250px;"/></td> 
			</tr>
			<tr>
				<td>参数说明：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="content" value="${v.content }" multiline="true" style="width: 250px;height: 50px;"/></td>
			</tr>
			<tr>
				<td>参数值：<span class="sen-required">*</span></td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="value" value="${v.value }" required style="width: 250px;"/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true" style="width: 250px;height: 50px;"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>