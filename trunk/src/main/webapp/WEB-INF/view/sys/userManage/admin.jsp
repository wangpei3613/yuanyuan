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
			url:'${path }/system/user/queryUserAdmin',
			sortName:'createdate',
		    sortOrder:'desc',
			columns:[[
				{field:'id',hidden:true},
				{field:'userName',title:'帐号',width:120,sortable:true},
				{field:'pass',title:'密码',width:120,sortable:true},
				{field:'nickName',title:'姓名',width:120,sortable:true},
				{field:'deptName',title:'所在部门',width:200,sortable:true},
				{field:'linkPhone',title:'联系电话',width:120,sortable:true},
				{field:'serialNo',title:'串号',width:130,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'isleave',title:'是否请假',width:90,sortable:true,align:'center',dictType:'SEN_IF'}
			]]
		});
	});
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
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>