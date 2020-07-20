<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>销毁档案管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
        var grid;
        $(function(){
            grid = $('#sen-datagrid').grid({
                url:'${path }/arch/destroy/getPager',
                sortName:'addtime',
                sortOrder:'desc',
                fitColumns:false,
                columns:[[
                    {field:'desty_no',title:'销毁单编号',width:200,sortable:true,align:'center'},
                    {field:'desty_reason',title:'销毁事由',width:100,sortable:true},
                    {field:'arch_reel_no',title:'案件编码',width:100,sortable:true},
                    {field:'box_name',title:'档案盒',width:100,sortable:true},
                    {field:'reel_name',title:'案件名称',width:100,sortable:true},
                    {field:'mulu_name',title:'目录分类',width:100,sortable:true},
                    {field:'file_date',title:'归档时间',width:100,sortable:true},
                    {field:'expire_date',title:'到期时间',width:100,sortable:true},
                    {field:'rfid',title:'rfid码',width:100,sortable:true},
                    {field:'desty_date',title:'注销时间',width:200,sortable:true},
                    {field:'zx_name',title:'注销人',width:200,sortable:true}
                ]]
            });
        });
        function exportExcel(){
            grid.grid('exportExcel',{excelName:"导出档案销毁信息",serviceName:"archDestroySvc",methodName:"getDestroyList"});
        }
	</script>
</head>
<body class="easyui-layout sen-crud-page">
<div class="sen-crud-page-header" data-options="region:'north',border:false">
	<form class="sen-crud-page-form" id="sen-form">
		<input type="hidden" name="@desty_no" value="like_anywhere" />
		<input type="hidden" name="@arch_reel_no" value="like_anywhere" />
		<input type="hidden" name="@reel_name" value="like_anywhere" />
		<label>注销编号：</label><input type="text" name="desty_no" class="easyui-textbox" style="width:120px">
		<label>案件编号：</label><input type="text" name="arch_reel_no" class="easyui-textbox" style="width:120px">
		<label>案件名称：</label><input type="text" name="reel_name" class="easyui-textbox" style="width:120px">
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
	</form>
</div>
<div class="sen-crud-page-center" data-options="region:'center',border:false">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-download" onclick="exportExcel()" plain="true">导出EXCEL</a>
	<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
</div>
</body>
</html>