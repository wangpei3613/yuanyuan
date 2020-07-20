<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>环节人员配置编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		if('${v.id}'){
			$('#value').textbox('readonly',false); 
		}
		$('#type').combobox({
			onChange:function(v){
				if(v=='1' || v=='2' || v=='3'){
					$('#value').textbox('setValue',null);
					$('#value').textbox('readonly',true);
				}else{
					$('#value').textbox('readonly',false);
				}
			}
		});
		$('#value').textbox('textbox').on('click',function(){
			var v = $('#type').combobox('getValue');
			if(v == '1'){
				getUser();
			}else if(v == '2'){
				getRole();
			}else if(v == '3'){
				getDept();
			}
		});
	});
	function getUser(){
		layer.win({
			  title: '请选择用户',
			  area: [top.window.innerWidth-100+'px', top.window.innerHeight-100+'px'],
			  content: '${path}/system/user/toGetUser?multiple=1',
			  btn:['确定','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow,names = [];
				  if(win.ids.length > 0){
					  for(var i=0;i<win.rows.length;i++){
						  names.push(win.rows[i].nickName);
					  }
					  $('#value').textbox('setValue',names.join(','));
					  $('#values').val(win.ids.join(','));
					  top.layer.close(index);
				  }else{
					  sen.alert('新添加要选择的用户');
				  }
				  return false;
			  }
			});
	}
	function getRole(){
		layer.win({
			  title: '请勾选要添加的角色',
			  area: ['400px', '500px'],
			  content: '${path}/system/role/toAllotRole',
			  btn:['确定','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow,
				  	  selecteds = win.grid.grid('getSelections'),
				  	  ids = [],
				  	  names = [];
				  if(selecteds.length > 0){
					  for(var i=0;i<selecteds.length;i++){
						  ids.push(selecteds[i].id);
						  names.push(selecteds[i].roleName);
					  }
					  $('#value').textbox('setValue',names.join(','));
					  $('#values').val(ids.join(','));
					  top.layer.close(index);
				  }else{
					  sen.alert('请勾选要添加的角色');
				  }
				  return false;
			  }
		});
	}
	function getDept(){
		layer.win({
			  title: '请勾选要添加的部门',
			  area: ['400px', '500px'],
			  content: '${path}/system/depart/toAllotDept',
			  btn:['确定','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow,
				  	  selecteds = win.grid.treegrid('getCheckedNodes'),
				  	  ids = [],
				      names = [];
				  if(selecteds.length > 0){
					  for(var i=0;i<selecteds.length;i++){
						  ids.push(selecteds[i].id);
						  names.push(selecteds[i].fullName);
					  }
					  $('#value').textbox('setValue',names.join(','));
					  $('#values').val(ids.join(','));
					  top.layer.close(index);
				  }else{
					  sen.alert('请勾选要添加的部门');
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
		<input type="hidden" name="values" id="values"/>  
		<input type="hidden" name="tacheid" value="${v.tacheid }"/>  
		<input type="hidden" name="category" value="${v.category }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 25%;">规则类别：<span class="sen-required">*</span></td>
				<td><input id="type" style="width: 350px;" type="text" class="sen-edit-element easyui-combobox" value="${v.type }" data-options="url:'${path}/sys/sysData?type=SEN_ACT_TACHE_OPERATOR_TYPE'" name="type" required/></td> 
			</tr>
			<tr>
				<td>内容：<span class="sen-required">*</span></td>
				<td><input id="value" style="width: 350px;height: 80px;" type="text" class="sen-edit-element easyui-textbox" readonly name="value" value="${v.value }" required multiline="true"/></td> 
			</tr>
			<tr>
				<td>备注：</td>
				<td><input type="text" style="width: 350px;height: 50px;" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true"/></td> 
			</tr>
		</table>
	</form>
</body>
</html>