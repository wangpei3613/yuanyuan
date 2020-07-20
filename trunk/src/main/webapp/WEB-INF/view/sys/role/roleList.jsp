<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色列表</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/role/select',
			method:'get',
			columns:[[
				{field:'code',title:'角色编码',width:200,sortable:true},
				{field:'roleName',title:'角色名称',width:200,sortable:true},
				{field:'status',title:'状态',width:120,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				//{field:'checkcreditpassstate',title:'是否验证信贷系统密码',width:150,sortable:true,align:'center',dictType:'SEN_IF'},
				{field:'remark',title:'备注',width:200}
			]],
			onSelect:function(index,row){
				parent.row = row;
				parent.enableTabs();
			}
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增角色',
		  area: ['500px', '350px'],
		  content: '${path}/system/role/edit',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/system/role/add',
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
				  title: '修改角色',
				  area: ['500px', '350px'],
				  content: '${path}/system/role/edit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/role/update',
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
					  url:'/system/role/delete',
					  data:{data:JSON.stringify(grid.datagrid('getSelections'))},
					  success:function(){
						  sen.msg('删除成功');
						  parent.disableTabs();
						  grid.grid('clearSelections');
						  grid.grid('load');
					  }
			  	});
			});
		}
	}
	function alloMenu(){
		var d = grid.grid('getSelected')
		if(grid.grid('selectOne')){
			layer.win({
				  title: '请勾选要分配的菜单',
				  area: [top.window.innerWidth-300+'px', top.window.innerHeight-100+'px'],
				  content: '${path}/system/module/toAllotModule?id='+d.id,
				  btn:['确定','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  selecteds = win.grid.treegrid('getCheckedNodes'),
					  	  ids = [],auths = [];
					  for(var i=0;i<selecteds.length;i++){
						  ids.push(selecteds[i].id);
					  }
					  win.$('input').each(function(){
						  $(this).prop('checked') && auths.push($(this).prop('id'));
				      });
					  sen.ajax({
						  url:'/system/role/addRoleModulesAndAuths',
						  data:{ids:ids.join(','),roleid:d.id,auths:auths.join(',')},
						  loaddingEle:layero,
						  success:function(){
							  top.layer.close(index);
							  sen.msg('菜单分配成功');
						  }
				  	  });
					  return false;
				  }
			});
		}
	}
	function alloAppMenu(){
		var d = grid.grid('getSelected')
		if(grid.grid('selectOne')){
			layer.win({
				  title: '请勾选要分配的App菜单',
				  area: ['500px', '500px'],
				  content: '${path}/system/moduleApp/toAllotAppModule?id='+d.id,
				  btn:['确定','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  selecteds = win.grid.treegrid('getCheckedNodes'),
					  	  ids = [];
					  for(var i=0;i<selecteds.length;i++){
						  ids.push(selecteds[i].id);
					  }
					  sen.ajax({
						  url:'/system/role/addRoleAppModules',
						  data:{ids:ids.join(','),roleid:d.id},
						  loaddingEle:layero,
						  success:function(){
							  top.layer.close(index);
							  sen.msg('菜单分配成功');
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
    			<input type="hidden" name="@code" value="like_anywhere" /> 
    			<input type="hidden" name="@roleName" value="like_anywhere" /> 
    			<label>角色编码：</label><input type="text" name="code" class="easyui-textbox" style="width:120px">
    			<label>角色名称：</label><input type="text" name="roleName" class="easyui-textbox" style="width:120px">
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