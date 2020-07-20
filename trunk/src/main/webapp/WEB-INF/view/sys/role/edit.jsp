<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">角色编码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required/></td> 
			</tr>
			<tr>
				<td>角色名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="roleName" value="${v.roleName }" required/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<%-- <tr>
				<td>是否验证信贷系统密码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.checkcreditpassstate }" data-options="url:'${path }/sys/sysData?type=SEN_IF'" name="checkcreditpassstate" required/></td> 
			</tr> --%>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>