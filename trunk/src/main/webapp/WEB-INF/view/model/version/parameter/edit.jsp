<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标参数编辑</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	function init(d){
		if(d.id){
			$('#code').textbox('readonly');
		}
		$('form').form('load',d);
	}
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value=""/>  
		<input type="hidden" name="indexid" value=""/>  
		<input type="hidden" name="indexversionid" value=""/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">参数编码：<span class="sen-required">*</span></td>
				<td><input id="code" type="text" class="sen-edit-element easyui-textbox" name="code" value="" required/></td> 
			</tr>
			<tr>
				<td>参数名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="" name="name" required/></td> 
			</tr>
			<tr>
				<td>值类别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="" data-options="url:'${path}/sys/sysData?type=MODEL_INDEX_PARAMETER_VALUE_TYPE'" name="valuetype" required/></td>
			</tr>
			<tr>
				<td>排序号：<span class="sen-required">*</span></td>
				<td><input id="tache_type" type="text" class="sen-edit-element easyui-numberbox" value="" validType="integer" name="sort" required/></td>
			</tr>
			<tr>
				<td>取值类别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="" data-options="url:'${path}/sys/sysData?type=MODEL_INDEX_PARAMETER_TYPE'" name="type" required/></td>
			</tr>
			<tr>
				<td>参数值：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="" name="value" multiline="true" required/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>