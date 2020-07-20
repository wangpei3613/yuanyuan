<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>征信规则编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<table class="sen-edit-table">
			<tr>
				<td style="width: 30%;">规则编码：<span class="sen-required">*</span></td>
				<td><input type="text" readonly class="sen-edit-element easyui-textbox" name="id" value="${v.id }" required style="width: 250px;"/></td> 
			</tr>
			<tr>
				<td>规则名称：<span class="sen-required">*</span></td>
				<td><input type="text" readonly class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required style="width: 250px;"/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" style="width: 250px;" name="status" required/></td> 
			</tr>
			<tr>
				<td>规则说明：</td>
				<td colspan="3"><input type="text" readonly class="sen-edit-element easyui-textbox" name="content" value="${v.content }" multiline="true" style="width: 250px;height: 50px;"/></td>
			</tr>
			<tr>
				<td>提示信息：<span class="sen-required">*</span></td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="title" value="${v.title }" required style="width: 250px;"/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true" style="width: 250px;height: 50px;"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>