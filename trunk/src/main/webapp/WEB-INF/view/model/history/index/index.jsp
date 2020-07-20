<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本历史指标</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/model/index/history/getVersionIndexTree?hisid=${param.id}',
			treeField:'name',
			fitColumns:false,
			 frozenColumns:[[
				 {field:'name',title:'指标名称',width:300},
				 {field:'code',title:'指标编码',width:100},
		    ]],
			columns:[[
				{field:'status',title:'状态',width:80,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'sort',title:'排列顺序',width:80},
				{field:'category',title:'指标类别',width:80,align:'center',dictType:'MODEL_INDEX_LIBRARY_CATEGORY'},
				{field:'content',title:'指标内容',width:200},
				{field:'maxscore',title:'指标满分',width:80},
				{field:'formula',title:'指标公式',width:200},
				{field:'argument',title:'取值参数',width:200},
				{field:'remark',title:'备注',width:400}
			]],
			onSelect:function(row){
				parent.index = row;
				parent.disableTabs('2,3');
				if(row.level == '2'){
					parent.enableTabs(2);
					if(row.category == '1'){
						parent.enableTabs(3);
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