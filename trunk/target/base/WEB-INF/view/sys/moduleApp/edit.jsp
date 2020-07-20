<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>功能菜单编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		if('${v.level}'){
			setRequire('${v.level}'=='2');  
		}
		$('#pid').combotree({
			onSelect:function(d){
				setRequire(d.level=='1');
				$('#level').combobox('setValue',Number(d.level)+1);
			}
		});
	});
	function setRequire(b){
		$('#indexshow').combobox({required:!b});
		$('#icons').textbox({required:!b});
		if(b){
			$('#indexshow,#icons').closest('td').prev().find('span').remove();
		}else{
			if($('#indexshow').closest('td').prev().find('span').length == 0){
				$('#indexshow,#icons').closest('td').prev().append('<span class="sen-required">*</span>');
			}
		}
	}
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">菜单编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required/></td> 
			</tr>
			<tr>
				<td>菜单名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>菜单类型：<span class="sen-required">*</span></td>
				<td><input id="level" type="text" readonly class="sen-edit-element easyui-combobox" value="${v.level }" data-options="url:'${path }/sys/sysData?type=SEN_MODULE_APP_TYPE'" name="level" required/></td> 
			</tr>
			<tr>
				<td>父级菜单：<span class="sen-required">*</span></td>
				<td><input type="text" id="pid" class="sen-edit-element easyui-combotree" value="${v.pid }" data-options="url:'${path }/system/moduleApp/getTreeGrid?level=1'" name="pid" required/></td> 
			</tr>
			<tr>
				<td>排列顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="ordernum" value="${v.ordernum }" validType="integer" required/></td> 
			</tr>
			<tr>
				<td>是否首页展示：</td>
				<td><input id="indexshow" type="text" class="sen-edit-element easyui-combobox" value="${v.indexshow }" data-options="url:'${path }/sys/sysData?type=SEN_IF'" name="indexshow"/></td> 
			</tr>
			<tr>
				<td>菜单图标：</td>
				<td><input id="icons" type="text" class="sen-edit-element easyui-textbox" name="icons" value="${v.icons }"/></td> 
			</tr>
			<tr>
				<td>菜单路径：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="url" value="${v.url }"/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>