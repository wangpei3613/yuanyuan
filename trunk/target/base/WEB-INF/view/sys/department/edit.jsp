<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>部门编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">部门编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="deptCode" value="${v.deptCode }" required/></td> 
			</tr>
			<tr>
				<td>部门名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="fullName" value="${v.fullName }" required/></td> 
			</tr>
			<tr>
				<td>父级部门：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combotree" value="${v.pid }" data-options="url:'${path }/system/depart/getDeptTree'" name="pid" required/></td> 
			</tr>
			<tr>
				<td>部门电话：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="phone" value="${v.phone }" validType="phoneAndMobile"/></td> 
			</tr>
			<tr>
				<td>排列顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="orderIndex" value="${v.orderIndex }" validType="integer" required/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>