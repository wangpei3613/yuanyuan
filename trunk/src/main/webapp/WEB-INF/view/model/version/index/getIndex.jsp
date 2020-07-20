<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标分配</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').treegrid({
			url:'${path }/model/index/version/getIndexToVersionTree?versionid=${param.versionid}',
			fitColumns:true,
			idField:'id',
		    treeField:'name',
		    lines:true,
		    fit:true,
		    checkbox:true,
			columns:[[
				{field:'name',title:'指标名称',width:300},
				{field:'code',title:'指标编号',width:100},
			]],
			onLoadSuccess:function(row, data){
				var d = $.extend(true,[],data)
				if(d && d.length>0){
					for(var i=0;i<d.length;i++){
						doit(d[i]);
					}
				}
			}
		});
	});
	function doit(d){
		if(filter(d)){
			if(d.children && d.children.length>0){
				for(var i=0;i<d.children.length;i++){
					doit(d.children[i]);
				}
			}
		}else{
			grid.tgrid('remove',d.id);
		}
	}
	function filter(data){
		if(data.level == '1'){
			if(data.status == '1' && data.children && data.children.length>0){
				for(var i=0;i<data.children.length;i++){
					if(filter(data.children[i])){
						return true;
					}
				}
			}
			return false;
		}
		return data.status == '1';
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>