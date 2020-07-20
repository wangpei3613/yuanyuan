<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>环节走向配置编辑</title>
	<%@ include file="../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="actid" value="${v.actid }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">处理顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="ordernum" validType="integer" value="${v.ordernum }" required/></td> 
			</tr>
			<tr>
				<td>当前环节：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.curr_tache_id }" data-options="url:'${path}/act/config/tache/getTacheDict?actid=${param.actid }',valueField:'code',textField:'name'" name="curr_tache_id" required/></td>
			</tr>
			<tr>
				<td>下环节：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.next_tache_id }" data-options="url:'${path}/act/config/tache/getTacheDict?actid=${param.actid }',valueField:'code',textField:'name'" name="next_tache_id" required/></td>
			</tr>
			<tr>
				<td>类别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.trend_type }" data-options="url:'${path}/sys/sysData?type=SEN_ACT_TREND_TYPE'" name="trend_type" required/></td>
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.state }" data-options="url:'${path}/sys/sysData?type=SEN_ENABLE_STATE'" name="state" required/></td>
			</tr>
			<tr>
				<td>分支条件：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.branch_condition }" name="branch_condition" multiline="true"/></td> 
			</tr>
			<tr>
				<td>分支条件描述：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.branch_condition_remark }" name="branch_condition_remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>