<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>菜单分配</title>
	<%@ include file="../../index/lib.jsp" %>
	<style type="text/css">
	.datagrid-row-over,.datagrid-row-selected {
	  background: inherit;
	  color: inherit;
	  cursor: default;
	}
	</style>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').treegrid({
			url:'${path }/system/module/getTreeGrid?roleid=${param.id}',
			fitColumns:true,
			idField:'id',
		    treeField:'moduleName',
		    lines:true,
		    fit:true,
		    checkbox:true,
		    nowrap:false,
			columns:[[
				{field:'moduleName',title:'菜单名称',width:150},
				{field:'moduleno',title:'菜单编号',width:100},
				{field:'xxx',title:'权限',width:400,showTitle:false,formatter:function(v,row){
					var d = row.auths,h = [];
					if(d && d.length>0){
						for(var i=0;i<d.length;i++){
							h.push('<div style="display:inline-block;"><input id="'+d[i].id+'" type="checkbox" '+(d[i].checked=='1'?'checked':'')+'/><label for="'+d[i].id+'">'+d[i].name+'</label></div>');
						}
					}
					return h.join('  ');
				}}
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