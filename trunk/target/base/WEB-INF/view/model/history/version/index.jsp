<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本历史</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/model/index/history/getVersionPager',
			sortName:'createdate',
		    sortOrder:'desc',
		    method:'get',
		    queryParams:{versionid:'${param.id}'},
			columns:[[
				{field:'code',title:'版本号',width:100,sortable:true,align:'center'},
				{field:'name',title:'版本名称',width:200,sortable:true,align:'center'},
				{field:'createdate',title:'使用开始时间',width:200,sortable:true,align:'center'},
				{field:'remark',title:'备注',width:300}
			]],
			onSelect:function(index,row){
				parent.row = row;
				parent.disableTabs();
				parent.enableTabs(1);
			}
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