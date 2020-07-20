<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色分配</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/role/getUserRole?userId=${param.id}',
			pagination:false,
			checkbox:true,
			singleSelect:false,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'id',hidden:true},
				{field:'code',title:'角色编码',width:120},
				{field:'roleName',title:'角色名称',width:120}
			]],
			loadFilterFn:function(d){
				if(d && d.length>0){
					for(var i=0;i<d.length;i++){
						d[i].checked = (d[i].checked=='1');
					}
				}
				return d;
			},
			onLoadSuccess:function(d){
				if(d.total > 0){
					for(var i=0;i<d.total;i++){
						if(d.rows[i].checked){
							grid.grid("selectRow", i);
						}
					}
				}
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