<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案架编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="arch_room_rack_id" value="${v.arch_room_rack_id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">档案盒编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="box_no" value="${v.box_no }" required/></td>
			</tr>
			<tr>
				<td>档案盒卷数：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="box_num" value="${v.box_num }" validType="positiveInteger" required/></td>
			</tr>
			<tr>
				<td>所在层数：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="layer" value="${v.layer }" validType="positiveInteger" required/></td>
			</tr>
			<tr>
				<td>所在盒数：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="column" value="${v.column }" validType="positiveInteger" required/></td>
			</tr>
		</table>
	</form>
</body>
</html>