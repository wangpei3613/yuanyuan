<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/user/queryUser',
			sortName:'createdate',
		    sortOrder:'desc',
			columns:[[
				{field:'id',hidden:true},
				{field:'userName',title:'帐号',width:120,sortable:true},
				{field:'nickName',title:'姓名',width:120,sortable:true},
				{field:'deptName',title:'所在部门',width:200,sortable:true},
				{field:'linkPhone',title:'联系电话',width:120,sortable:true},
				{field:'serialNo',title:'串号',width:130,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'isleave',title:'是否请假',width:90,sortable:true,align:'center',dictType:'SEN_IF'}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增用户',
		  area: ['400px', '400px'],
		  content: '${path}/system/user/editIndex',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/system/user/addUser',
					  data:form.serializeObject(),
					  loaddingEle:layero,
					  success:function(){
						  top.layer.close(index);
						  sen.msg('保存成功');
						  grid.grid('load');
					  }
				  });
			  }
			  return false;
		  }
		}); 
	}
	function openEdit(){
		if(grid.grid('selectOne')){
		     layer.win({
				  title: '修改用户',
				  area: ['400px', '400px'],
				  content: '${path}/system/user/editIndex?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/user/editUser',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  sen.msg('修改成功');
								  grid.grid('load');
							  }
						  });
					  }
					  return false;
				  }
			}); 
		}
	}
	function remove(){
		if(grid.grid('selectOne')){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					  url:'/system/user/delUser',
					  data:{data:JSON.stringify(grid.datagrid('getSelections'))},
					  success:function(){
						  sen.msg('删除成功');
						  grid.grid('clearSelections');
						  grid.grid('load');
					  }
			  	});
			});
		}
	}
	function resetPwd(){
		if(grid.grid('selectOne')){
			sen.confirm('确定要重置密码吗?',function(index){
				sen.ajax({
					  url:'/system/user/resetPwd',
					  data:{id:grid.grid('getSelected').id},
					  success:function(){
						  sen.msg('重置密码成功');
					  }
			  	});
			});
		}
	}
	function allotDept(){
		var d = grid.grid('getSelected')
		if(grid.grid('selectOne')){
			layer.win({
				  title: '请勾选要分配的部门',
				  area: ['600px', '500px'],
				  content: '${path}/system/depart/toAllotDept?id='+d.id,
				  btn:['确定','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  selecteds = win.grid.treegrid('getCheckedNodes'),
					  	  ids = [];
					  for(var i=0;i<selecteds.length;i++){
						  ids.push(selecteds[i].id);
					  }
					  sen.ajax({
						  url:'/system/depart/saveUserDept',
						  data:{ids:ids.join(','),userId:d.id},
						  loaddingEle:layero,
						  success:function(){
							  top.layer.close(index);
							  sen.msg('部门分配成功');
						  }
				  	  });
					  return false;
				  }
			});
		}
	}
	function allotRole(){
		var d = grid.grid('getSelected')
		if(grid.grid('selectOne')){
			layer.win({
				  title: '请勾选要分配的角色',
				  area: ['400px', '500px'],
				  content: '${path}/system/role/toAllotRole?id='+d.id,
				  btn:['确定','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  selecteds = win.grid.grid('getSelections'),
					  	  ids = [];
					  for(var i=0;i<selecteds.length;i++){
						  ids.push(selecteds[i].id);
					  }
					  sen.ajax({
						  url:'/system/role/addUserRole',
						  data:{roleIds:ids.join(','),userId:d.id},
						  loaddingEle:layero,
						  success:function(){
							  top.layer.close(index);
							  sen.msg('角色分配成功');
						  }
				  	  });
					  return false;
				  }
			});
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@t1.userName" value="like_anywhere" /> 
    			<input type="hidden" name="@t1.nickName" value="like_anywhere" /> 
    			<input type="hidden" name="@t1.deptId" value="in" /> 
    			<label>帐号：</label><input type="text" name="t1.userName" class="easyui-textbox" style="width:120px">
    			<label>姓名：</label><input type="text" name="t1.nickName" class="easyui-textbox" style="width:120px">
    			<label>所在部门：</label><input type="text" name="t1.deptId" data-options="url:'${path }/system/depart/getDeptTree',multiple:true,cascadeCheck:false" class="easyui-combotree" style="width:180px">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
    		</form>
    </div>
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<c:if test="${not empty moduleAuths }">
    			<div class="sen-crud-page-toolbar" id="sen-toolbar">
    				<c:forEach items="${moduleAuths }" var="auth">  
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="${auth.icon }" onclick="${empty auth.action?auth.code:auth.action}()" plain="true">${auth.name }</a>
    				</c:forEach>
    			</div>
    		</c:if>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>