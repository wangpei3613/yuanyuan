<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案借阅编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<input type="hidden" id="arch_reel_id" name="arch_reel_id" value="${v.arch_reel_id }"/>
		<table class="sen-edit-table">
			<tr>
				<td>案卷：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-texthbox" id="reel_name" name="reel_name" value="${v.reel_name }" reaonly/></td>
			</tr>
			<tr>
				<td style="width: 35%;">借阅时间：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-datebox" name="brow_date" value="${v.brow_date }" required/></td>
			</tr>
			<tr>
				<td>归还时间：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-datebox" name="back_date" value="${v.back_date }" required/></td>
			</tr>
		</table>
	</form>
</body>
</html>