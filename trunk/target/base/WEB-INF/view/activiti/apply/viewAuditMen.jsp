<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看当前审批人</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/act/apply/info/getAuditMen?applyid=${param.applyid}',
			columns:[[
				{field:'userName',title:'帐号',width:120,sortable:true},
				{field:'nickName',title:'姓名',width:120,sortable:true},
				{field:'deptName',title:'所在部门',width:200,sortable:true},
				{field:'linkPhone',title:'联系电话',width:120,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'isleave',title:'是否请假',width:90,sortable:true,align:'center',dictType:'SEN_IF'}
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