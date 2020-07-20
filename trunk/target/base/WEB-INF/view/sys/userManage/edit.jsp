<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		if('${v.id }'){
			$('#userName').textbox('readonly');
		}
	});
	</script>
</head>
<body>
	<form id="formId" action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">账号：<span class="sen-required">*</span></td>
				<td><input type="text" id="userName" class="sen-edit-element easyui-textbox" name="userName" value="${v.userName }" required/></td> 
			</tr>
			<tr>
				<td>昵称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="nickName" id="nickName"  value="${v.nickName}" required/></td> 
			</tr>
			<tr>
				<td>隶属部门：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combotree" value="${v.deptId }" data-options="url:'${path }/system/depart/getDeptTree'" name="deptId" required/></td> 
			</tr>
			<tr>
				<td>部门视野控制：</td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.deptControl }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="deptControl"/></td> 
			</tr>
			<tr>
				<td>联系电话：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.linkPhone }" name="linkPhone" validType="mobile"/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>默认系统：</td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.defaultsys }" data-options="url:'${path }/system/module/getSysDict'" name="defaultsys"/></td> 
			</tr>
			<tr>
				<td>串号：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.serialNo }" name="serialNo" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>