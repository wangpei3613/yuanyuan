<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案目录编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">目录编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="menu_no" value="${v.menu_no }" required/></td>
			</tr>
			<tr>
				<td>目录名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="menu_name" value="${v.menu_name }" required/></td>
			</tr>
			<tr>
				<td>父级菜单：</td>
				<td><input type="text" class="sen-edit-element easyui-combotree" value="${v.pid }" data-options="url:'${path }/arch/menu/getTreeGrid'" name="pid"/></td>
			</tr>
			<tr>
				<td>排序号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="ordernumber" value="${v.ordernumber }" validType="integer" required/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>