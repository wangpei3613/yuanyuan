<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>数据字典管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<style type="text/css">
	body>.layout-panel-center{
		
	}
	</style>
	<script type="text/javascript">
	var grid,gridChild;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/dictionary/queryPaper',
			sortName:'createDate',
		    sortOrder:'desc',
		    method:'get',
			columns:[[
				{field:'code',title:'编码',width:200,sortable:true},
				{field:'name',title:'名称',width:200,sortable:true},
				{field:'remark',title:'备注',width:200}
			]],
			onSelect:function(index,row){
				if(row.id != $('#typeId').val()){
					$('#typeId').val(row.id);
					gridChild.grid('clearSelections');
					gridChild.grid('load');
				}
			}
		});
		gridChild = $('#sen-datagrid-child').grid({
			url:'${path }/system/dictionary/queryContent',
			sortName:'ordNumber',
		    sortOrder:'asc',
		    method:'get',
			singleSelect:false,
		    toolbar:'#sen-toolbar-child',
			formEl:'#sen-form-child',
			columns:[[
				{field:'ck',checkbox:true},
				{field:'dictionaryCode',title:'编码',width:60,align:'center',fixed:true,sortable:true},
				{field:'dictionaryName',title:'名称',width:200,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,fixed:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'ordNumber',title:'排序',width:60,align:'center',fixed:true,sortable:true},
				{field:'contents',title:'内容',width:60,align:'center',fixed:true,sortable:true},
				{field:'remark',title:'备注',width:200}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增数据字典',
		  area: ['400px', '300px'],
		  content: '${path}/system/dictionary/editType',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/system/dictionary/add',
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
				  title: '修改数据字典',
				  area: ['400px', '300px'],
				  content: '${path}/system/dictionary/editType?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/dictionary/update',
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
					  url:'/system/dictionary/delete',
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
	function openAddChild(){
		var typeId = $('#typeId').val();
		if(typeId){
			layer.win({
				  title: '新增数据字典子项',
				  area: ['400px', '400px'],
				  content: '${path}/system/dictionary/editContent?typeId='+typeId,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/dictionary/addDicContent',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  sen.msg('保存成功');
								  gridChild.grid('load');
							  }
						  });
					  }
					  return false;
				  }
				});
		}else{
			sen.alert('请选择数据字典');
		}
	}
	function openEditChild(){
		if(gridChild.grid('selectOne')){
		     layer.win({
				  title: '修改数据字典子项',
				  area: ['400px', '400px'],
				  content: '${path}/system/dictionary/editContent?id='+gridChild.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/dictionary/updateDicContent',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  sen.msg('修改成功');
								  gridChild.grid('load');
							  }
						  });
					  }
					  return false;
				  }
			}); 
		}
	}
	function removesChild(){
		var d = gridChild.datagrid('getSelections');
		if(d.length > 0){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					  url:'/system/dictionary/delDicContent',
					  data:{data:JSON.stringify(d)},
					  success:function(){
						  sen.msg('删除成功');
						  gridChild.grid('clearSelections');
						  gridChild.grid('load');
					  }
			  	});
			});
		}else{
			sen.alert('请选择要删除的数据');
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div data-options="region:'west',border:false,title:'数据字典',collapsible:false,cls:'pd3'" style="width: 50%;">
		<div class="easyui-layout sen-crud-page">
			<div class="sen-crud-page-header" data-options="region:'north',border:false">
		    		<form class="sen-crud-page-form" id="sen-form">
		    			<input type="hidden" name="@code" value="like_anywhere" /> 
		    			<input type="hidden" name="@name" value="like_anywhere" /> 
		    			<label>编码：</label><input type="text" name="code" class="easyui-textbox" style="width:120px">
		    			<label>名称：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
		    		</form>
		    </div>
		    <div class="sen-crud-page-center" data-options="region:'center',border:false">
		    		<div class="sen-crud-page-toolbar" id="sen-toolbar">
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
		    		</div>
		    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
		    </div>
		</div>
	</div>
	<div data-options="region:'center',border:false,title:'数据字典子项',cls:'pd3'" style="width: 50%;">
		<div class="easyui-layout sen-crud-page">
			<div class="sen-crud-page-header" data-options="region:'north',border:false">
		    		<form class="sen-crud-page-form" id="sen-form-child">
		    			<input id="typeId" type="hidden" name="typeId" value="" /> 
		    			<input type="hidden" name="@dictionaryCode" value="like_anywhere" /> 
		    			<input type="hidden" name="@dictionaryName" value="like_anywhere" /> 
		    			<label>编码：</label><input type="text" name="dictionaryCode" class="easyui-textbox" style="width:120px">
		    			<label>名称：</label><input type="text" name="dictionaryName" class="easyui-textbox" style="width:120px">
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
		    		</form>
		    </div>
		    <div class="sen-crud-page-center" data-options="region:'center',border:false">
		    		<div class="sen-crud-page-toolbar" id="sen-toolbar-child">
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddChild()" plain="true">新增</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditChild()" plain="true">修改</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removesChild()" plain="true">删除</a>
		    		</div>
		    		<table id="sen-datagrid-child" class="sen-crud-page-datagrid"></table>
		    </div>
		</div>
	</div>
</body>
</html>