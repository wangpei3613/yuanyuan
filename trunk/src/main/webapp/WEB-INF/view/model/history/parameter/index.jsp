<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本历史指标参数配置</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/model/index/history/getParameterPager',
			sortName:'sort',
		    sortOrder:'asc',
		    method:'get',
		    queryParams:{indexid:'${param.id}'},
		    pageSize:50,
			columns:[[
				{field:'code',title:'参数编码',width:100,sortable:true},
				{field:'name',title:'参数名称',width:100,sortable:true},
				{field:'valuetype',title:'值类别',width:100,sortable:true,align:'center',dictType:'MODEL_INDEX_PARAMETER_VALUE_TYPE'},
				{field:'sort',title:'排序号',width:60,sortable:true,fixed:true},
				{field:'type',title:'取值类别',width:100,sortable:true,align:'center',dictType:'MODEL_INDEX_PARAMETER_TYPE'},
				{field:'value',title:'参数值',width:200},
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