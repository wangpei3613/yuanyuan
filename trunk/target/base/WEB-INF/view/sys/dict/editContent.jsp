<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>数据字典子项编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="typeId" value="${v.typeId }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">编码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="dictionaryCode" value="${v.dictionaryCode }" required/></td> 
			</tr>
			<tr>
				<td>名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="dictionaryName" value="${v.dictionaryName }" required/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>排序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="ordNumber" value="${v.ordNumber }" validType="integer" required/></td> 
			</tr>
			<tr>
				<td>内容：</td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="contents" value="${v.contents }" data-options="precision:2" /></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>