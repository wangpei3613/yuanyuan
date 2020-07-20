<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>功能权限编辑</title>
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
		
// 		var id = $("input[name='id']").val();
// 		if(id){
// 			sen.ajax({
// 				  url:'/system/module/auth/toEdit?id='+id,
// 				  data:{},
// 				  success:function(d){
// 					  if(d){
// 						  $("form").setForm(d);
// 					  }
// 				  }
// 			  });
// 		}
	});
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id}"/>  
		<input type="hidden" name="moduleid" value="${v.moduleid}"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">编码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code }" required/></td> 
			</tr>
			<tr>
				<td>名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td> 
			</tr>
			<tr>
				<td>类别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.type }" required data-options="url:'${path }/sys/sysData?type=SEN_MODULE_AUTH_TYPE'" name="type"/></td> 
			</tr>
			<tr>
				<td>排列顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="sort" value="${v.sort }" validType="integer" required/></td> 
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path }/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>图标：</td>
				<td><input type="text" id="icon" class="sen-edit-element easyui-textbox" name="icon" value="${v.icon }"/></td> 
			</tr>
			<tr>
				<td>事件：</td>
				<td><input type="text" prompt="默认与编码相同" class="sen-edit-element easyui-textbox" name="action" value="${v.action }"/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>