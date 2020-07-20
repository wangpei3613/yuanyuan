<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">案件编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="reel_no" value="${v.reel_no }" required/></td>
			</tr>
			<tr>
				<td>案件名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="reel_name" value="${v.reel_name }" required/></td>
			</tr>
			<tr>
			<td>所属目录：<span class="sen-required">*</span></td>
			<td><input type="text" class="sen-edit-element easyui-combotree" name="arch_menu_id" value="${v.arch_menu_id }" required  data-options="url:'/archive/arch/menu/getTreeGrid'" readOnly/></td>
		</tr>
			<tr>
				<td>案件类型：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" name="reel_type" value="${v.reel_type }" data-options="url:'/archive/arch/reel/getDalx_list'" required/></td>
			</tr>
			<tr>
				<td>rfid码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="rfid" value="${v.rfid }" /></td>
			</tr>
			<tr>
				<td>到期时间：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-datebox" name="expire_date" value="${v.expire_date }" required/></td>
			</tr>
			<tr>
				<td>档案状态：</td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.reel_status }" name="reel_status" data-options="url:'/archive/sys/sysData?type=SEN_REEL_STATE'" readOnly/></td>
			</tr>
		</table>
	</form>
</body>
</html>