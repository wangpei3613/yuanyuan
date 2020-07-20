<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>选择用户</title>
	<%@ include file="../../index/lib.jsp" %>
	<style type="text/css">
	 .grid-child>div{
		height: 100%;
	}
	.grid-child .datagrid-wrap{
		height: 100%;
		height: calc(100% - 2px);
	} 
	</style>
	<script type="text/javascript">
	var grid,gridChild,rows = [],ids = [];
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/user/queryUser',
			sortName:'createdate',
		    sortOrder:'desc',
		    singleSelect:false,
			columns:[[
				{field:'id',hidden:true},
				{field:'ck',checkbox:true},
				{field:'userName',title:'帐号',width:120,sortable:true},
				{field:'nickName',title:'姓名',width:120,sortable:true},
				{field:'deptName',title:'所在部门',width:200,sortable:true},
				{field:'linkPhone',title:'联系电话',width:120,sortable:true},
				{field:'serialNo',title:'串号',width:130,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'}
			]]
		});
		gridChild = $('#sen-datagrid-child').datagrid({
			toolbar:'#sen-toolbar-child',
			data:[],
			fitColumns:true,
			rownumbers:true,
			singleSelect:false,
			columns:[[
				{field:'id',hidden:true},
				{field:'ck',checkbox:true},
				{field:'userName',title:'帐号',width:120},
				{field:'nickName',title:'姓名',width:120}
			]]
		});
	});
	function addUser(){
		var d = grid.grid('getSelections');
		if(d.length > 0){
			for(var i=0;i<d.length;i++){
				if(ids.indexOf(d[i].id) == -1){
					gridChild.datagrid('appendRow', d[i]);
					rows.push(d[i]);
					ids.push(d[i].id);
				}
			}
		}else{
			sen.alert('请选择要添加的用户');
		}
	}
	function removeUser(){
		var d = gridChild.datagrid('getSelections');
		if(d.length > 0){
			for(var i=0;i<d.length;i++){
				rows.splice(rows.indexOf(d[i]),1);
				ids.splice(ids.indexOf(d[i].id),1);
				gridChild.datagrid('deleteRow', gridChild.datagrid('getRowIndex',d[i]));
			}
			gridChild.datagrid('clearSelections');
		}else{
			sen.alert('请选择要移除的用户');
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
    <div class="sen-crud-page-center" data-options="title:'待选择用户',region:'center',border:false,cls:'pd3'">
   	    <div class="sen-crud-page-toolbar" id="sen-toolbar">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addUser()" plain="true">添加</a>
    		</div>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
    <div class="sen-crud-page-center grid-child" data-options="title:'已选择用户',region:'east',border:false,cls:'pd3',collapsible:false" style="width: 350px;">
    		<div class="sen-crud-page-toolbar" id="sen-toolbar-child">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeUser()" plain="true">移除</a>
    		</div>
    		<table id="sen-datagrid-child" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>