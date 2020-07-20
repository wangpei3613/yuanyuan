<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>部门分配</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').treegrid({
			url:'${path }/system/depart/getUserDepart?userId=${param.id}',
			fitColumns:true,
			idField:'id',
		    treeField:'fullName',
		    lines:true,
		    fit:true,
		    checkbox:true,
			columns:[[
				{field:'id',hidden:true},
				{field:'fullName',title:'部门名称',width:2},
				{field:'deptCode',title:'部门编号',width:1}
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