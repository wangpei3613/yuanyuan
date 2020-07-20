<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>文件编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<input type="hidden" name="arch_reel_id" value="${v.arch_reel_id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">文件名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="file_name" value="${v.file_name }" required/></td>
			</tr>
			<tr>
				<td>父级文件：</td>
				<td><input type="text" class="sen-edit-element easyui-combotree" name="pid" value="${v.pid }" data-options="url:'${path }/arch/reel/files/getTreeGrid?file_type=1&arch_reel_id=${v.arch_reel_id }'"/></td>
			</tr>
			<tr>
				<td>文件类型：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" name="file_type" value="${v.file_type }" required  data-options="url:'/archive/sys/sysData?type=SEN_FILE_TYPE'"/></td>
			</tr>
			<tr>
				<td>年度：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="year" value="${v.year }" required/></td>
			</tr>
			<tr>
				<td>文件顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="serial" value="${v.serial }" validType="positiveInteger" required/></td>
			</tr>
		</table>
	</form>
</body>
</html>