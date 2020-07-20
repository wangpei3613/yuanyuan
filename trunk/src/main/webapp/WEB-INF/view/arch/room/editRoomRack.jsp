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
		<input type="hidden" name="arch_room_id" value="${v.arch_room_id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">档案架编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="rack_no" value="${v.rack_no }" required/></td>
			</tr>
			<tr>
				<td>档案架层数：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="rack_layer" value="${v.rack_layer }" validType="positiveInteger" required/></td>
			</tr>
			<tr>
				<td>每层盒数：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="rack_layer_column" value="${v.rack_layer_column }" validType="positiveInteger" required/></td>
			</tr>
		</table>
	</form>
</body>
</html>