<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>APP菜单分配</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').treegrid({
			url:'${path }/system/moduleApp/getTreeGrid?roleid=${param.id}',
			fitColumns:true,
			idField:'id',
		    treeField:'name',
		    lines:true,
		    fit:true,
		    checkbox:true,
			columns:[[
				{field:'id',hidden:true},
				{field:'name',title:'菜单名称',width:200},
				{field:'code',title:'菜单编号',width:100},
			]]
		});
	});
	</script>
</head>
<body class="easyui-layout sen-crud-page">
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>