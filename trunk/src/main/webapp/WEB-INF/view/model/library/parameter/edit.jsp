<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标参数编辑</title>
	<%@ include file="../../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="indexid" value="${v.indexid }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">参数编码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required/></td> 
			</tr>
			<tr>
				<td>参数名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.name }" name="name" required/></td> 
			</tr>
			<tr>
				<td>值类别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.valuetype }" data-options="url:'${path}/sys/sysData?type=MODEL_INDEX_PARAMETER_VALUE_TYPE'" name="valuetype" required/></td>
			</tr>
			<tr>
				<td>排序号：<span class="sen-required">*</span></td>
				<td><input id="tache_type" type="text" class="sen-edit-element easyui-numberbox" value="${v.sort }" validType="integer" name="sort" required/></td>
			</tr>
			<tr>
				<td>取值类别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.type }" data-options="url:'${path}/sys/sysData?type=MODEL_INDEX_PARAMETER_TYPE'" name="type" required/></td>
			</tr>
			<tr>
				<td>参数值：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.value }" name="value" multiline="true" required/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>