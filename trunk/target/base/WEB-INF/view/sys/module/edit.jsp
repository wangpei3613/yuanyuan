<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>功能菜单编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		$('#icon').textbox({
			icons:[{
                iconCls:'icon-add',
                handler: function(e){
                		sen.selectIcon(function(v){
                			$('#icon').textbox('setValue',v);
                		});
                }
            }]
		});
	});
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>
		<input type="hidden" name="controller" value="${v.controller }"/>  
		<input type="hidden" name="viewport" value="${v.viewport }"/>  
		<input type="hidden" name="url" value="${v.url }"/>    
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">菜单编号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="moduleno" value="${v.moduleno }" required/></td> 
			</tr>
			<tr>
				<td>菜单名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="moduleName" value="${v.moduleName }" required/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.isuse }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="isuse" required/></td> 
			</tr>
			<tr>
				<td>菜单类型：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.moduletype }" data-options="url:'${path }/sys/sysData?type=SEN_MODULE_TYPE'" name="moduletype" required/></td> 
			</tr>
			<tr>
				<td>父级菜单：</td>
				<td><input type="text" class="sen-edit-element easyui-combotree" value="${v.pid }" data-options="url:'${path }/system/module/getTreeGrid?moduletype=1'" name="pid"/></td> 
			</tr>
			<tr>
				<td>排列顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="orderNumber" value="${v.orderNumber }" validType="integer" required/></td> 
			</tr>
			<tr>
				<td>菜单图标：</td>
				<td><input type="text" id="icon" class="sen-edit-element easyui-textbox" name="icons" value="${v.icons }"/></td> 
			</tr>
			<tr>
				<td>菜单路径：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="weburl" value="${v.weburl }"/></td> 
			</tr>
			<tr>
				<td>系统首页：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="homepage" value="${v.homepage }"/></td> 
			</tr>
			<tr>
				<td>所属系统：</td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.syscode }" data-options="url:'${path }/sys/sysData?type=SEN_SYSCODE'" name="syscode"/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>