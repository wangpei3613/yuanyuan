<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	function getUser(){
		layer.win({
			  title: '请选择用户',
			  area: [top.window.innerWidth-300+'px', top.window.innerHeight-100+'px'],
			  content: '${path}/system/user/toGetUser',
			  btn:['确定','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow;
				  if(win.grid.grid('selectOne')){
					  $('#userid').val(win.grid.grid('getSelected').id);
					  $('#nickname').textbox('setValue',win.grid.grid('getSelected').nickName);
					  top.layer.close(index);
				  }
				  return false;
			  }
			});
	}
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/> 
		<input type="hidden" id="userid" name="userid" value="${v.userid }"/> 
		<table class="sen-edit-table">
			<tr>
				<td style="width: 35%;">请假用户：<span class="sen-required">*</span></td>
				<td><input type="text" editable="false" id="nickname" class="sen-edit-element easyui-textbox" name="nickname" value="${v.nickname }" required data-options="
					icons:[{
                        iconCls:'icon-search',
                        handler: getUser
                    }]
				"/></td> 
			</tr>
			<tr>
				<td>请假开始时间：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-datetimebox" editable="false" name="startdate" value="${v.startdate }" required/></td> 
			</tr>
			<tr>
				<td>请假结束时间：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-datetimebox" editable="false" name="enddate" value="${v.enddate }" required/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>