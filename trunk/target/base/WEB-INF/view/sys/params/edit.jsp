<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<form action="">
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">参数编码：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="id" value="${v.id }" readonly/></td> 
			</tr>
			<tr>
				<td>参数名称：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" readonly/></td> 
			</tr>
			<tr>
				<td>参数值：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="value" value="${v.value }" required/></td> 
			</tr>
			<tr>
				<td>参数参考值：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.reference }" name="reference" readonly/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true" readonly/></td> 
			</tr>
		</table>
	</form>
</body>
</html>