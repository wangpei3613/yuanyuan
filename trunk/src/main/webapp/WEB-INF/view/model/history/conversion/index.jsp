<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本历史指标分值转换配置</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/model/index/history/getConversionPager',
			sortName:'sort',
		    sortOrder:'asc',
		    method:'get',
		    queryParams:{indexid:'${param.id}'},
		    pageSize:50,
			columns:[[
				{field:'code',title:'转换编码',width:100,sortable:true},
				{field:'name',title:'转换名称',width:100,sortable:true},
				{field:'value',title:'转换分值',width:100,sortable:true,align:'center'},
				{field:'sort',title:'排序号',width:60,sortable:true,fixed:true},
				{field:'remark',title:'备注',width:200}
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