<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>风险规则编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="riskid" value="${v.riskid }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 18%;">规则编码：<span class="sen-required">*</span></td>
				<td style="width: 32%;"><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required/></td>
				<td style="width: 18%;">规则名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td> 
			</tr>
			<tr>
				<td>排序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="sort" value="${v.sort }" validType="integer" required/></td>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path}/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>规则说明：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="content" value="${v.content }" multiline="true" style="width: 570px;height: 50px;"/></td>
			</tr>
			<tr>
				<td>规则条件：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="condition" value="${v.condition }" multiline="true" style="width: 570px;height: 50px;"/></td>
			</tr>
			<tr>
				<td>规则内容：<span class="sen-required">*</span></td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="rule" value="${v.rule }" multiline="true" style="width: 570px;height: 50px;" required/></td>
			</tr>
			<tr>
				<td>提示信息：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="title" value="${v.title }" style="width: 570px"/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true" style="width: 570px;height: 50px;"	/></td> 
			</tr>
		</table>
	</form>
</body>
</html>